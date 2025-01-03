import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ResurseUmane {
    static void afiseazaInterfataResurseUmane(String cnp) {
        // Create the frame
        JFrame frameResurseUmane = new JFrame("Resurse Umane");
        frameResurseUmane.setSize(500, 500);
        frameResurseUmane.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameResurseUmane.setLayout(null); // Absolute layout for layered pane

        // Load the background GIF
        ImageIcon backgroundIcon = new ImageIcon("C:/Users/pinza/Desktop/FACULTATE/AN2/SEM1/(BD) Baze de date/proiect bd (1) - Copy/proiect bd/hmm.gif");
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setBounds(0, 0, 500, 500); // Full-size background

        // Create a layered pane
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 500, 500);

        // Add the background to the lowest layer
        layeredPane.add(backgroundLabel, Integer.valueOf(0));

        // Create a panel for buttons
        JPanel panelResurseUmane = new JPanel();
        panelResurseUmane.setLayout(new GridLayout(5, 2, 10, 10)); // Grid layout with spacing
        panelResurseUmane.setOpaque(false); // Transparent background
        panelResurseUmane.setBounds(45, 70, 400, 300); // Position of the button grid

        // Style for buttons
        Font buttonFont = new Font("Arial", Font.BOLD, 12); // Font size for buttons
        Color buttonBackground = new Color(116, 0, 0); // Button color
        Color buttonForeground = Color.WHITE; // Text color for buttons

        // Create buttons
        JButton dateButton = new JButton("Date Personale");
        JButton orarButton = new JButton("Orar");
        JButton concediiButton = new JButton("Vizualizare Concedii");
        JButton adaugaConcediuButton = new JButton("Adaugare Concediu");
        JButton stergeConcediuButton = new JButton("Sterge Concediu");
        JButton cautareAButton = new JButton("Cauta Angajatul");
        JButton cautareUButton = new JButton("Cauta Utilizatorul");
        JButton orareButton = new JButton("Orare");
        JButton adaugaOrarButton= new JButton("AdaugaOrar");
        JButton stergeOrarButton = new JButton("Sterge Orar");
        JButton logoutButton = new JButton("Logout"); // Logout button

        // Set button sizes (to make them more consistent and smaller)
        Dimension buttonSize = new Dimension(120, 30); // Smaller size for buttons

        // Add buttons to the panel and set properties
        JButton[] buttons = {dateButton,orarButton,concediiButton, adaugaConcediuButton, stergeConcediuButton, cautareAButton, cautareUButton, orareButton, adaugaOrarButton, stergeOrarButton, logoutButton};
        for (JButton button : buttons) {
            button.setFont(buttonFont);
            button.setBackground(buttonBackground);
            button.setForeground(buttonForeground);
            button.setFocusPainted(false);
            button.setBorderPainted(false);
            button.setOpaque(true);
            button.setPreferredSize(buttonSize); // Set the preferred size for buttons
            panelResurseUmane.add(button); // Add each button to the panel
        }

        // Add action listeners for buttons
        dateButton.addActionListener(e->afiseazaDatePersonale(cnp));
        orarButton.addActionListener(e->afiseazaOrarByCnp(cnp));
        concediiButton.addActionListener(e -> afiseazaInterfataConcedii());
        orareButton.addActionListener(e -> afiseazaInterfataOrare());
        cautareAButton.addActionListener(e -> afiseazaInterfataCautareAngajat());
        cautareUButton.addActionListener(e-> afiseazaInterfataCautareUtilizator());
        adaugaConcediuButton.addActionListener(e -> afiseazaInterfataAdaugareConcediu());
        adaugaOrarButton.addActionListener(e -> afiseazaInterfataAdaugaOrar());
        stergeOrarButton.addActionListener(e -> afiseazaInterfataStergereOrar());
        stergeConcediuButton.addActionListener(e -> afiseazaInterfataStergereConcediu());
        logoutButton.addActionListener(e -> frameResurseUmane.dispose()); // Close the window when logout button is clicked

        // Add the button panel to the layered pane (higher layer)
        layeredPane.add(panelResurseUmane, Integer.valueOf(1));

        // Create the logout button (positioned below the button grid)
        logoutButton.setBounds(200, 400, 100, 40); // Position below the panel

        // Add the logout button to the layered pane (higher layer)
        layeredPane.add(logoutButton, Integer.valueOf(1));

        // Add the layered pane to the frame
        frameResurseUmane.add(layeredPane);

        // Finalize the frame
        frameResurseUmane.setLocationRelativeTo(null); // Center on screen
        frameResurseUmane.setVisible(true);
    }

    private static void afiseazaDatePersonale(String cnp) {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/proiect2?useSSL=false&serverTimezone=UTC",
                "root",
                "LionelMessieTeo1"
        )) {
            // Apelăm procedura stocată utilizând CallableStatement
            String callProcedure = "{ CALL AfisareDatePersonaleDupaCNP(?) }";
            try (CallableStatement callableStatement = connection.prepareCall(callProcedure)) {
                callableStatement.setString(1, cnp);

                boolean hasResults = callableStatement.execute();

                if (hasResults) {
                    try (ResultSet resultSet = callableStatement.getResultSet()) {
                        // Creăm un JTextArea pentru a afișa datele
                        JTextArea textArea = new JTextArea();
                        textArea.setEditable(false);
                        textArea.setFont(new Font("Arial", Font.PLAIN, 16)); // Font puțin mai mare
                        textArea.setLineWrap(true); // Împărțire pe linii
                        textArea.setWrapStyleWord(true); // Împărțire inteligentă
                        textArea.setBackground(new Color(255, 255, 255, 200)); // Fundal semi-transparent

                        // Iterăm prin rezultatele interogării și adăugăm în JTextArea
                        StringBuilder sb = new StringBuilder();
                        while (resultSet.next()) {
                            int columnCount = resultSet.getMetaData().getColumnCount();
                            for (int i = 1; i <= columnCount; i++) {
                                sb.append(resultSet.getMetaData().getColumnName(i))
                                        .append(": ")
                                        .append(resultSet.getObject(i))
                                        .append("\n");
                            }
                            sb.append("\n------------------------------------------\n");
                        }
                        textArea.setText(sb.toString().isEmpty() ? "Nu există date de afișat." : sb.toString());

                        // Creare JScrollPane pentru textArea
                        JScrollPane scrollPane = new JScrollPane(textArea);
                        scrollPane.setBounds(75, 100, 350, 300); // Dimensiuni mai mari, centrat

                        // Creare fereastră principală
                        JFrame frameDatePersonale = new JFrame("Date Personale");
                        frameDatePersonale.setSize(500, 500);
                        frameDatePersonale.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                        // Creare JLayeredPane pentru gestionarea layerelelor
                        JLayeredPane layeredPane = new JLayeredPane();
                        layeredPane.setBounds(0, 0, 500, 500);

                        // Adăugare GIF ca fundal
                        ImageIcon backgroundIcon = new ImageIcon("C:/Users/pinza/Desktop/FACULTATE/AN2/SEM1/(BD) Baze de date/proiect bd (1) - Copy/proiect bd/hmm.gif");
                        JLabel backgroundLabel = new JLabel(backgroundIcon);
                        backgroundLabel.setBounds(0, 0, 500, 500);

                        // Creare JPanel pentru text
                        JPanel panel = new JPanel();
                        panel.setLayout(new BorderLayout());
                        panel.setOpaque(false); // Transparent
                        panel.setBounds(75, 100, 350, 300); // Dimensiuni mai mari, centrat
                        panel.add(scrollPane);

                        // Adăugare fundal și panel în layere
                        layeredPane.add(backgroundLabel, Integer.valueOf(0)); // Fundal în stratul 0
                        layeredPane.add(panel, Integer.valueOf(1)); // Cutia de afișare în stratul 1

                        // Adăugare layeredPane la fereastră
                        frameDatePersonale.setContentPane(layeredPane);

                        // Configurare fereastră
                        frameDatePersonale.setLocationRelativeTo(null); // Centrare pe ecran
                        frameDatePersonale.setVisible(true);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    private static void afiseazaOrarByCnp(String cnp) {
        // Crearea ferestrei principale
        JFrame frameOrare = new JFrame("Orare");
        frameOrare.setSize(500, 500);
        frameOrare.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameOrare.setLayout(null);

        // Încărcarea unui GIF de fundal
        ImageIcon backgroundIcon = new ImageIcon("C:/Users/pinza/Desktop/FACULTATE/AN2/SEM1/(BD) Baze de date/proiect bd (1) - Copy/proiect bd/hmm.gif"); // Modifică calea
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setBounds(0, 0, 500, 500);

        // Crearea unui JLayeredPane pentru fundal și alte componente
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 500, 500);
        layeredPane.add(backgroundLabel, Integer.valueOf(0));

        // Crearea unui JPanel pentru JTextArea
        JPanel panelOrare = new JPanel();
        panelOrare.setLayout(new BorderLayout());
        panelOrare.setOpaque(false); // Transparent
        panelOrare.setBounds(50, 50, 400, 350); // Dimensiune mai mică pentru a lăsa fundalul vizibil
        layeredPane.add(panelOrare, Integer.valueOf(1));

        // Crearea unui JTextArea pentru a afișa rezultatele
        JTextArea resultTextArea = new JTextArea(10, 30);
        resultTextArea.setEditable(false);
        resultTextArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        resultTextArea.setBackground(new Color(240, 248, 255));  // Fundal ușor pentru TextArea

        // Adăugarea unui JScrollPane pentru a permite derularea textului
        JScrollPane scrollPane = new JScrollPane(resultTextArea);
        panelOrare.add(scrollPane, BorderLayout.CENTER);

        // Executarea procedurii de afișare a orelor pentru CNP
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/proiect2?useSSL=false&serverTimezone=UTC",
                "root",
                "LionelMessieTeo1"
        );
             CallableStatement stmt = conn.prepareCall("{CALL AfisareOrarByCnp(?)}")) {

            stmt.setString(1, cnp); // Transmite CNP-ul către procedura stocată
            stmt.execute();

            try (ResultSet rs = stmt.getResultSet()) {
                StringBuilder sb = new StringBuilder();
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnCount = rsmd.getColumnCount();

                // Iterarea prin rezultatele interogării
                while (rs.next()) {
                    for (int i = 1; i <= columnCount; i++) {
                        sb.append(rsmd.getColumnLabel(i)).append(": ").append(rs.getString(i)).append("\n");
                    }
                    sb.append("--------------------------------------------------\n");
                }

                // Setarea textului în JTextArea
                resultTextArea.setText(sb.toString().isEmpty() ? "Nu există date de afișat." : sb.toString());
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frameOrare,
                    "Eroare la preluarea datelor: " + ex.getMessage(),
                    "Eroare", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }

        // Adăugarea JPanel-ului la JLayeredPane
        frameOrare.add(layeredPane);

        // Finalizarea configurării ferestrei
        frameOrare.setLocationRelativeTo(null);
        frameOrare.setVisible(true);
    }
    private static void afiseazaInterfataConcedii() {
        // Crearea ferestrei principale
        JFrame frameConcediiOrare = new JFrame("Concedii");
        frameConcediiOrare.setSize(500, 500);
        frameConcediiOrare.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameConcediiOrare.setLayout(null);

        // Încărcarea unui GIF de fundal
        ImageIcon backgroundIcon = new ImageIcon("C:/Users/pinza/Desktop/FACULTATE/AN2/SEM1/(BD) Baze de date/proiect bd (1) - Copy/proiect bd/hmm.gif"); // Modifică calea
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setBounds(0, 0, 500, 500);

        // Crearea unui JLayeredPane pentru fundal și alte componente
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 500, 500);
        layeredPane.add(backgroundLabel, Integer.valueOf(0));

        // Crearea unui JPanel pentru JTextArea
        JPanel panelConcedii = new JPanel();
        panelConcedii.setLayout(new BorderLayout());
        panelConcedii.setOpaque(false);
        panelConcedii.setBounds(50, 50, 400, 350); // Dimensiune mai mică pentru a lăsa fundalul vizibil
        layeredPane.add(panelConcedii, Integer.valueOf(1));

        // Crearea unui JTextArea pentru a afișa rezultatele
        JTextArea resultTextArea = new JTextArea(10, 30);
        resultTextArea.setEditable(false);
        resultTextArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        resultTextArea.setBackground(new Color(240, 248, 255));  // Fundal ușor pentru TextArea

        // Adăugarea unui JScrollPane pentru a permite derularea textului
        JScrollPane scrollPane = new JScrollPane(resultTextArea);
        panelConcedii.add(scrollPane, BorderLayout.CENTER);

        // Executarea procedurii de afișare a concediilor
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/proiect2?useSSL=false&serverTimezone=UTC",
                "root",
                "LionelMessieTeo1"
        );
             CallableStatement stmt = conn.prepareCall("{CALL AfisareConcedii()}")) {

            stmt.execute();

            try (ResultSet rs = stmt.getResultSet()) {
                StringBuilder sb = new StringBuilder();
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnCount = rsmd.getColumnCount();

                // Iterarea prin rezultatele interogării
                while (rs.next()) {
                    for (int i = 1; i <= columnCount; i++) {
                        sb.append(rsmd.getColumnName(i)).append(": ").append(rs.getString(i)).append("\n");
                    }
                    sb.append("\n");
                }

                // Setarea textului în JTextArea
                resultTextArea.setText(sb.toString());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // Adăugarea JPanel-ului la JLayeredPane
        frameConcediiOrare.add(layeredPane);

        // Finalizarea configurării ferestrei
        frameConcediiOrare.setLocationRelativeTo(null);
        frameConcediiOrare.setVisible(true);
    }
    private static void afiseazaInterfataOrare() {
        // Crearea ferestrei principale
        JFrame frameOrare = new JFrame("Orare");
        frameOrare.setSize(500, 500);
        frameOrare.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameOrare.setLayout(null);

        // Încărcarea unui GIF de fundal
        ImageIcon backgroundIcon = new ImageIcon("C:/Users/pinza/Desktop/FACULTATE/AN2/SEM1/(BD) Baze de date/proiect bd (1) - Copy/proiect bd/hmm.gif"); // Modifică calea
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setBounds(0, 0, 500, 500);

        // Crearea unui JLayeredPane pentru fundal și alte componente
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 500, 500);
        layeredPane.add(backgroundLabel, Integer.valueOf(0));

        // Crearea unui JPanel pentru JTextArea
        JPanel panelOrare = new JPanel();
        panelOrare.setLayout(new BorderLayout());
        panelOrare.setOpaque(false); // Transparent
        panelOrare.setBounds(50, 50, 400, 350); // Dimensiune mai mică pentru a lăsa fundalul vizibil
        layeredPane.add(panelOrare, Integer.valueOf(1));

        // Crearea unui JTextArea pentru a afișa rezultatele
        JTextArea resultTextArea = new JTextArea(10, 30);
        resultTextArea.setEditable(false);
        resultTextArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        resultTextArea.setBackground(new Color(240, 248, 255));  // Fundal ușor pentru TextArea

        // Adăugarea unui JScrollPane pentru a permite derularea textului
        JScrollPane scrollPane = new JScrollPane(resultTextArea);
        panelOrare.add(scrollPane, BorderLayout.CENTER);

        // Executarea procedurii de afișare a orelor
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/proiect2?useSSL=false&serverTimezone=UTC",
                "root",
                "LionelMessieTeo1"
        );
             CallableStatement stmt = conn.prepareCall("{CALL AfisareOrare()}")) {

            stmt.execute();

            try (ResultSet rs = stmt.getResultSet()) {
                StringBuilder sb = new StringBuilder();
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnCount = rsmd.getColumnCount();

                // Iterarea prin rezultatele interogării
                while (rs.next()) {
                    for (int i = 1; i <= columnCount; i++) {
                        sb.append(rsmd.getColumnLabel(i)).append(": ").append(rs.getString(i)).append("\n");
                    }
                    sb.append("--------------------------------------------------\n");
                }

                // Setarea textului în JTextArea
                resultTextArea.setText(sb.toString().isEmpty() ? "Nu există date de afișat." : sb.toString());
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frameOrare,
                    "Eroare la preluarea datelor: " + ex.getMessage(),
                    "Eroare", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }

        // Adăugarea JPanel-ului la JLayeredPane
        frameOrare.add(layeredPane);

        // Finalizarea configurării ferestrei
        frameOrare.setLocationRelativeTo(null);
        frameOrare.setVisible(true);
    }
    private static void afiseazaInterfataCautareAngajat() {
        // Create the main frame
        JFrame frameCautareAngajat = new JFrame("Căutare Angajat");
        frameCautareAngajat.setSize(500, 500);
        frameCautareAngajat.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameCautareAngajat.setLayout(null);

        // Load the background GIF
        ImageIcon backgroundIcon = new ImageIcon("C:/Users/pinza/Desktop/FACULTATE/AN2/SEM1/(BD) Baze de date/proiect bd (1) - Copy/proiect bd/hmm.gif");
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setBounds(0, 0, 500, 500);

        // Create a layered pane
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 500, 500);
        layeredPane.add(backgroundLabel, Integer.valueOf(0));

        // Panel for search fields and buttons
        JPanel panelCautareAngajat = new JPanel();
        panelCautareAngajat.setLayout(new GridBagLayout());
        panelCautareAngajat.setOpaque(false);
        panelCautareAngajat.setBounds(50, 20, 400, 200);
        layeredPane.add(panelCautareAngajat, Integer.valueOf(1));

        // GridBag constraints for layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5); // Reduced spacing
        gbc.anchor = GridBagConstraints.WEST;

        // Nume Label and TextField
        JLabel labelNume = new JLabel("Nume:");
        labelNume.setFont(new Font("Arial", Font.BOLD, 12));
        labelNume.setForeground(Color.WHITE);
        panelCautareAngajat.add(labelNume, gbc);

        gbc.gridx = 1;
        JTextField numeField = new JTextField(15);
        panelCautareAngajat.add(numeField, gbc);

        // Prenume Label and TextField
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel labelPrenume = new JLabel("Prenume:");
        labelPrenume.setFont(new Font("Arial", Font.BOLD, 12));
        labelPrenume.setForeground(Color.WHITE);
        panelCautareAngajat.add(labelPrenume, gbc);

        gbc.gridx = 1;
        JTextField prenumeField = new JTextField(15);
        panelCautareAngajat.add(prenumeField, gbc);

        // Functie Label and TextField
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel labelFunctie = new JLabel("Funcție:");
        labelFunctie.setFont(new Font("Arial", Font.BOLD, 12));
        labelFunctie.setForeground(Color.WHITE);
        panelCautareAngajat.add(labelFunctie, gbc);

        gbc.gridx = 1;
        JTextField functieField = new JTextField(15);
        panelCautareAngajat.add(functieField, gbc);

        // Buttons Panel (Search and Clear)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setOpaque(false);
        buttonPanel.setBounds(50, 240, 400, 40);
        layeredPane.add(buttonPanel, Integer.valueOf(1));

        // Create buttons
        JButton cautareButton = new JButton("Căutare");
        JButton clearButton = new JButton("Șterge");

        // Button Styling
        Font buttonFont = new Font("Arial", Font.BOLD, 12);
        Color buttonBackground = new Color(116, 0, 0);
        Color buttonForeground = Color.WHITE;

        cautareButton.setFont(buttonFont);
        cautareButton.setBackground(buttonBackground);
        cautareButton.setForeground(buttonForeground);
        cautareButton.setFocusPainted(false);
        cautareButton.setBorderPainted(false);
        cautareButton.setOpaque(true);

        clearButton.setFont(buttonFont);
        clearButton.setBackground(buttonBackground);
        clearButton.setForeground(buttonForeground);
        clearButton.setFocusPainted(false);
        clearButton.setBorderPainted(false);
        clearButton.setOpaque(true);

        // Add buttons with spacing
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(cautareButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(clearButton);
        buttonPanel.add(Box.createHorizontalGlue());

        // Result Text Area with Scroll Pane
        JTextArea resultTextArea = new JTextArea(8, 30); // Reduced height for better fit
        resultTextArea.setEditable(false);
        resultTextArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        resultTextArea.setBackground(new Color(240, 248, 255));
        JScrollPane scrollPane = new JScrollPane(resultTextArea);
        scrollPane.setBounds(50, 300, 400, 120);
        layeredPane.add(scrollPane, Integer.valueOf(1));

        // Search Button Action
        cautareButton.addActionListener(evt -> {
            String nume = numeField.getText();
            String prenume = prenumeField.getText();
            String functie = functieField.getText();

            try (Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/proiect2?useSSL=false&serverTimezone=UTC",
                    "root",
                    "LionelMessieTeo1");
                 CallableStatement stmt = conn.prepareCall("{CALL Ex1(?, ?, ?)}")) {

                stmt.setString(1, nume);
                stmt.setString(2, prenume);
                stmt.setString(3, functie);
                stmt.execute();

                try (ResultSet rs = stmt.getResultSet()) {
                    StringBuilder sb = new StringBuilder();
                    ResultSetMetaData rsmd = rs.getMetaData();
                    int columnCount = rsmd.getColumnCount();

                    while (rs.next()) {
                        for (int i = 1; i <= columnCount; i++) {
                            sb.append(rsmd.getColumnName(i)).append(": ").append(rs.getString(i)).append("\n");
                        }
                        sb.append("\n");
                    }

                    if (sb.length() == 0) {
                        sb.append("Nu au fost găsite rezultate.");
                    }

                    resultTextArea.setText(sb.toString());
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(frameCautareAngajat,
                        "Eroare la conectarea la baza de date:\n" + ex.getMessage(),
                        "Eroare", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        // Clear Button Action
        clearButton.addActionListener(e -> {
            numeField.setText("");
            prenumeField.setText("");
            functieField.setText("");
            resultTextArea.setText("");
        });

        // Add the layered pane to the frame
        frameCautareAngajat.add(layeredPane);

        // Finalize the frame
        frameCautareAngajat.setLocationRelativeTo(null);
        frameCautareAngajat.setVisible(true);
    }
    private static void afiseazaInterfataCautareUtilizator() {
        // Create the main frame
        JFrame frameCautareUtilizator = new JFrame("Căutare Utilizator");
        frameCautareUtilizator.setSize(500, 500);
        frameCautareUtilizator.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameCautareUtilizator.setLayout(null);

        // Load the background GIF
        ImageIcon backgroundIcon = new ImageIcon("C:/Users/pinza/Desktop/FACULTATE/AN2/SEM1/(BD) Baze de date/proiect bd (1) - Copy/proiect bd/hmm.gif");
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setBounds(0, 0, 500, 500);

        // Create a layered pane
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 500, 500);
        layeredPane.add(backgroundLabel, Integer.valueOf(0));

        // Panel for search fields and buttons
        JPanel panelCautareUtilizator = new JPanel();
        panelCautareUtilizator.setLayout(new GridBagLayout());
        panelCautareUtilizator.setOpaque(false);
        panelCautareUtilizator.setBounds(50, 20, 400, 200);
        layeredPane.add(panelCautareUtilizator, Integer.valueOf(1));

        // GridBag constraints for layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5); // Reduced spacing
        gbc.anchor = GridBagConstraints.WEST;

        // Nume Label and TextField
        JLabel labelNume = new JLabel("Nume:");
        labelNume.setFont(new Font("Arial", Font.BOLD, 12));
        labelNume.setForeground(Color.WHITE);
        panelCautareUtilizator.add(labelNume, gbc);

        gbc.gridx = 1;
        JTextField numeField = new JTextField(15);
        panelCautareUtilizator.add(numeField, gbc);

        // Prenume Label and TextField
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel labelPrenume = new JLabel("Prenume:");
        labelPrenume.setFont(new Font("Arial", Font.BOLD, 12));
        labelPrenume.setForeground(Color.WHITE);
        panelCautareUtilizator.add(labelPrenume, gbc);

        gbc.gridx = 1;
        JTextField prenumeField = new JTextField(15);
        panelCautareUtilizator.add(prenumeField, gbc);


        // Buttons Panel (Search and Clear)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setOpaque(false);
        buttonPanel.setBounds(50, 240, 400, 40);
        layeredPane.add(buttonPanel, Integer.valueOf(1));

        // Create buttons
        JButton cautareButton = new JButton("Căutare");
        JButton clearButton = new JButton("Șterge");

        // Button Styling
        Font buttonFont = new Font("Arial", Font.BOLD, 12);
        Color buttonBackground = new Color(116, 0, 0);
        Color buttonForeground = Color.WHITE;

        cautareButton.setFont(buttonFont);
        cautareButton.setBackground(buttonBackground);
        cautareButton.setForeground(buttonForeground);
        cautareButton.setFocusPainted(false);
        cautareButton.setBorderPainted(false);
        cautareButton.setOpaque(true);

        clearButton.setFont(buttonFont);
        clearButton.setBackground(buttonBackground);
        clearButton.setForeground(buttonForeground);
        clearButton.setFocusPainted(false);
        clearButton.setBorderPainted(false);
        clearButton.setOpaque(true);

        // Add buttons with spacing
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(cautareButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(clearButton);
        buttonPanel.add(Box.createHorizontalGlue());

        // Result Text Area with Scroll Pane
        JTextArea resultTextArea = new JTextArea(8, 30); // Reduced height for better fit
        resultTextArea.setEditable(false);
        resultTextArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        resultTextArea.setBackground(new Color(240, 248, 255));
        JScrollPane scrollPane = new JScrollPane(resultTextArea);
        scrollPane.setBounds(50, 300, 400, 120);
        layeredPane.add(scrollPane, Integer.valueOf(1));

        // Search Button Action
        cautareButton.addActionListener(evt -> {
            String nume = numeField.getText();
            String prenume = prenumeField.getText();

            try (Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/proiect2?useSSL=false&serverTimezone=UTC",
                    "root",
                    "LionelMessieTeo1");
                 CallableStatement stmt = conn.prepareCall("{CALL Ex2(?,?)}")) {

                stmt.setString(1, nume);
                stmt.setString(2, prenume);

                stmt.execute();

                try (ResultSet rs = stmt.getResultSet()) {
                    StringBuilder sb = new StringBuilder();
                    ResultSetMetaData rsmd = rs.getMetaData();
                    int columnCount = rsmd.getColumnCount();

                    while (rs.next()) {
                        for (int i = 1; i <= columnCount; i++) {
                            sb.append(rsmd.getColumnName(i)).append(": ").append(rs.getString(i)).append("\n");
                        }
                        sb.append("\n");
                    }

                    if (sb.length() == 0) {
                        sb.append("Nu au fost găsite rezultate.");
                    }

                    resultTextArea.setText(sb.toString());
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(frameCautareUtilizator,
                        "Eroare la conectarea la baza de date:\n" + ex.getMessage(),
                        "Eroare", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        // Clear Button Action
        clearButton.addActionListener(e -> {
            numeField.setText("");
            prenumeField.setText("");
            //emailField.setText("");
            resultTextArea.setText("");
        });

        // Add the layered pane to the frame
        frameCautareUtilizator.add(layeredPane);

        // Finalize the frame
        frameCautareUtilizator.setLocationRelativeTo(null);
        frameCautareUtilizator.setVisible(true);
    }
    private static void afiseazaInterfataAdaugareConcediu() {
        // Crearea ferestrei pentru adăugarea concediului
        JFrame frameAdaugareConcediu = new JFrame("Adăugare Concediu");
        frameAdaugareConcediu.setSize(500, 500);
        frameAdaugareConcediu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameAdaugareConcediu.setLayout(null);

        // Încărcarea unui GIF de fundal
        ImageIcon backgroundIcon = new ImageIcon("C:/Users/pinza/Desktop/FACULTATE/AN2/SEM1/(BD) Baze de date/proiect bd (1) - Copy/proiect bd/hmm.gif"); // Modifică calea
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setBounds(0, 0, 500, 500);  // Setăm dimensiunile ferestrei pentru fundal

        // Crearea unui pane stratificat
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 500, 500);
        layeredPane.add(backgroundLabel, Integer.valueOf(0));  // Adăugăm fundalul în stratul de fundal

        // Crearea unui panou pentru formularul de adăugare
        JPanel panelAdaugareConcediu = new JPanel();
        panelAdaugareConcediu.setLayout(new GridLayout(5, 2, 10, 10));  // GridLayout pentru câmpuri
        panelAdaugareConcediu.setOpaque(false);  // Setăm panoul ca transparent
        panelAdaugareConcediu.setBounds(100, 150, 300, 200);  // Dimensiuni mai mici pentru a lăsa fundalul vizibil
        layeredPane.add(panelAdaugareConcediu, Integer.valueOf(1));  // Adăugăm panoul pe stratul anterior

        // Crearea câmpurilor de text și etichetelor
        JLabel labelNume = new JLabel("Nume:");
        labelNume.setForeground(Color.WHITE);
        JTextField numeField = new JTextField(20);
        panelAdaugareConcediu.add(labelNume);
        panelAdaugareConcediu.add(numeField);

        JLabel labelPrenume = new JLabel("Prenume:");
        labelPrenume.setForeground(Color.WHITE);
        JTextField prenumeField = new JTextField(20);
        panelAdaugareConcediu.add(labelPrenume);
        panelAdaugareConcediu.add(prenumeField);

        JLabel labelDataInceput = new JLabel("Data început:");
        labelDataInceput.setForeground(Color.WHITE);
        JTextField dataInceputField = new JTextField(20);
        panelAdaugareConcediu.add(labelDataInceput);
        panelAdaugareConcediu.add(dataInceputField);

        JLabel labelDataSfarsit = new JLabel("Data sfârșit:");
        labelDataSfarsit.setForeground(Color.WHITE);
        JTextField dataSfarsitField = new JTextField(20);
        panelAdaugareConcediu.add(labelDataSfarsit);
        panelAdaugareConcediu.add(dataSfarsitField);

        // Crearea butonului de adăugare
        JButton realizareButton = new JButton("Realizare");
        panelAdaugareConcediu.add(realizareButton);

        // Acțiunea butonului de adăugare
        realizareButton.addActionListener((e) -> {
            String nume = numeField.getText();
            String prenume = prenumeField.getText();
            String dataInceput = dataInceputField.getText();
            String dataSfarsit = dataSfarsitField.getText();
            inserareConcediu(nume, prenume, dataInceput, dataSfarsit);  // Funcția de inserare în bază de date
        });

        // Adăugarea pane-ului stratificat la frame
        frameAdaugareConcediu.add(layeredPane);

        // Configurarea ferestrei
        frameAdaugareConcediu.setLocationRelativeTo(null);  // Fereastra va fi centrată
        frameAdaugareConcediu.setVisible(true);
    }
    private static void inserareConcediu(String nume, String prenume, String dataInceput, String dataSfarsit) {
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/proiect2?useSSL=false&serverTimezone=UTC",
                "root",
                "LionelMessieTeo1"
        );
             CallableStatement stmt = conn.prepareCall("{CALL InserareConcediu(?, ?, ?, ?)}")) {

            stmt.setString(1, nume);
            stmt.setString(2, prenume);
            stmt.setString(3, dataInceput);
            stmt.setString(4, dataSfarsit);

            stmt.execute();
            JOptionPane.showMessageDialog(null, "Concediu adăugat cu succes!");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Eroare la adăugarea concediului!");
        }
    }
    private static void afiseazaInterfataAdaugaOrar() {
        // Create the frame
        JFrame frameAdaugareOrar = new JFrame("Adăugare Orar");
        frameAdaugareOrar.setSize(500, 500);
        frameAdaugareOrar.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameAdaugareOrar.setLayout(null);

        // Load the background GIF
        ImageIcon backgroundIcon = new ImageIcon("C:/Users/pinza/Desktop/FACULTATE/AN2/SEM1/(BD) Baze de date/proiect bd (1) - Copy/proiect bd/hmm.gif");
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setBounds(0, 0, 500, 500);

        // Create layered pane
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 500, 500);

        // Add background to the lowest layer
        layeredPane.add(backgroundLabel, Integer.valueOf(0));

        // Create form panel with GridBagLayout
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout()); // GridBagLayout for precise control
        formPanel.setOpaque(false); // Make the panel transparent
        formPanel.setBounds(50, 50, 400, 300); // Adjust form position and size

        // Create form fields
        String[] labels = {"Ziua Săptămânii", "Ora Început", "Ora Sfârșit", "Tip Orar", "Medic ID", "Unitate ID"};
        JTextField[] textFields = new JTextField[labels.length];

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Add some space between the components

        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i]);
            label.setForeground(Color.WHITE);

            // Add label
            gbc.gridx = 0;
            gbc.gridy = i;
            formPanel.add(label, gbc);

            textFields[i] = new JTextField(20); // Fixed width for text fields

            // Add text field
            gbc.gridx = 1;
            formPanel.add(textFields[i], gbc);
        }

        // Add buttons
        JButton addButton = new JButton("Adaugă Orar");
        JButton cancelButton = new JButton("Anulează");

        // Create a panel for buttons to align them properly
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); // Make it transparent
        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);

        // Add button panel to the form
        gbc.gridx = 0;
        gbc.gridy = labels.length;
        gbc.gridwidth = 2; // Span both columns for buttons
        formPanel.add(buttonPanel, gbc);

        // Style buttons
        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        addButton.setFont(buttonFont);
        addButton.setBackground(new Color(116, 0, 0));
        addButton.setForeground(Color.WHITE);

        cancelButton.setFont(buttonFont);
        cancelButton.setBackground(new Color(116, 0, 0));
        cancelButton.setForeground(Color.WHITE);

        // Action listeners
        addButton.addActionListener(e -> {
            String ziuaSaptamanii = textFields[0].getText();
            String oraInceput = textFields[1].getText();
            String oraSfarsit = textFields[2].getText();
            String tipOrar = textFields[3].getText();
            String medicId = textFields[4].getText();
            String unitateId = textFields[5].getText();

            adaugareOrar(ziuaSaptamanii, oraInceput, oraSfarsit, tipOrar, medicId, unitateId);
        });

        cancelButton.addActionListener(e -> frameAdaugareOrar.dispose());

        // Add form to layered pane
        layeredPane.add(formPanel, Integer.valueOf(1));

        // Add layered pane to frame
        frameAdaugareOrar.add(layeredPane);

        // Finalize frame
        frameAdaugareOrar.setLocationRelativeTo(null);
        frameAdaugareOrar.setVisible(true);
    }
    private static void adaugareOrar(String ziuaSaptamanii, String oraInceput, String oraSfarsit, String tipOrar, String medicId, String unitateId) {
        Connection connection = null;
        CallableStatement statement = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/proiect2?useSSL=false&serverTimezone=UTC",
                    "root",
                    "LionelMessieTeo1"
            );

            // Prepare and call the stored procedure
            String query = "{ CALL AdaugareOrar(?, ?, ?, ?, ?, ?) }";
            statement = connection.prepareCall(query);

            // Set parameters
            statement.setString(1, ziuaSaptamanii);  // Ziua Săptămânii
            statement.setTime(2, Time.valueOf(oraInceput));  // Ora Început
            statement.setTime(3, Time.valueOf(oraSfarsit));  // Ora Sfârșit
            statement.setString(4, tipOrar);  // Tip Orar
            statement.setInt(5, Integer.parseInt(medicId));  // Medic ID
            statement.setInt(6, Integer.parseInt(unitateId));  // Unitate ID

            statement.execute();
            JOptionPane.showMessageDialog(null, "Orarul a fost adăugat cu succes!");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Eroare la adăugarea orarului!", "Eroare", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    private static void afiseazaInterfataStergereOrar() {
        JFrame frameStergereOrar = new JFrame("Șterge Orar");
        frameStergereOrar.setSize(500, 500); // Păstrăm dimensiunea mare
        frameStergereOrar.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameStergereOrar.setLayout(null);

        // Încărcarea unui GIF de fundal
        ImageIcon backgroundIcon = new ImageIcon("C:/Users/pinza/Desktop/FACULTATE/AN2/SEM1/(BD) Baze de date/proiect bd (1) - Copy/proiect bd/hmm.gif"); // Adaptează calea
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setBounds(0, 0, 500, 500);

        // Panoul principal
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 500, 500);
        layeredPane.add(backgroundLabel, Integer.valueOf(0));

        // Panelul pentru câmpurile de text și butoane
        JPanel panelStergereOrar = new JPanel();
        panelStergereOrar.setLayout(new GridBagLayout());
        panelStergereOrar.setOpaque(false);
        panelStergereOrar.setBounds(45, 50, 400, 200);
        layeredPane.add(panelStergereOrar, Integer.valueOf(1));

        // GridBag constraints pentru alinierea elementelor
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Etichetă și câmp ID Orar
        JLabel idOrarLabel = new JLabel("ID Orar:");
        idOrarLabel.setFont(new Font("Arial", Font.BOLD, 12));
        idOrarLabel.setForeground(Color.WHITE);
        panelStergereOrar.add(idOrarLabel, gbc);

        gbc.gridx = 1;
        JTextField idOrarField = new JTextField(15);
        panelStergereOrar.add(idOrarField, gbc);

        // Panel pentru butonul de ștergere
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setOpaque(false);
        buttonPanel.setBounds(45, 200, 400, 40);
        layeredPane.add(buttonPanel, Integer.valueOf(1));

        JButton stergeOrarDBButton = new JButton("Șterge");
        Font buttonFont = new Font("Arial", Font.BOLD, 12);
        Color buttonBackground = new Color(116, 0, 0);
        Color buttonForeground = Color.WHITE;

        stergeOrarDBButton.setFont(buttonFont);
        stergeOrarDBButton.setBackground(buttonBackground);
        stergeOrarDBButton.setForeground(buttonForeground);
        stergeOrarDBButton.setFocusPainted(false);
        stergeOrarDBButton.setBorderPainted(false);
        stergeOrarDBButton.setOpaque(true);

        // Adăugarea butonului la panel
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(stergeOrarDBButton);
        buttonPanel.add(Box.createHorizontalGlue());

        // Acțiune pentru butonul de ștergere
        stergeOrarDBButton.addActionListener(e -> {
            String idOrarText = idOrarField.getText();
            try {
                int idOrar = Integer.parseInt(idOrarText);
                stergeOrar(idOrar);  // Funcția ta pentru a șterge orarul din DB
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frameStergereOrar,
                        "Te rog să introduci un ID valid.",
                        "Eroare", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Adăugarea panoului stratificat în cadrul principal
        frameStergereOrar.add(layeredPane);

        // Finalizare
        frameStergereOrar.setLocationRelativeTo(null);
        frameStergereOrar.setVisible(true);
    }
    private static void stergeOrar(int idOrar) {
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/proiect2?useSSL=false&serverTimezone=UTC",
                "root",
                "LionelMessieTeo1"
        );
             CallableStatement stmt = conn.prepareCall("{CALL StergereOrar(?)}")) {

            stmt.setInt(1, idOrar);

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Orarul a fost șters cu succes!");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Eroare la ștergerea orarului!");
        }
    }
    private static void afiseazaInterfataStergereConcediu() {
        JFrame frameStergereConcediu = new JFrame("Șterge Concediu");
        frameStergereConcediu.setSize(500, 500); // Păstrăm dimensiunea mare
        frameStergereConcediu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameStergereConcediu.setLayout(null);

        // Încărcarea unui GIF de fundal
        ImageIcon backgroundIcon = new ImageIcon("C:/Users/pinza/Desktop/FACULTATE/AN2/SEM1/(BD) Baze de date/proiect bd (1) - Copy/proiect bd/hmm.gif"); // Adaptează calea
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setBounds(0, 0, 500, 500);

        // Panoul principal
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 500, 500);
        layeredPane.add(backgroundLabel, Integer.valueOf(0));

        // Panelul pentru câmpurile de text și butoane
        JPanel panelStergereConcediu = new JPanel();
        panelStergereConcediu.setLayout(new GridBagLayout());
        panelStergereConcediu.setOpaque(false);
        panelStergereConcediu.setBounds(45, 50, 400, 200);
        layeredPane.add(panelStergereConcediu, Integer.valueOf(1));

        // GridBag constraints pentru alinierea elementelor
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Etichetă și câmp ID Orar
        JLabel idConcediuLabel = new JLabel("ID Concediu:");
        idConcediuLabel.setFont(new Font("Arial", Font.BOLD, 12));
        idConcediuLabel.setForeground(Color.WHITE);
        panelStergereConcediu.add(idConcediuLabel, gbc);

        gbc.gridx = 1;
        JTextField idConcediuField= new JTextField(15);
        panelStergereConcediu.add(idConcediuField, gbc);

        // Panel pentru butonul de ștergere
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setOpaque(false);
        buttonPanel.setBounds(45, 200, 400, 40);
        layeredPane.add(buttonPanel, Integer.valueOf(1));

        JButton stergeConcediuDBButton = new JButton("Șterge");
        Font buttonFont = new Font("Arial", Font.BOLD, 12);
        Color buttonBackground = new Color(116, 0, 0);
        Color buttonForeground = Color.WHITE;

        stergeConcediuDBButton.setFont(buttonFont);
        stergeConcediuDBButton.setBackground(buttonBackground);
        stergeConcediuDBButton.setForeground(buttonForeground);
        stergeConcediuDBButton.setFocusPainted(false);
        stergeConcediuDBButton.setBorderPainted(false);
        stergeConcediuDBButton.setOpaque(true);

        // Adăugarea butonului la panel
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(stergeConcediuDBButton);
        buttonPanel.add(Box.createHorizontalGlue());

        // Acțiune pentru butonul de ștergere
        stergeConcediuDBButton.addActionListener(e -> {
            String idConcediu = idConcediuField.getText();
            try {
                stergeConcediu(Integer.parseInt(idConcediu));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frameStergereConcediu,
                        "Te rog să introduci un ID valid.",
                        "Eroare", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Adăugarea panoului stratificat în cadrul principal
        frameStergereConcediu.add(layeredPane);

        // Finalizare
        frameStergereConcediu.setLocationRelativeTo(null);
        frameStergereConcediu.setVisible(true);
    }
    private static void stergeConcediu(int idConcediu) {
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/proiect2?useSSL=false&serverTimezone=UTC",
                "root",
                "LionelMessieTeo1"
        );
             CallableStatement stmt = conn.prepareCall("{CALL StergereConcediu(?)}")) {

            stmt.setInt(1, idConcediu);

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Concediul a fost șters cu succes!");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Eroare la ștergerea concediului!");
        }
    }


}

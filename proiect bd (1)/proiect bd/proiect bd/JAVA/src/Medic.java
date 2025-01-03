import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class Medic {
    private JTextField numePacientField;
    private JTextField prenumePacientField;
    private JTextField rezultatField;
    private JTextField NumeAnalizaField;
    private JTextField DataField;
    private JTextField SimptomeField;

    public void afiseazaInterfataMedic(String cnp) {
        // Create the frame
        JFrame frameAsistent = new JFrame("Interfață Medic");
        frameAsistent.setSize(500, 500);
        frameAsistent.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameAsistent.setLayout(null); // Absolute layout for layered pane

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
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1, 10, 10)); // 4 rows for buttons (with new one)
        buttonPanel.setOpaque(false); // Transparent background
        buttonPanel.setBounds(95, 100, 400, 200); // Position of the button grid
        buttonPanel.setSize(300, 150);

        // Style for buttons
        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        Color buttonBackground = new Color(116, 0, 0); // Light blue
        Color buttonForeground = Color.WHITE;

        // Create buttons
        JButton dateButton = new JButton("Date Personale");
        JButton orarButton = new JButton("Orar");
        JButton creareraporButton = new JButton("Creare raport medical");
        JButton programariButton = new JButton("Istoric Programari");
        JButton pacientiButton = new JButton("Pacienți");
        JButton rapoarteButton = new JButton("Rapoarte");


        // Add buttons to panel
        JButton[] buttons = {dateButton,orarButton, creareraporButton, pacientiButton, programariButton, rapoarteButton}; // Include the new button
        for (JButton btn : buttons) {
            btn.setFont(buttonFont);
            btn.setBackground(buttonBackground);
            btn.setForeground(buttonForeground);
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            btn.setOpaque(true);
            buttonPanel.add(btn);
        }

        // Add action listeners
        dateButton.addActionListener(e->afiseazaDatePersonale(cnp));
        orarButton.addActionListener(e->afiseazaOrarByCnp(cnp));
        creareraporButton.addActionListener(e -> afiseazaInterfataCreareRaport(cnp));
        programariButton.addActionListener(e -> afiseazaInterfataProgramariByCnp(cnp));
        pacientiButton.addActionListener(e -> afiseazaInterfataPacienti());
        rapoarteButton.addActionListener(e -> afiseazaInterfataRapoarteByCnp(cnp));

        // Add the button panel to the layered pane (higher layer)
        layeredPane.add(buttonPanel, Integer.valueOf(1));

        // Create a "Deconectare" button
        JButton logoutButton = new JButton("Deconectare");
        logoutButton.setFont(buttonFont);
        logoutButton.setBackground(new Color(116, 0, 0)); // Distinct color for logout
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.setBorderPainted(false);
        logoutButton.setOpaque(true);
        logoutButton.setPreferredSize(new Dimension(150, 40)); // Dimensiune buton logout
        logoutButton.setBounds(175, 400, 150, 40); // Poziționare la mijloc, jos

        // Add action listener to close the frame
        logoutButton.addActionListener(e -> frameAsistent.dispose());

        // Add the logout button to the layered pane (higher layer)
        layeredPane.add(logoutButton, Integer.valueOf(1));

        // Add the layered pane to the frame
        frameAsistent.add(layeredPane);

        // Finalize the frame
        frameAsistent.setLocationRelativeTo(null); // Center on screen
        frameAsistent.setVisible(true);
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
    private void afiseazaInterfataCreareRaport(String cnp) {
        // Create the frame
        JFrame frameAnalize = new JFrame("Creare Raport Medical");
        frameAnalize.setSize(500, 500);
        frameAnalize.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameAnalize.setLayout(null); // Absolute layout for layered pane

        // Load the background GIF
        ImageIcon backgroundIcon = new ImageIcon("C:/Users/pinza/Desktop/FACULTATE/AN2/SEM1/(BD) Baze de date/proiect bd (1) - Copy/proiect bd/hmm.gif");
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setBounds(0, 0, 500, 500); // Full-size background

        // Create a layered pane
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 500, 500);

        // Add the background to the lowest layer
        layeredPane.add(backgroundLabel, Integer.valueOf(0));

        // Create a panel for input fields and labels
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(7, 2, 10, 10)); // Grid layout with spacing
        inputPanel.setOpaque(false); // Transparent background
        inputPanel.setBounds(50, 100, 400, 250); // Position of the input grid

        // Initialize text fields (already defined at class level)
        numePacientField = new JTextField();
        prenumePacientField = new JTextField();
        rezultatField = new JTextField();
        NumeAnalizaField = new JTextField();
        DataField = new JTextField();
        SimptomeField = new JTextField();

        // Set properties for text fields (white text on dark background or vice versa)
        Color textColor = Color.BLACK;
        Color backgroundColor = Color.WHITE;
        Font fieldFont = new Font("SansSerif", Font.PLAIN, 14);

        JTextField[] textFields = {
                numePacientField,
                prenumePacientField,
                rezultatField,
                NumeAnalizaField,
                DataField,
                SimptomeField
        };

        for (JTextField field : textFields) {
            field.setForeground(textColor);      // Text color
            field.setBackground(backgroundColor); // Background color
            field.setFont(fieldFont);            // Font style and size
        }

        // Create and style labels
        JLabel numePacientLabel = new JLabel("Nume Pacient:");
        JLabel prenumePacientLabel = new JLabel("Prenume Pacient:");
        JLabel rezultatLabel = new JLabel("Rezultat analiza:");
        JLabel NumeAnalizaLabel = new JLabel("Nume analiza:");
        JLabel DataLabel = new JLabel("Data analiza:");
        JLabel SimptomeLabel = new JLabel("Simptome:");

        JLabel[] labels = {
                numePacientLabel,
                prenumePacientLabel,
                rezultatLabel,
                NumeAnalizaLabel,
                DataLabel,
                SimptomeLabel
        };

        for (JLabel label : labels) {
            label.setForeground(Color.BLACK);    // Label text color
            label.setFont(new Font("SansSerif", Font.BOLD, 14)); // Bold font style
        }

        // Add components to the input panel
        inputPanel.add(numePacientLabel);
        inputPanel.add(numePacientField);

        inputPanel.add(prenumePacientLabel);
        inputPanel.add(prenumePacientField);

        inputPanel.add(rezultatLabel);
        inputPanel.add(rezultatField);

        inputPanel.add(NumeAnalizaLabel);
        inputPanel.add(NumeAnalizaField);

        inputPanel.add(DataLabel);
        inputPanel.add(DataField);

        inputPanel.add(SimptomeLabel);
        inputPanel.add(SimptomeField);

        JButton completareRaportButton = new JButton("Completare raport");
        completareRaportButton.addActionListener(e -> {
            inserareRaportMedical(
                    numePacientField.getText(),
                    prenumePacientField.getText(),
                    rezultatField.getText(),
                    NumeAnalizaField.getText(),
                    DataField.getText(),
                    SimptomeField.getText(),
                    cnp
            );
            frameAnalize.dispose();
        });

        inputPanel.add(new JLabel()); // Empty space for alignment
        inputPanel.add(completareRaportButton);

        layeredPane.add(inputPanel, Integer.valueOf(1));
        frameAnalize.add(layeredPane);
        frameAnalize.setLocationRelativeTo(null);
        frameAnalize.setVisible(true);
    }
    private static void inserareRaportMedical(String numePacient, String prenumePacient, String rezultat, String NumeAnaliza, String data, String simptome, String cnp) {
        String url = "jdbc:mysql://localhost:3306/proiect2"; // Database connection URL
        String username = "root";
        String password = "LionelMessieTeo1";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "CALL CreareRaportMedical(?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, numePacient);
                preparedStatement.setString(2, prenumePacient);
                preparedStatement.setString(3, rezultat);
                preparedStatement.setString(4, NumeAnaliza);
                preparedStatement.setString(5, data);
                preparedStatement.setString(6, simptome);
                preparedStatement.setString(7, cnp);

                preparedStatement.execute();
            }
        } catch (SQLException e) {
            // If an exception occurs, print the error message and show it as a popup
            System.err.println("Error occurred during procedure execution: " + e.getMessage());

            // If it's a custom error raised from the SIGNAL in the stored procedure, show it as a popup
            if (e.getSQLState().equals("45000")) {
                String errorMessage = "Custom error: " + e.getMessage();
                System.err.println(errorMessage); // Print to console
                JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);  // Show popup
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);  // Show general error message
            }
        }
    }
    private void afiseazaInterfataProgramariByCnp(String cnp) {
        // Crearea ferestrei principale
        JFrame framePacienti = new JFrame("Programari");
        framePacienti.setSize(500, 500);
        framePacienti.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        framePacienti.setLayout(null);

        // Încărcarea unui GIF de fundal
        ImageIcon backgroundIcon = new ImageIcon("C:/Users/pinza/Desktop/FACULTATE/AN2/SEM1/(BD) Baze de date/proiect bd (1) - Copy/proiect bd/hmm.gif"); // Modifică calea
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setBounds(0, 0, 500, 500);

        // Crearea unui JLayeredPane pentru fundal și alte componente
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 500, 500);
        layeredPane.add(backgroundLabel, Integer.valueOf(0));

        // Crearea unui JPanel pentru JTextArea
        JPanel panelPacienti = new JPanel();
        panelPacienti.setLayout(new BorderLayout());
        panelPacienti.setOpaque(false); // Transparent
        panelPacienti.setBounds(50, 50, 400, 350); // Dimensiune mai mică pentru a lăsa fundalul vizibil
        layeredPane.add(panelPacienti, Integer.valueOf(1));

        // Crearea unui JTextArea pentru a afișa rezultatele
        JTextArea resultTextArea = new JTextArea(10, 30);
        resultTextArea.setEditable(false);
        resultTextArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        resultTextArea.setBackground(new Color(240, 248, 255));  // Fundal ușor pentru TextArea

        // Adăugarea unui JScrollPane pentru a permite derularea textului
        JScrollPane scrollPane = new JScrollPane(resultTextArea);
        panelPacienti.add(scrollPane, BorderLayout.CENTER);

        // Executarea procedurii de afișare a programărilor
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/proiect2?useSSL=false&serverTimezone=UTC",
                "root",
                "LionelMessieTeo1"
        );
             CallableStatement stmt = conn.prepareCall("{CALL AfisareProgramareByCnp(?)}")) {

            // Setarea parametrului CNP în apelul procedurii
            stmt.setString(1, cnp);

            stmt.execute();
            try (ResultSet rs = stmt.getResultSet()) {
                StringBuilder sb = new StringBuilder();
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnCount = rsmd.getColumnCount();

                // Iterarea prin rezultatele interogării
                while (rs.next()) {
                    // Concatenarea numelui și prenumele pe aceeași linie pentru client și medic
                    String programare = String.format("Programare ID: %s\n" +
                                    "Client: %s %s\n" +  // Client: nume prenume
                                    "Medic: %s %s\n" +  // Medic: nume prenume
                                    "Serviciu: %s\n" +
                                    "Data: %s\n" +
                                    "Durata: %s\n" +
                                    "Parafa: %s\n",
                            rs.getString("programare_id"),
                            rs.getString("client_nume"), rs.getString("client_prenume"), // Client
                            rs.getString("medic_nume"), rs.getString("medic_prenume"),   // Medic
                            rs.getString("serviciu_nume"),
                            rs.getString("data"),
                            rs.getString("durata"),
                            rs.getString("parafa"));
                    sb.append(programare);
                    sb.append("--------------------------------------------------\n");
                }

                // Setarea textului în JTextArea
                resultTextArea.setText(sb.toString().isEmpty() ? "Nu există date de afișat." : sb.toString());
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(framePacienti,
                    "Eroare la preluarea datelor: " + ex.getMessage(),
                    "Eroare", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }

        // Adăugarea JPanel-ului la JLayeredPane
        framePacienti.add(layeredPane);

        // Finalizarea configurării ferestrei
        framePacienti.setLocationRelativeTo(null);
        framePacienti.setVisible(true);
    }
    private void afiseazaInterfataPacienti() {
        // Crearea ferestrei principale
        JFrame framePacienti = new JFrame("Pacienti");
        framePacienti.setSize(500, 500);
        framePacienti.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        framePacienti.setLayout(null);

        // Încărcarea unui GIF de fundal
        ImageIcon backgroundIcon = new ImageIcon("C:/Users/pinza/Desktop/FACULTATE/AN2/SEM1/(BD) Baze de date/proiect bd (1) - Copy/proiect bd/hmm.gif"); // Modifică calea
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setBounds(0, 0, 500, 500);

        // Crearea unui JLayeredPane pentru fundal și alte componente
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 500, 500);
        layeredPane.add(backgroundLabel, Integer.valueOf(0));

        // Crearea unui JPanel pentru JTextArea
        JPanel panelPacienti = new JPanel();
        panelPacienti.setLayout(new BorderLayout());
        panelPacienti.setOpaque(false); // Transparent
        panelPacienti.setBounds(50, 50, 400, 350); // Dimensiune mai mică pentru a lăsa fundalul vizibil
        layeredPane.add(panelPacienti, Integer.valueOf(1));

        // Crearea unui JTextArea pentru a afișa rezultatele
        JTextArea resultTextArea = new JTextArea(10, 30);
        resultTextArea.setEditable(false);
        resultTextArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        resultTextArea.setBackground(new Color(240, 248, 255));  // Fundal ușor pentru TextArea

        // Adăugarea unui JScrollPane pentru a permite derularea textului
        JScrollPane scrollPane = new JScrollPane(resultTextArea);
        panelPacienti.add(scrollPane, BorderLayout.CENTER);

        // Executarea procedurii de afișare a orelor
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/proiect2?useSSL=false&serverTimezone=UTC",
                "root",
                "LionelMessieTeo1"
        );
             CallableStatement stmt = conn.prepareCall("{CALL AfisarePacienti()}")) {
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
            JOptionPane.showMessageDialog(framePacienti,
                    "Eroare la preluarea datelor: " + ex.getMessage(),
                    "Eroare", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }

        // Adăugarea JPanel-ului la JLayeredPane
        framePacienti.add(layeredPane);

        // Finalizarea configurării ferestrei
        framePacienti.setLocationRelativeTo(null);
        framePacienti.setVisible(true);
    }
    private static void afiseazaInterfataRapoarteByCnp(String cnp) {
        // Crearea ferestrei principale
        JFrame frameProgramari = new JFrame("Rapoarte");
        frameProgramari.setSize(500, 500);
        frameProgramari.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameProgramari.setLayout(null);

        // Încărcarea unui GIF de fundal
        ImageIcon backgroundIcon = new ImageIcon("C:/Users/pinza/Desktop/FACULTATE/AN2/SEM1/(BD) Baze de date/proiect bd (1) - Copy/proiect bd/hmm.gif");
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setBounds(0, 0, 500, 500);

        // Crearea unui JLayeredPane pentru fundal și alte componente
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 500, 500);
        layeredPane.add(backgroundLabel, Integer.valueOf(0));

        // Crearea unui JPanel pentru JTextArea
        JPanel panelProgramari = new JPanel();
        panelProgramari.setLayout(new BorderLayout());
        panelProgramari.setOpaque(false); // Transparent
        panelProgramari.setBounds(50, 50, 400, 350); // Dimensiune mai mică pentru a lăsa fundalul vizibil
        layeredPane.add(panelProgramari, Integer.valueOf(1));

        // Crearea unui JTextArea pentru a afișa rezultatele
        JTextArea resultTextArea = new JTextArea(10, 30);
        resultTextArea.setEditable(false);
        resultTextArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        resultTextArea.setBackground(new Color(240, 248, 255));  // Fundal ușor pentru TextArea

        // Adăugarea unui JScrollPane pentru a permite derularea textului
        JScrollPane scrollPane = new JScrollPane(resultTextArea);
        panelProgramari.add(scrollPane, BorderLayout.CENTER);

        // Executarea procedurii de afișare a rapoartelor
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/proiect2?useSSL=false&serverTimezone=UTC",
                "root",
                "LionelMessieTeo1"
        );
             CallableStatement stmt = conn.prepareCall("{CALL AfisareRapoarteByCnp(?)}")) {

            // Setarea parametrului CNP
            stmt.setString(1, cnp);

            // Executarea procedurii
            stmt.execute();

            try (ResultSet rs = stmt.getResultSet()) {
                StringBuilder sb = new StringBuilder();
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnCount = rsmd.getColumnCount();

                // Iterarea prin rezultatele interogării
                while (rs.next()) {
                    // Concatenarea numelui și prenumelui clientului și medicului într-o singură linie
                    String clientNumeComplet = rs.getString("client_nume") + " " + rs.getString("client_prenume");
                    String medicNumeComplet = rs.getString("medic_nume") + " " + rs.getString("medic_prenume");

                    // Construirea textului pentru fiecare raport
                    sb.append("Raport ID: ").append(rs.getInt("raport_id")).append("\n")
                            .append("Client: ").append(clientNumeComplet).append("\n")  // Afișează numele complet al clientului
                            .append("Medicul: ").append(medicNumeComplet).append("\n")  // Afișează numele complet al medicului
                            .append("Rezultatul Analizei: ").append(rs.getString("rezultat_analiza")).append("\n")
                            .append("Numele Analizei: ").append(rs.getString("nume_analiza")).append("\n")
                            .append("Data: ").append(rs.getDate("data")).append("\n")
                            .append("Simptome: ").append(rs.getString("simptome")).append("\n")
                            .append("Diagnostic: ").append(rs.getString("diagnostic")).append("\n")
                            .append("Recomandari: ").append(rs.getString("recomandari")).append("\n")
                            .append("--------------------------------------------------\n");
                }

                // Setarea textului în JTextArea
                resultTextArea.setText(sb.toString().isEmpty() ? "Nu există date de afișat." : sb.toString());
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frameProgramari,
                    "Eroare la preluarea datelor: " + ex.getMessage(),
                    "Eroare", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }

        // Adăugarea JPanel-ului la JLayeredPane
        frameProgramari.add(layeredPane);

        // Finalizarea configurării ferestrei
        frameProgramari.setLocationRelativeTo(null);
        frameProgramari.setVisible(true);
    }



}

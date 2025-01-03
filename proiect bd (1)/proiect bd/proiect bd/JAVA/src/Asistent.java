import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
public class Asistent {
    void afiseazaInterfataAsistent(String cnp) {
        // Create the frame
        JFrame frameAsistent = new JFrame("Interfață Asistent");
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
        JButton dateButton = new JButton("Vizualizare Date Personale");
        JButton analizeButton = new JButton("Completare raport medical");
        JButton pacientiButton = new JButton("Vizualizare Pacienți");
        JButton rapoarteButton = new JButton("Vizualizare Rapoarte");  // New button for rapoarte

        // Add buttons to panel
        JButton[] buttons = {dateButton, analizeButton, pacientiButton, rapoarteButton}; // Include the new button
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
        dateButton.addActionListener(e -> afiseazaDatePersonale(cnp));
        analizeButton.addActionListener(e -> afiseazaInterfataCompletareRaport());
        pacientiButton.addActionListener(e -> afiseazaInterfataPacienti());
        rapoarteButton.addActionListener(e -> afiseazaInterfataRapoarte());  // Action for new button

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
    private void afiseazaInterfataCompletareRaport() {
        // Create the frame
        JFrame frameAnalize = new JFrame("Completare Raport Medical");
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

        // Style for labels and fields
        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font fieldFont = new Font("Arial", Font.PLAIN, 14);
        Color fieldBackground = Color.WHITE;
        Color labelForeground = Color.BLACK;

        // Create labels and text fields
        JLabel numePacientLabel = new JLabel("Nume Pacient:");
        JTextField numePacientField = new JTextField();

        JLabel prenumePacientLabel = new JLabel("Prenume Pacient:");
        JTextField prenumePacientField = new JTextField();

        JLabel idRaportLabel = new JLabel("Raport medical nr:");
        JTextField idRaportField = new JTextField();

        JLabel diagnosticLabel = new JLabel("Diagnostic:");
        JTextField diagnosticAnalizaField = new JTextField();

        JLabel recomandariLabel = new JLabel("Recomandări:");
        JTextField recomandariAnalizaField = new JTextField();

        // Style labels
        JLabel[] labels = {numePacientLabel, prenumePacientLabel, idRaportLabel, diagnosticLabel, recomandariLabel};
        for (JLabel lbl : labels) {
            lbl.setFont(labelFont);
            lbl.setForeground(labelForeground);
        }

        // Style text fields
        JTextField[] fields = {numePacientField, prenumePacientField, idRaportField, diagnosticAnalizaField, recomandariAnalizaField};
        for (JTextField field : fields) {
            field.setFont(fieldFont);
            field.setBackground(fieldBackground);
        }

        // Create the "Completare raport" button
        JButton completareRaportButton = new JButton("Completare raport");
        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        Color buttonBackground = new Color(116, 0, 0);
        Color buttonForeground = Color.WHITE;
        completareRaportButton.setFont(buttonFont);
        completareRaportButton.setBackground(buttonBackground);
        completareRaportButton.setForeground(buttonForeground);
        completareRaportButton.setFocusPainted(false);
        completareRaportButton.setBorderPainted(false);
        completareRaportButton.setOpaque(true);

        // Add components to the input panel
        inputPanel.add(numePacientLabel);
        inputPanel.add(numePacientField);

        inputPanel.add(prenumePacientLabel);
        inputPanel.add(prenumePacientField);

        inputPanel.add(idRaportLabel);
        inputPanel.add(idRaportField);

        inputPanel.add(diagnosticLabel);
        inputPanel.add(diagnosticAnalizaField);

        inputPanel.add(recomandariLabel);
        inputPanel.add(recomandariAnalizaField);

        inputPanel.add(new JLabel()); // Empty cell for spacing
        inputPanel.add(completareRaportButton);

        // Add action listener to the button
        completareRaportButton.addActionListener(e -> adaugaCompletariRaport(numePacientField, prenumePacientField, idRaportField, diagnosticAnalizaField, recomandariAnalizaField));

        layeredPane.add(inputPanel, Integer.valueOf(1));
        frameAnalize.add(layeredPane);
        frameAnalize.setLocationRelativeTo(null); // Center on screen
        frameAnalize.setVisible(true);
    }
    private void adaugaCompletariRaport(JTextField numePacientField, JTextField prenumePacientField,JTextField idRaportField, JTextField diagnosticAnalizaField, JTextField recomandariAnalizaField) {
        try {
            // Step 1: Collect Input
            String numePacient = numePacientField.getText().trim();
            String prenumePacient = prenumePacientField.getText().trim();
            String idRaportStr = idRaportField.getText().trim();
            String diagnosticAnaliza = diagnosticAnalizaField.getText().trim();
            String recomandariAnaliza = recomandariAnalizaField.getText().trim();

            // Step 2: Validate Input
            if (numePacient.isEmpty() || prenumePacient.isEmpty() || idRaportStr.isEmpty()
                    || diagnosticAnaliza.isEmpty() || recomandariAnaliza.isEmpty()) {
                showError("Toate câmpurile trebuie completate!");
                return;
            }

            int idRaport;
            try {
                idRaport = Integer.parseInt(idRaportStr);
            } catch (NumberFormatException e) {
                showError("ID Raport invalid. Trebuie să fie un număr întreg.");
                return;
            }

            // Step 3: Database Interaction
            try (Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/proiect2?useSSL=false&serverTimezone=UTC",
                    "root",
                    "LionelMessieTeo1"
            );
                 CallableStatement stmt = conn.prepareCall("{CALL CompletareRaport(?, ?, ?, ?, ?)}")) {

                stmt.setString(1, numePacient);
                stmt.setString(2, prenumePacient);
                stmt.setInt(3, idRaport);
                stmt.setString(4, diagnosticAnaliza);
                stmt.setString(5, recomandariAnaliza);

                stmt.execute(); // Execute the stored procedure

                // Step 4: Handle Success
                showSuccess("Raportul a fost completat cu succes!");
                resetFields(numePacientField, prenumePacientField, idRaportField, diagnosticAnalizaField, recomandariAnalizaField);
            } catch (SQLException ex) {
                ex.printStackTrace();
                showError("Eroare la completarea raportului: " + ex.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            showError("O eroare neașteptată a apărut: " + e.getMessage());
        }
    }
    private void resetFields(JTextField numePacientField, JTextField prenumePacientField, JTextField idRaportField, JTextField diagnosticAnalizaField, JTextField recomandariAnalizaField) {
        numePacientField.setText("");
        prenumePacientField.setText("");
        idRaportField.setText("");
        diagnosticAnalizaField.setText("");
        recomandariAnalizaField.setText("");
    }
    private void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(null, message, "Success", JOptionPane.INFORMATION_MESSAGE);
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
    private static void afiseazaInterfataRapoarte() {
        // Crearea ferestrei principale
        JFrame frameProgramari = new JFrame("Rapoarte");
        frameProgramari.setSize(500, 500);
        frameProgramari.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameProgramari.setLayout(null);
        // Încărcarea unui GIF de fundal
        ImageIcon backgroundIcon = new ImageIcon("C:/Users/pinza/Desktop/FACULTATE/AN2/SEM1/(BD) Baze de date/proiect bd (1) - Copy/proiect bd/hmm.gif"); // Modifică calea
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

        // Executarea procedurii de afișare a orelor
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/proiect2?useSSL=false&serverTimezone=UTC",
                "root",
                "LionelMessieTeo1"
        );
             CallableStatement stmt = conn.prepareCall("{CALL AfisareRapoarte()}")) {

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

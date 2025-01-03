import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.math.BigDecimal;

public class Financiar {
    void afiseazaInterfataFinanciar(String cnp) {
        // Create the frame
        JFrame frameFinanciar = new JFrame("Interfață Financiar");
        frameFinanciar.setSize(500, 500);
        frameFinanciar.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameFinanciar.setLayout(null); // Absolute layout for layered pane

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
        buttonPanel.setLayout(new GridLayout(4, 2, 10, 10)); // Grid layout with spacing
        buttonPanel.setOpaque(false); // Transparent background
        buttonPanel.setBounds(45, 70, 400, 300); // Position of the button grid

        // Style for buttons
        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        Color buttonBackground = new Color(116, 0, 0);
        Color buttonForeground = Color.WHITE;

        // Create buttons
        JButton dateButton = new JButton("Vizualizare Date Personale");
        JButton profitButton = new JButton("Vizualizare profit unitate");
        JButton medicButton = new JButton("Vizualizare Profit medic");
        JButton salarangajatButton = new JButton("Vizualizare Salar angajat");

        // Add buttons to panel
        JButton[] buttons = {dateButton,  profitButton, medicButton, salarangajatButton};
        for (JButton btn : buttons) {
            btn.setFont(buttonFont);
            btn.setBackground(buttonBackground);
            btn.setForeground(buttonForeground);
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            btn.setOpaque(true);
            buttonPanel.add(btn);
        }

        // Add action listeners (optional)
        profitButton.addActionListener(e -> afiseazaInterfataAfisareProfitUnitate());
        dateButton.addActionListener(e -> afiseazaDatePersonale(cnp));
        salarangajatButton.addActionListener(e->afiseazaInterfataSalarById());
        medicButton.addActionListener(e -> afiseazaInterfataProfitMedic());


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
        logoutButton.setBounds(175, 400, 150, 40); // Position of the button (below panel)

// Add action listener to close the frame
        logoutButton.addActionListener(e -> frameFinanciar.dispose());

// Add the logout button to the layered pane (higher layer)
        layeredPane.add(logoutButton, Integer.valueOf(1));

        // Add the layered pane to the frame
        frameFinanciar.add(layeredPane);

        // Finalize the frame
        frameFinanciar.setLocationRelativeTo(null); // Center on screen
        frameFinanciar.setVisible(true);
    }
    private void afiseazaInterfataSalarById() {
        // Create the frame
        JFrame frameAfisareSalar = new JFrame("Vizualizare salar");
        frameAfisareSalar.setSize(500, 500);
        frameAfisareSalar.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameAfisareSalar.setLayout(null);

        // Load the background GIF
        ImageIcon backgroundIcon = new ImageIcon("C:/Users/pinza/Desktop/FACULTATE/AN2/SEM1/(BD) Baze de date/proiect bd (1) - Copy/proiect bd/hmm.gif");
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setBounds(0, 0, 500, 500);

        // Create layered pane
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 500, 500);

        // Add background to the lowest layer
        layeredPane.add(backgroundLabel, Integer.valueOf(0));

        // Create form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(3, 1, 10, 10)); // Grid layout for fields and labels
        formPanel.setOpaque(false);
        formPanel.setBounds(45, 50, 400, 200);

        // Create form fields
        String[] labels = {"Angajat ID"};
        JTextField[] textFields = new JTextField[labels.length];

        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i]);
            label.setForeground(Color.WHITE);
            formPanel.add(label);

            textFields[i] = new JTextField();
            formPanel.add(textFields[i]);
        }

        // Add buttons
        JButton addButton = new JButton("Vizualizeaza");
        JButton cancelButton = new JButton("Anulează");

        formPanel.add(addButton);
        formPanel.add(cancelButton);

        // Style buttons
        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        addButton.setFont(buttonFont);
        addButton.setSize(150,50);
        addButton.setBackground(new Color(116, 0, 0));
        addButton.setForeground(Color.WHITE);

        cancelButton.setFont(buttonFont);
        cancelButton.setSize(150,50);
        cancelButton.setBackground(new Color(116, 0, 0));
        cancelButton.setForeground(Color.WHITE);

        // Action listeners
        addButton.addActionListener(e -> {
            String utilizatorId = textFields[0].getText();

            afisareSalar(utilizatorId);
        });

        cancelButton.addActionListener(e -> frameAfisareSalar.dispose());

        // Add form to layered pane
        layeredPane.add(formPanel, Integer.valueOf(1));

        // Add layered pane to frame
        frameAfisareSalar.add(layeredPane);

        // Finalize frame
        frameAfisareSalar.setLocationRelativeTo(null);
        frameAfisareSalar.setVisible(true);
    }
    private void afisareSalar(String utilizatorId) {
        Connection connection = null;
        CallableStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/proiect2?useSSL=false&serverTimezone=UTC",
                    "root",
                    "LionelMessieTeo1"
            );

            // Prepare and call the stored procedure
            String query = "{ CALL AfisareSalarByAngajatId(?) }";
            statement = connection.prepareCall(query);

            // Set parameters
            statement.setInt(1, Integer.parseInt(utilizatorId));  // Angajat ID

            // Execute the procedure
            boolean hasResults = statement.execute();

            // Check if the procedure returned results
            if (hasResults) {
                resultSet = statement.getResultSet();

                if (resultSet.next()) {
                    // Retrieve the salary info
                    String salaryInfo = resultSet.getString(1);

                    // Display the salary in a new dialog
                    JOptionPane.showMessageDialog(null, salaryInfo, "Salariu Angajat", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Nu s-au găsit informații pentru acest ID.", "Informație", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Eroare la vizualizare!", "Eroare", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
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
    private void afiseazaInterfataAfisareProfitUnitate() {
        JFrame frameAfisareProfit = new JFrame("Afisare Profit Unitate");
        frameAfisareProfit.setSize(500, 500);
        frameAfisareProfit.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameAfisareProfit.setLayout(null); // Absolute layout for layered pane

        // Load the background GIF
        ImageIcon backgroundIcon = new ImageIcon("C:/Users/pinza/Desktop/FACULTATE/AN2/SEM1/(BD) Baze de date/proiect bd (1) - Copy/proiect bd/hmm.gif");
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setBounds(0, 0, 500, 500); // Full-size background

        // Create a layered pane
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 500, 500);

        // Add the background to the lowest layer
        layeredPane.add(backgroundLabel, Integer.valueOf(0));

        // Create a panel for input and button
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 1, 10, 10)); // 3 rows for label, input, and result
        inputPanel.setOpaque(false); // Transparent background
        inputPanel.setBounds(50, 150, 400, 150); // Position the panel

        // Style for components
        Font inputFont = new Font("Arial", Font.BOLD, 14);
        Color buttonBackground = new Color(116, 0, 0);
        Color buttonForeground = Color.WHITE;

        // Create input components
        JLabel idLabel = new JLabel("ID Unitate:", SwingConstants.CENTER);
        idLabel.setFont(inputFont);
        idLabel.setForeground(buttonForeground);

        JTextField idUnitateField = new JTextField();
        idUnitateField.setFont(inputFont);
        idUnitateField.setBorder(BorderFactory.createLineBorder(buttonBackground));

        JLabel resultLabel = new JLabel("", SwingConstants.CENTER);
        resultLabel.setFont(inputFont);
        resultLabel.setForeground(buttonForeground);

        // Add components to panel
        inputPanel.add(idLabel);
        inputPanel.add(idUnitateField);
        inputPanel.add(resultLabel);

        // Add the input panel to the layered pane
        layeredPane.add(inputPanel, Integer.valueOf(1));

        // Create and style the button
        JButton afiseazaButton = new JButton("Afiseaza");
        afiseazaButton.setFont(inputFont);
        afiseazaButton.setBackground(buttonBackground);
        afiseazaButton.setForeground(buttonForeground);
        afiseazaButton.setFocusPainted(false);
        afiseazaButton.setBorderPainted(false);
        afiseazaButton.setOpaque(true);
        afiseazaButton.setBounds(175, 350, 150, 40); // Position of the button

        // Add the button to the layered pane
        layeredPane.add(afiseazaButton, Integer.valueOf(1));

        // Add action listener to the button
        afiseazaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String idUnitate = idUnitateField.getText();

                if (idUnitate.isEmpty()) {
                    JOptionPane.showMessageDialog(frameAfisareProfit, "Introduceti un ID valid.", "Eroare", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try (Connection connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/proiect2?useSSL=false&serverTimezone=UTC",
                        "root",
                        "LionelMessieTeo1"
                )) {
                    String callProcedure = "{ CALL CalculProfitUnitate(?, ?) }";

                    try (CallableStatement callableStatement = connection.prepareCall(callProcedure)) {
                        callableStatement.setInt(1, Integer.parseInt(idUnitate));
                        callableStatement.registerOutParameter(2, Types.DECIMAL);

                        callableStatement.execute();

                        BigDecimal profit = callableStatement.getBigDecimal(2);

                        // Display the profit in the resultLabel
                        resultLabel.setText("Profit: " + profit + " RON");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frameAfisareProfit, "Eroare la interogare: " + ex.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frameAfisareProfit, "ID-ul trebuie sa fie un numar intreg.", "Eroare", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Add the layered pane to the frame
        frameAfisareProfit.add(layeredPane);

        // Finalize the frame
        frameAfisareProfit.setLocationRelativeTo(null); // Center on screen
        frameAfisareProfit.setVisible(true);
    }

    private void afiseazaInterfataProfitMedic() {
        JFrame frameProfitMedic = new JFrame("Profit Medic");
        frameProfitMedic.setSize(400, 200);
        frameProfitMedic.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panelProfitMedic = new JPanel();
        frameProfitMedic.add(panelProfitMedic);
        panelProfitMedic.setLayout(new BorderLayout());

        // Adăugați JTextArea în partea centrală a panelului pentru afișarea rezultatelor
        JTextArea resultTextArea = new JTextArea();
        panelProfitMedic.add(new JScrollPane(resultTextArea), BorderLayout.CENTER);

        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/proiect2?useSSL=false&serverTimezone=UTC",
                "root",
                "LionelMessieTeo1"
        );
             CallableStatement stmt = conn.prepareCall("{CALL calcul_profit_per_medic_procedure()}")) {

            stmt.execute();

            try (ResultSet rs = stmt.getResultSet()) {
                if (rs != null) {
                    StringBuilder sb = new StringBuilder();
                    while (rs.next()) {
                        sb.append(rs.getString("ProfitPerMedic"));
                        sb.append("\n");
                    }
                    resultTextArea.setText(sb.toString());
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        frameProfitMedic.setVisible(true);
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
}

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class SuperAdministrator {
    void afiseazaInterfataSuperAdministrator(String cnp) {
        // Create the frame
        JFrame frameAsistent = new JFrame("ADMIN");
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

        // Create a panel for buttons with GridLayout
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 2, 10, 10)); // 3 rows, 2 columns (adjust the number as needed)
        buttonPanel.setOpaque(false); // Transparent background
        buttonPanel.setBounds(45, 100, 400, 200); // Position of the button grid

        // Define the common button styling (matching the one from `afiseazaAdaugareUtilizator`)
        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        Color buttonBackground = new Color(116, 0, 0); // Dark red color, same as first example
        Color buttonForeground = Color.WHITE;

        // Create buttons
        JButton dateButton = new JButton("Date Personale");
        JButton VizualizariButton = new JButton("Vizualizeaza");
        JButton ModificariButton = new JButton("Modifica");
        JButton StergeriButton = new JButton("Sterge");
        JButton AdaugariButton = new JButton("Adauga");
        JButton AdminButton = new JButton("Admin Button");

        // Create a button array
        JButton[] buttons = {dateButton, VizualizariButton, ModificariButton, StergeriButton, AdaugariButton, AdminButton};

        // Apply styling to all buttons
        for (JButton btn : buttons) {
            btn.setFont(buttonFont);
            btn.setBackground(buttonBackground);
            btn.setForeground(buttonForeground);
            btn.setFocusPainted(false);

            // Add hover effect for border only (optional)
            btn.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    // Implement hover effect if needed (border change, etc.)
                    // btn.setBorder(BorderFactory.createLineBorder(borderColor, 3)); // Example
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    // Revert back to the original border (if hover effect is implemented)
                    // btn.setBorder(BorderFactory.createLineBorder(borderColor, 2)); // Example
                }
            });

            // Add buttons to the button panel (which is using GridLayout)
            buttonPanel.add(btn);
        }

        // Add action listeners
        dateButton.addActionListener(e -> afiseazaDatePersonale(cnp));
        VizualizariButton.addActionListener(e -> afiseazaInterfataVizualizari());
        StergeriButton.addActionListener(e -> afiseazaInterfataStergeri());
        AdaugariButton.addActionListener(e -> afiseazaInterfataAdaugari());
        AdminButton.addActionListener(e -> afiseazaAdminButton(cnp));

        // Add the button panel to the layered pane (higher layer)
        layeredPane.add(buttonPanel, Integer.valueOf(1));

        // Create a "Deconectare" button
        JButton logoutButton = new JButton("Deconectare");
        logoutButton.setFont(buttonFont);
        logoutButton.setBackground(buttonBackground); // Same background color
        logoutButton.setForeground(buttonForeground);
        logoutButton.setFocusPainted(false);
        logoutButton.setBorderPainted(false);
        logoutButton.setOpaque(true);
        logoutButton.setPreferredSize(new Dimension(150, 40)); // Consistent size
        logoutButton.setBounds(175, 400, 150, 40); // Positioned at center, bottom

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
    private void afiseazaAdminButton(String cnp) {
        // Create the frame
        JFrame frameAdmin = new JFrame("ADMIN");
        frameAdmin.setSize(500, 500);
        frameAdmin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameAdmin.setLayout(null); // Absolute layout for layered pane

        // Load the background GIF
        ImageIcon backgroundIcon = new ImageIcon("C:/Users/pinza/Desktop/FACULTATE/AN2/SEM1/(BD) Baze de date/proiect bd (1) - Copy/proiect bd/hmm.gif");
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setBounds(0, 0, 500, 500); // Full-size background

        // Create a layered pane
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 500, 500);

        // Add the background to the lowest layer
        layeredPane.add(backgroundLabel, Integer.valueOf(0));

        // Create a panel for buttons and input field
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 1, 10, 10)); // 5 buttons + 1 input field
        buttonPanel.setOpaque(false); // Transparent background
        buttonPanel.setBounds(95, 100, 400, 250); // Position of the button grid
        buttonPanel.setSize(300, 250);

        // Style for buttons and input field
        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        Color buttonBackground = new Color(116, 0, 0); // Light blue
        Color buttonForeground = Color.WHITE;

        // Create the "Tastați clearance level" label and input field
        JLabel clearanceLabel = new JLabel("Tastați clearance level:");
        clearanceLabel.setFont(buttonFont);
        clearanceLabel.setForeground(Color.WHITE);
        JTextField clearanceField = new JTextField();
        clearanceField.setFont(buttonFont);
        clearanceField.setPreferredSize(new Dimension(200, 30));

        // Add label and text field to the button panel
        buttonPanel.add(clearanceLabel);
        buttonPanel.add(clearanceField);

        // Add the button panel and logout button to the layered pane
        layeredPane.add(buttonPanel, Integer.valueOf(1));

        // Create the action for the clearance field input
        clearanceField.addActionListener(e -> {
            String clearanceLevel = clearanceField.getText().trim();

            // Based on the clearance level input, call different functions
            if (clearanceLevel.equals("medic")) {
                Medic medic = new Medic();
                medic.afiseazaInterfataMedic(cnp);
            } else if (clearanceLevel.equals("asistent")) {
                Asistent asistent = new Asistent();
                asistent.afiseazaInterfataAsistent(cnp);
            } else if (clearanceLevel.equals("receptioner")) {
                Receptioner receptioner = new Receptioner();
                receptioner.afiseazaInterfataReceptioner(cnp);
            } else if (clearanceLevel.equals("financiar")) {
               Financiar financiar = new Financiar();
               financiar.afiseazaInterfataFinanciar(cnp);
            } else if (clearanceLevel.equals("resurse umane")) {
                ResurseUmane resur = new ResurseUmane();
                resur.afiseazaInterfataResurseUmane(cnp);
            }  else {
                JOptionPane.showMessageDialog(frameAdmin, "Clearance level invalid!");
            }
        });

        // Add the layered pane to the frame
        frameAdmin.add(layeredPane);

        // Finalize the frame
        frameAdmin.setLocationRelativeTo(null); // Center on screen
        frameAdmin.setVisible(true);
    }
    private void afiseazaInterfataVizualizari() {
        // Create the frame
        JFrame frameVizualizari = new JFrame("Interfață Vizualizări");
        frameVizualizari.setSize(500, 500);
        frameVizualizari.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameVizualizari.setLayout(null); // Absolute layout for layered pane

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
        buttonPanel.setLayout(new GridLayout(4, 1, 10, 10)); // 4 rows for buttons
        buttonPanel.setOpaque(false); // Transparent background
        buttonPanel.setBounds(95, 100, 400, 300); // Position of the button grid
        buttonPanel.setSize(300, 300);

        // Style for buttons
        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        Color buttonBackground = new Color(116, 0, 0); // Light blue
        Color buttonForeground = Color.WHITE;

        // Create the buttons
        JButton button1 = new JButton("Clienti/Pacienti");
        JButton button2 = new JButton("Unitati");
        JButton button3 = new JButton("Angajati");
        JButton button4 = new JButton("Utilizatori");

        // Add buttons to the panel
        JButton[] buttons = {button1, button2, button3, button4};
        for (JButton btn : buttons) {
            btn.setFont(buttonFont);
            btn.setBackground(buttonBackground);
            btn.setForeground(buttonForeground);
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            btn.setOpaque(true);
            buttonPanel.add(btn);
        }

        // Add action listeners to buttons
        button1.addActionListener(e -> afiseazaInterfataPacienti());
        button2.addActionListener(e -> afiseazaInterfataUnitati());
        button3.addActionListener(e -> afiseazaInterfataAngajati());
        button4.addActionListener(e -> afiseazaInterfataUtilizatori());

        // Add the button panel to the layered pane (higher layer)
        layeredPane.add(buttonPanel, Integer.valueOf(1));

        // Add the layered pane to the frame
        frameVizualizari.add(layeredPane);

        // Finalize the frame
        frameVizualizari.setLocationRelativeTo(null); // Center the frame on screen
        frameVizualizari.setVisible(true);
    }
    private void afiseazaInterfataStergeri() {
        // Create the frame
        JFrame frameStergeri = new JFrame("Interfață Stergeri");
        frameStergeri.setSize(500, 500);
        frameStergeri.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameStergeri.setLayout(null); // Absolute layout for layered pane
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
        buttonPanel.setLayout(new GridLayout(4, 1, 10, 10)); // 4 rows for buttons
        buttonPanel.setOpaque(false); // Transparent background
        buttonPanel.setBounds(95, 100, 400, 300); // Position of the button grid
        buttonPanel.setSize(300, 300);
        // Style for buttons
        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        Color buttonBackground = new Color(116, 0, 0); // Light blue
        Color buttonForeground = Color.WHITE;

        // Create the buttons
        JButton button1 = new JButton("Stergere Client/Pacient");
        JButton button2 = new JButton("Stergere Unitate");
        JButton button3 = new JButton("Stergere Angajat");
        JButton button4 = new JButton("Stergere Utilizator");

        // Add buttons to the panel
        JButton[] buttons = {button1, button2, button3, button4};
        for (JButton btn : buttons) {
            btn.setFont(buttonFont);
            btn.setBackground(buttonBackground);
            btn.setForeground(buttonForeground);
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            btn.setOpaque(true);
            buttonPanel.add(btn);
        }

        // Add action listeners to buttons
        button1.addActionListener(e -> afiseazaStergereClient());
        button2.addActionListener(e -> afiseazaStergereUnitate());
        button3.addActionListener(e -> afiseazaStergereAngajat());
        button4.addActionListener(e -> afiseazaStergereUtilizator());

        // Add the button panel to the layered pane (higher layer)
        layeredPane.add(buttonPanel, Integer.valueOf(1));

        // Add the layered pane to the frame
        frameStergeri.add(layeredPane);

        // Finalize the frame
        frameStergeri.setLocationRelativeTo(null); // Center the frame on screen
        frameStergeri.setVisible(true);
    }
    private void afiseazaInterfataAdaugari() {
        // Create the frame
        JFrame frameAdaugari = new JFrame("Interfață Adăugări");
        frameAdaugari.setSize(500, 500);
        frameAdaugari.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameAdaugari.setLayout(null); // Absolute layout for layered pane

        // Load the background GIF
        ImageIcon backgroundIcon = new ImageIcon("C:/Users/pinza/Desktop/FACULTATE/AN2/SEM1/(BD) Baze de date/proiect bd (1) - Copy/proiect bd/hmm.gif");
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setBounds(0, 0, 500, 500); // Full-size background

        // Create a layered pane
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 500, 500);

        // Add the background to the lowest layer
        layeredPane.add(backgroundLabel, Integer.valueOf(0));

        // Create buttons
        JButton button1 = new JButton("Adaugă Client");
        JButton button2 = new JButton("Adăugare Unitate");
        JButton button3 = new JButton("Adăugare Angajat");
        JButton button4 = new JButton("Adaugă Utilizator");

        // Button styling (same as in afiseazaAdaugareUtilizator())
        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        Color buttonBackground = new Color(116, 0, 0); // Deep red
        Color buttonForeground = Color.WHITE;
        //Color borderColor = Color.WHITE;

        JButton[] buttons = {button1, button2, button3, button4};
        int yPosition = 100;

        for (JButton btn : buttons) {
            btn.setFont(buttonFont);
            btn.setBackground(buttonBackground);
            btn.setForeground(buttonForeground);
            btn.setFocusPainted(false);
            //btn.setBorder(BorderFactory.createLineBorder(borderColor, 2)); // Subtle border effect
            btn.setBounds(95, yPosition, 300, 50); // Position buttons vertically
            yPosition += 70;

            // Add hover effect for border only
            btn.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    //btn.setBorder(BorderFactory.createLineBorder(borderColor, 3)); // Thicker border on hover
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    //btn.setBorder(BorderFactory.createLineBorder(borderColor, 2)); // Normal border
                }
            });

            // Add buttons to the layered pane
            layeredPane.add(btn, Integer.valueOf(1));
        }

        // Add action listeners
        //button1.addActionListener(e -> afiseazaInterfataInregistrareClient());
        button2.addActionListener(e -> afiseazaAdaugareUnitate());
        button3.addActionListener(e -> afiseazaAdaugareAngajat());
        button4.addActionListener(e -> afiseazaAdaugareUtilizator());

        // Add the layered pane to the frame
        frameAdaugari.add(layeredPane);

        // Finalize the frame
        frameAdaugari.setLocationRelativeTo(null); // Center the frame on screen
        frameAdaugari.setVisible(true);
    }
    private void afiseazaStergereClient() {
        // Create the frame
        JFrame frameStergereClient = new JFrame("Interfață Stergere Client");
        frameStergereClient.setSize(500, 500);
        frameStergereClient.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameStergereClient.setLayout(null); // Absolute layout for layered pane

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
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 10)); // 3 rows for buttons
        buttonPanel.setOpaque(false); // Transparent background
        buttonPanel.setBounds(45, 100, 400, 200); // Position of the button grid

        // Style for buttons
        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        Color buttonBackground = new Color(116, 0, 0); // Light blue
        Color buttonForeground = Color.WHITE;

        // Create labels and text fields
        JLabel clientIdLabel = new JLabel("Tastați ID-ul clientului:");
        JTextField clientIdField = new JTextField();
        clientIdLabel.setFont(buttonFont);
        clientIdField.setFont(buttonFont);

        // Create the delete button
        JButton deleteButton = new JButton("Șterge Client");
        deleteButton.setFont(buttonFont);
        deleteButton.setBackground(buttonBackground);
        deleteButton.setForeground(buttonForeground);
        deleteButton.setFocusPainted(false);
        deleteButton.setBorderPainted(false);
        deleteButton.setOpaque(true);

        // Add action listener to the delete button
        deleteButton.addActionListener(e -> {
            // Get client ID from the text field
            String clientIdText = clientIdField.getText();
            if (!clientIdText.isEmpty()) {
                try {
                    int clientId = Integer.parseInt(clientIdText);
                    // Call the stored procedure to delete the client
                    stergereClient(clientId);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frameStergereClient, "ID-ul clientului trebuie să fie un număr valid!", "Eroare", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frameStergereClient, "Vă rugăm să introduceți un ID de client!", "Eroare", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Add components to the panel
        buttonPanel.add(clientIdLabel);
        buttonPanel.add(clientIdField);
        buttonPanel.add(deleteButton);

        // Add the button panel to the layered pane (higher layer)
        layeredPane.add(buttonPanel, Integer.valueOf(1));

        // Add the layered pane to the frame
        frameStergereClient.add(layeredPane);

        // Finalize the frame
        frameStergereClient.setLocationRelativeTo(null); // Center on screen
        frameStergereClient.setVisible(true);
    }
    private void stergereClient(int clientId) {
        Connection connection = null;
        CallableStatement statement = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/proiect2?useSSL=false&serverTimezone=UTC",
                    "root",
                    "LionelMessieTeo1"
            );

            // Prepare and call the stored procedure
            String query = "{ CALL StergereClient(?) }";
            statement = connection.prepareCall(query);
            statement.setInt(1, clientId);
            statement.execute();

            JOptionPane.showMessageDialog(null, "Clientul a fost șters cu succes!");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Eroare la ștergerea clientului!", "Eroare", JOptionPane.ERROR_MESSAGE);
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
    private void afiseazaStergereUnitate() {
        // Create the frame
        JFrame frameStergereUnitate = new JFrame("Interfață Stergere Unitate");
        frameStergereUnitate.setSize(500, 500);
        frameStergereUnitate.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameStergereUnitate.setLayout(null); // Absolute layout for layered pane

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
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 10)); // 3 rows for buttons
        buttonPanel.setOpaque(false); // Transparent background
        buttonPanel.setBounds(45, 100, 400, 200); // Position of the button grid

        // Style for buttons
        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        Color buttonBackground = new Color(116, 0, 0); // Light blue
        Color buttonForeground = Color.WHITE;

        // Create labels and text fields
        JLabel unitateIdLabel = new JLabel("Tastați ID-ul unității:");
        JTextField unitateIdField = new JTextField();
        unitateIdLabel.setFont(buttonFont);
        unitateIdField.setFont(buttonFont);

        // Create the delete button
        JButton deleteButton = new JButton("Șterge Unitate");
        deleteButton.setFont(buttonFont);
        deleteButton.setBackground(buttonBackground);
        deleteButton.setForeground(buttonForeground);
        deleteButton.setFocusPainted(false);
        deleteButton.setBorderPainted(false);
        deleteButton.setOpaque(true);

        // Add action listener to the delete button
        deleteButton.addActionListener(e -> {
            // Get unitate ID from the text field
            String unitateIdText = unitateIdField.getText();
            if (!unitateIdText.isEmpty()) {
                try {
                    int unitateId = Integer.parseInt(unitateIdText);
                    // Call the stored procedure to delete the unitate
                    stergereUnitate(unitateId);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frameStergereUnitate, "ID-ul unității trebuie să fie un număr valid!", "Eroare", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frameStergereUnitate, "Vă rugăm să introduceți un ID de unitate!", "Eroare", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Add components to the panel
        buttonPanel.add(unitateIdLabel);
        buttonPanel.add(unitateIdField);
        buttonPanel.add(deleteButton);

        // Add the button panel to the layered pane (higher layer)
        layeredPane.add(buttonPanel, Integer.valueOf(1));

        // Add the layered pane to the frame
        frameStergereUnitate.add(layeredPane);

        // Finalize the frame
        frameStergereUnitate.setLocationRelativeTo(null); // Center on screen
        frameStergereUnitate.setVisible(true);
    }
    private void stergereUnitate(int unitateId) {
        Connection connection = null;
        CallableStatement statement = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/proiect2?useSSL=false&serverTimezone=UTC",
                    "root",
                    "LionelMessieTeo1"
            );

            // Prepare and call the stored procedure
            String query = "{ CALL StergereUnitate(?) }";
            statement = connection.prepareCall(query);
            statement.setInt(1, unitateId);
            statement.execute();

            JOptionPane.showMessageDialog(null, "Unitatea a fost ștersă cu succes!");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Eroare la ștergerea unității!", "Eroare", JOptionPane.ERROR_MESSAGE);
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
    private void afiseazaStergereAngajat() {
        // Create the frame
        JFrame frameStergereAngajat = new JFrame("Interfață Stergere Angajat");
        frameStergereAngajat.setSize(500, 500);
        frameStergereAngajat.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameStergereAngajat.setLayout(null); // Absolute layout for layered pane

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
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 10)); // 3 rows for buttons
        buttonPanel.setOpaque(false); // Transparent background
        buttonPanel.setBounds(45, 100, 400, 200); // Position of the button grid

        // Style for buttons
        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        Color buttonBackground = new Color(116, 0, 0); // Light blue
        Color buttonForeground = Color.WHITE;

        // Create labels and text fields
        JLabel angajatIdLabel = new JLabel("Tastați ID-ul Angajatului:");
        JTextField angajatIdField = new JTextField();
        angajatIdLabel.setFont(buttonFont);
        angajatIdField.setFont(buttonFont);

        // Create the delete button
        JButton deleteButton = new JButton("Șterge Angajat");
        deleteButton.setFont(buttonFont);
        deleteButton.setBackground(buttonBackground);
        deleteButton.setForeground(buttonForeground);
        deleteButton.setFocusPainted(false);
        deleteButton.setBorderPainted(false);
        deleteButton.setOpaque(true);

        // Add action listener to the delete button
        deleteButton.addActionListener(e -> {
            // Get angajat ID from the text field
            String angajatIdText = angajatIdField.getText();
            if (!angajatIdText.isEmpty()) {
                try {
                    int angajatId = Integer.parseInt(angajatIdText);
                    // Call the stored procedure to delete the angajat and the associated utilizator
                    stergereAngajat(angajatId);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frameStergereAngajat, "ID-ul angajatului trebuie să fie un număr valid!", "Eroare", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frameStergereAngajat, "Vă rugăm să introduceți un ID de angajat!", "Eroare", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Add components to the panel
        buttonPanel.add(angajatIdLabel);
        buttonPanel.add(angajatIdField);
        buttonPanel.add(deleteButton);

        // Add the button panel to the layered pane (higher layer)
        layeredPane.add(buttonPanel, Integer.valueOf(1));

        // Add the layered pane to the frame
        frameStergereAngajat.add(layeredPane);

        // Finalize the frame
        frameStergereAngajat.setLocationRelativeTo(null); // Center on screen
        frameStergereAngajat.setVisible(true);
    }
    private void stergereAngajat(int angajatId) {
        Connection connection = null;
        CallableStatement statement = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/proiect2?useSSL=false&serverTimezone=UTC",
                    "root",
                    "LionelMessieTeo1"
            );

            // Prepare and call the stored procedure
            String query = "{ CALL StergereAngajat(?) }";
            statement = connection.prepareCall(query);
            statement.setInt(1, angajatId);
            statement.execute();

            JOptionPane.showMessageDialog(null, "Angajatul și utilizatorul asociat au fost șterși cu succes!");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Eroare la ștergerea angajatului și utilizatorului!", "Eroare", JOptionPane.ERROR_MESSAGE);
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
    private void afiseazaStergereUtilizator() {
        // Create the frame
        JFrame frameStergereUtilizator = new JFrame("Interfață Stergere Utilizator");
        frameStergereUtilizator.setSize(500, 500);
        frameStergereUtilizator.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameStergereUtilizator.setLayout(null); // Absolute layout for layered pane
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
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 10)); // 3 rows for buttons
        buttonPanel.setOpaque(false); // Transparent background
        buttonPanel.setBounds(45, 100, 400, 200); // Position of the button grid

        // Style for buttons
        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        Color buttonBackground = new Color(116, 0, 0); // Light blue
        Color buttonForeground = Color.WHITE;

        // Create labels and text fields
        JLabel utilizatorIdLabel = new JLabel("Tastați ID-ul utilizatorului:");
        JTextField utilizatorIdField = new JTextField();
        utilizatorIdLabel.setFont(buttonFont);
        utilizatorIdField.setFont(buttonFont);

        // Create the delete button
        JButton deleteButton = new JButton("Șterge Utilizator");
        deleteButton.setFont(buttonFont);
        deleteButton.setBackground(buttonBackground);
        deleteButton.setForeground(buttonForeground);
        deleteButton.setOpaque(true);

        // Add action listener to the delete button
        deleteButton.addActionListener(e -> {
            // Get utilizator ID from the text field
            String utilizatorIdText = utilizatorIdField.getText();
            if (!utilizatorIdText.isEmpty()) {
                try {
                    int utilizatorId = Integer.parseInt(utilizatorIdText);
                    // Call the stored procedure to delete the utilizator
                    stergereUtilizator(utilizatorId);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frameStergereUtilizator, "ID-ul utilizatorului trebuie să fie un număr valid!", "Eroare", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frameStergereUtilizator, "Vă rugăm să introduceți un ID de utilizator!", "Eroare", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Add components to the panel
        buttonPanel.add(utilizatorIdLabel);
        buttonPanel.add(utilizatorIdField);
        buttonPanel.add(deleteButton);

        // Add the button panel to the layered pane (higher layer)
        layeredPane.add(buttonPanel, Integer.valueOf(1));

        // Add the layered pane to the frame
        frameStergereUtilizator.add(layeredPane);

        // Finalize the frame
        frameStergereUtilizator.setLocationRelativeTo(null); // Center on screen
        frameStergereUtilizator.setVisible(true);
    }
    private void stergereUtilizator(int utilizatorId) {
        Connection connection = null;
        CallableStatement statement = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/proiect2?useSSL=false&serverTimezone=UTC",
                    "root",
                    "LionelMessieTeo1"
            );

            // Prepare and call the stored procedure
            String query = "{ CALL StergereUtilizator(?) }";
            statement = connection.prepareCall(query);
            statement.setInt(1, utilizatorId);
            statement.execute();

            JOptionPane.showMessageDialog(null, "Utilizatorul a fost șters cu succes!");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Eroare la ștergerea utilizatorului!", "Eroare", JOptionPane.ERROR_MESSAGE);
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
    private void afiseazaAdaugareUnitate() {
        // Create the frame
        JFrame frameAdaugareUnitate = new JFrame("Adaugare Unitate");
        frameAdaugareUnitate.setSize(500, 500);
        frameAdaugareUnitate.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameAdaugareUnitate.setLayout(null);

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
        formPanel.setLayout(new GridLayout(7, 2, 10, 10)); // Grid layout for fields and labels
        formPanel.setOpaque(false);
        formPanel.setBounds(50, 50, 400, 400);

        // Create form fields
        String[] labels = {"Nume", "Adresa", "Echipament", "Program", "Venituri", "Cheltuieli"};
        JTextField[] textFields = new JTextField[labels.length];

        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i]);
            label.setForeground(Color.WHITE);
            formPanel.add(label);

            textFields[i] = new JTextField();
            formPanel.add(textFields[i]);
        }

        // Add buttons
        JButton addButton = new JButton("Adaugă Unitate");
        JButton cancelButton = new JButton("Anulează");

        formPanel.add(addButton);
        formPanel.add(cancelButton);

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
            String nume = textFields[0].getText();
            String adresa = textFields[1].getText();
            String echipament = textFields[2].getText();
            String program = textFields[3].getText();
            String venituri = textFields[4].getText();
            String cheltuieli = textFields[5].getText();

            // Call the method to insert the data into the database
            adaugareUnitate(nume, adresa, echipament, program);
        });

        cancelButton.addActionListener(e -> frameAdaugareUnitate.dispose());

        // Add form to layered pane
        layeredPane.add(formPanel, Integer.valueOf(1));

        // Add layered pane to frame
        frameAdaugareUnitate.add(layeredPane);

        // Finalize frame
        frameAdaugareUnitate.setLocationRelativeTo(null);
        frameAdaugareUnitate.setVisible(true);
    }
    private void adaugareUnitate(String nume, String adresa, String echipament, String program) {
        Connection connection = null;
        CallableStatement statement = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/proiect2?useSSL=false&serverTimezone=UTC",
                    "root",
                    "LionelMessieTeo1"
            );

            // Prepare and call the stored procedure
            String query = "{ CALL AdaugareUnitate(?, ?, ?, ?) }";
            statement = connection.prepareCall(query);

            // Set parameters
            statement.setString(1, nume);
            statement.setString(2, adresa);
            statement.setString(3, echipament);
            statement.setString(4, program);

            statement.execute();
            JOptionPane.showMessageDialog(null, "Unitatea a fost adăugată cu succes!");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Eroare la adăugarea unității!", "Eroare", JOptionPane.ERROR_MESSAGE);
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
    private void afiseazaAdaugareAngajat() {
        // Create the frame
        JFrame frameAdaugareAngajat = new JFrame("Adăugare Angajat");
        frameAdaugareAngajat.setSize(500, 500);
        frameAdaugareAngajat.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameAdaugareAngajat.setLayout(null);

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
        formPanel.setLayout(new GridLayout(4, 2, 10, 10)); // Grid layout for fields and labels
        formPanel.setOpaque(false);
        formPanel.setBounds(50, 50, 400, 200);

        // Create form fields
        String[] labels = {"Salariu", "Nr. Ore", "Utilizator ID"};
        JTextField[] textFields = new JTextField[labels.length];

        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i]);
            label.setForeground(Color.WHITE);
            formPanel.add(label);

            textFields[i] = new JTextField();
            formPanel.add(textFields[i]);
        }

        // Add buttons
        JButton addButton = new JButton("Adaugă Angajat");
        JButton cancelButton = new JButton("Anulează");

        formPanel.add(addButton);
        formPanel.add(cancelButton);

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
            String salariu = textFields[0].getText();
            String nrOre = textFields[1].getText();
            String utilizatorId = textFields[2].getText();

            adaugareAngajat(salariu, nrOre, utilizatorId);
        });

        cancelButton.addActionListener(e -> frameAdaugareAngajat.dispose());

        // Add form to layered pane
        layeredPane.add(formPanel, Integer.valueOf(1));

        // Add layered pane to frame
        frameAdaugareAngajat.add(layeredPane);

        // Finalize frame
        frameAdaugareAngajat.setLocationRelativeTo(null);
        frameAdaugareAngajat.setVisible(true);
    }
    private void adaugareAngajat(String salariu, String nrOre, String utilizatorId) {
        Connection connection = null;
        CallableStatement statement = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/proiect2?useSSL=false&serverTimezone=UTC",
                    "root",
                    "LionelMessieTeo1"
            );

            // Prepare and call the stored procedure
            String query = "{ CALL AdaugareAngajat(?, ?, ?) }";
            statement = connection.prepareCall(query);

            // Set parameters
            statement.setInt(1, Integer.parseInt(salariu));  // Salariu
            statement.setInt(2, Integer.parseInt(nrOre));    // Nr. Ore
            statement.setInt(3, Integer.parseInt(utilizatorId));  // Utilizator ID

            statement.execute();
            JOptionPane.showMessageDialog(null, "Angajatul a fost adăugat cu succes!");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Eroare la adăugarea angajatului!", "Eroare", JOptionPane.ERROR_MESSAGE);
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
    private void afiseazaAdaugareUtilizator() {
        // Create the frame
        JFrame frameAdaugare = new JFrame("Adaugare Utilizator");
        frameAdaugare.setSize(500, 500);
        frameAdaugare.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameAdaugare.setLayout(null);

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
        formPanel.setLayout(new GridLayout(12, 2, 10, 10)); // Grid layout for fields and labels
        formPanel.setOpaque(false);
        formPanel.setBounds(50, 50, 400, 400);

        // Create form fields
        String[] labels = {"CNP", "Parola", "Functie", "Nume", "Prenume", "Adresa", "Nr. Tel", "Email", "IBAN", "Nr. Contact", "Data Angajarii"};
        JTextField[] textFields = new JTextField[labels.length];

        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i]);
            label.setForeground(Color.WHITE);
            formPanel.add(label);

            textFields[i] = new JTextField();
            formPanel.add(textFields[i]);
        }

        // Add buttons
        JButton addButton = new JButton("Adaugă Utilizator");
        JButton cancelButton = new JButton("Anulează");

        formPanel.add(addButton);
        formPanel.add(cancelButton);

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
            String cnp = textFields[0].getText();
            String parola = textFields[1].getText();
            String functie = textFields[2].getText();
            String nume = textFields[3].getText();
            String prenume = textFields[4].getText();
            String adresa = textFields[5].getText();
            String nrTel = textFields[6].getText();
            String email = textFields[7].getText();
            String iban = textFields[8].getText();
            String nrContact = textFields[9].getText();
            String dataAngajarii = textFields[10].getText();

            adaugareUtilizator(cnp, parola, functie, nume, prenume, adresa, nrTel, email, iban, nrContact, dataAngajarii);
        });

        cancelButton.addActionListener(e -> frameAdaugare.dispose());

        // Add form to layered pane
        layeredPane.add(formPanel, Integer.valueOf(1));

        // Add layered pane to frame
        frameAdaugare.add(layeredPane);

        // Finalize frame
        frameAdaugare.setLocationRelativeTo(null);
        frameAdaugare.setVisible(true);
    }
    private void adaugareUtilizator(String cnp, String parola, String functie, String nume, String prenume, String adresa, String nrTel, String email, String iban, String nrContact, String dataAngajarii) {
        Connection connection = null;
        CallableStatement statement = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/proiect2?useSSL=false&serverTimezone=UTC",
                    "root",
                    "LionelMessieTeo1"
            );

            // Prepare and call the stored procedure
            String query = "{ CALL AdaugareUtilizator(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }";
            statement = connection.prepareCall(query);

            // Set parameters
            statement.setString(1, cnp);
            statement.setString(2, parola);
            statement.setString(3, functie);
            statement.setString(4, nume);
            statement.setString(5, prenume);
            statement.setString(6, adresa);
            statement.setString(7, nrTel);
            statement.setString(8, email);
            statement.setString(9, iban);
            statement.setString(10, nrContact);
            statement.setString(11, dataAngajarii);

            statement.execute();
            JOptionPane.showMessageDialog(null, "Utilizatorul a fost adăugat cu succes!");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Eroare la adăugarea utilizatorului!", "Eroare", JOptionPane.ERROR_MESSAGE);
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
    private void afiseazaInterfataUnitati() {
        // Crearea ferestrei principale
        JFrame framePacienti = new JFrame("Unitati");
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
             CallableStatement stmt = conn.prepareCall("{CALL AfisareUnitati()}")) {
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
    private void afiseazaInterfataAngajati() {
        // Crearea ferestrei principale
        JFrame framePacienti = new JFrame("Angajati");
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
             CallableStatement stmt = conn.prepareCall("{CALL AfisareAngajati()}")) {
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
    private void afiseazaInterfataUtilizatori() {
        // Crearea ferestrei principale
        JFrame framePacienti = new JFrame("Utilizatori");
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
             CallableStatement stmt = conn.prepareCall("{CALL AfisareUtilizatori()}")) {
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
                        DefaultTableModel tableModel = new DefaultTableModel();
                        ResultSetMetaData metaData = resultSet.getMetaData();

                        int columnCount = metaData.getColumnCount();
                        for (int i = 1; i <= columnCount; i++) {
                            tableModel.addColumn(metaData.getColumnName(i));
                        }

                        while (resultSet.next()) {
                            Object[] rowData = new Object[columnCount];
                            for (int i = 1; i <= columnCount; i++) {
                                rowData[i - 1] = resultSet.getObject(i);
                            }
                            tableModel.addRow(rowData);
                        }

                        // Creare JTable cu modelul de date
                        JTable table = new JTable(tableModel);
                        table.setFont(new Font("Arial", Font.PLAIN, 14)); // Setăm fontul pentru text
                        table.setRowHeight(30); // Setăm înălțimea rândurilor pentru a fi mai clare
                        table.setGridColor(Color.GRAY); // Setăm culoarea grilei
                        table.setSelectionBackground(new Color(0, 120, 212)); // Fundalul pentru selecția unui rând
                        table.setSelectionForeground(Color.WHITE); // Textul alb pentru rândurile selectate

                        // Scroll pane pentru tabel
                        JScrollPane scrollPane = new JScrollPane(table);
                        scrollPane.setOpaque(false);
                        scrollPane.getViewport().setOpaque(false);

                        // Creare fereastră pentru date personale
                        JFrame frameDatePersonale = new JFrame("Date Personale");
                        frameDatePersonale.setSize(540, 540);
                        frameDatePersonale.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                        // Adăugare GIF ca fundal
                        ImageIcon backgroundIcon = new ImageIcon("C:/Users/pinza/Desktop/FACULTATE/AN2/SEM1/(BD) Baze de date/proiect bd (1) - Copy/proiect bd/hmm.gif");
                        JLabel backgroundLabel = new JLabel(backgroundIcon);
                        backgroundLabel.setLayout(new BorderLayout()); // Use BorderLayout to overlay components properly

                        // Adăugare tabel peste fundal
                        backgroundLabel.add(scrollPane, BorderLayout.CENTER);
                        frameDatePersonale.setContentPane(backgroundLabel); // Setăm fundalul ca content pane

                        // Setăm locația ferestrei pe ecran și o facem vizibilă
                        frameDatePersonale.setLocationRelativeTo(null); // Centrat pe ecran
                        frameDatePersonale.setVisible(true);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

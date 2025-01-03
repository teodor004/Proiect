import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
public class InterfataLogare extends Component {
    public InterfataLogare() {
        JFrame frame = new JFrame("Autentificare");
        frame.setSize(500, 500); // Increased height to accommodate password field
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(116, 0, 0));
        frame.add(panel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Display "Bun venit!" text directly above the input fields
        JTextArea welcomeText = new JTextArea("    Introduceți datele necesare");
        welcomeText.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeText.setForeground(new Color(72, 209, 204));
        welcomeText.setBackground(new Color(116, 0, 0)); // Same background as panel
        welcomeText.setLineWrap(true);
        welcomeText.setWrapStyleWord(true);
        welcomeText.setEditable(false);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(welcomeText, gbc);

        // Label for CNP input
        JLabel cnpLabel = new JLabel("CNP:");
        cnpLabel.setFont(new Font("Arial", Font.BOLD, 14));
        cnpLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(cnpLabel, gbc);

        // CNP input field
        JTextField cnpField = new JTextField(20);
        cnpField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(cnpField, gbc);

        // Label for password input
        JLabel passwordLabel = new JLabel("Parola:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        passwordLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel.add(passwordLabel, gbc);

        // Password input field
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel.add(passwordField, gbc);

        // Login button
        JButton loginButton = new JButton("Login in");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBackground(new Color(116, 0, 0)); // Turquoise color
        loginButton.setForeground(Color.WHITE); // White text
        loginButton.setFocusPainted(false);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(loginButton, gbc);

        // Footer with copyright
        JLabel footerLabel = new JLabel("© 2024 TeoRobertPepi:3");
        footerLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        footerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(footerLabel, BorderLayout.SOUTH);

        // Action logic for button
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String cnp = cnpField.getText();
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars);

                if (validateCNP(cnp)) {
                    if (checkCNPAndPasswordInDatabase(cnp, Integer.parseInt(password))) {
                        Role[] roles = Role.values(); // Get all defined roles
                        boolean roleFound = false;

                        for (Role role : roles) {
                            if (checkRole(cnp, role)) {
                                roleFound = true;
                                switch (role) {
                                    case Medic ->{
                                        Medic medic = new Medic();
                                        medic.afiseazaInterfataMedic(cnp);
                                    }
                                    case AsistentMedical -> {
                                        Asistent asistent = new Asistent();
                                        asistent.afiseazaInterfataAsistent(cnp);
                                    }
                                    case Receptioner ->{
                                        Receptioner receptioner = new Receptioner();
                                        receptioner.afiseazaInterfataReceptioner(cnp);
                                    }
                                    case Financiar ->{
                                        Financiar interfataFinanciar = new Financiar();
                                        interfataFinanciar.afiseazaInterfataFinanciar(cnp);
                                    }
                                    case ResurseUmane ->{
                                        ResurseUmane interfataResurseUmane = new ResurseUmane();
                                        interfataResurseUmane.afiseazaInterfataResurseUmane(cnp);
                                    }
                                    case SuperAdministrator ->{
                                        SuperAdministrator superAdministrator = new SuperAdministrator();
                                        superAdministrator.afiseazaInterfataSuperAdministrator(cnp);
                                    }
                                    default -> JOptionPane.showMessageDialog(null, "CNP-ul corespunde unui utilizator necunoscut.");
                                }
                                break; // Exit the loop once a matching role is found
                            }
                        }
                        if (!roleFound) {
                            JOptionPane.showMessageDialog(null, "CNP-ul corespunde unui utilizator, dar nu este asociat cu un rol valid.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "CNP-ul sau parola este incorectă. Autentificare eșuată!");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "CNP invalid. Autentificare eșuată!");
                }
            }
        });


        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    private static boolean validateCNP(String cnp) {
        return cnp.length() == 13;
    }
    public enum Role {
        Medic("IsMedic"),
        AsistentMedical("IsAsistentMedical"),
        Receptioner("IsReceptioner"),
        Financiar("IsFinanciar"),
        SuperAdministrator("IsSuperAdministrator"),
        ResurseUmane("IsResurseUmane");

        private final String procedureName;

        Role(String procedureName) {
            this.procedureName = procedureName;
        }

        public String getProcedureName() {
            return procedureName;
        }
    }
    public boolean checkRole(String cnp, Role role) {
        boolean result = false;
        Connection connection = null;
        CallableStatement statement = null;

        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/proiect2?useSSL=false&serverTimezone=UTC",
                    "root",
                    "LionelMessieTeo1"
            );

            // Use the procedure name from the Role enum
            String query = "{ ? = CALL " + role.getProcedureName() + "(?) }";
            System.out.println("Executing query: " + query); // For debugging
            statement = connection.prepareCall(query);
            statement.registerOutParameter(1, Types.BOOLEAN);
            statement.setString(2, cnp);
            statement.execute();

            result = statement.getBoolean(1);
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            // Ensure resources are closed properly
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return result;
    }
    private static boolean checkCNPAndPasswordInDatabase(String cnp, int password) {
        boolean exists = false;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish database connection
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/proiect2?useSSL=false&serverTimezone=UTC",
                    "root",
                    "LionelMessieTeo1"
            );

            // SQL query to check for a record with the given CNP and password
            String query = "SELECT COUNT(*) FROM utilizator WHERE CNP = ? AND parola = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, cnp);
            statement.setInt(2, password);

            // Execute query
            resultSet = statement.executeQuery();

            // Check if a matching record exists
            if (resultSet.next()) {
                exists = resultSet.getInt(1) > 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            // Close resources
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
        return exists;
    }
}


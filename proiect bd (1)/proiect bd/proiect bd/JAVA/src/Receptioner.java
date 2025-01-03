import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Receptioner {
    void afiseazaInterfataReceptioner(String cnp) {
        // Crearea ferestrei principale
        JFrame frameMedic = new JFrame("Interfață Receptioner");
        frameMedic.setSize(500, 500);
        frameMedic.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameMedic.setLayout(null);

        // Încărcarea unui fundal personalizat
        ImageIcon backgroundIcon = new ImageIcon("C:/Users/pinza/Desktop/FACULTATE/AN2/SEM1/(BD) Baze de date/proiect bd (1)/proiect bd/hmm.gif"); // Modifică calea
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setBounds(0, 0, 500, 500);

        // Crearea unui JLayeredPane pentru fundal și alte componente
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 500, 500);
        layeredPane.add(backgroundLabel, Integer.valueOf(0));

        // Crearea unui JPanel pentru butoane (GridLayout cu 2 butoane pe rând)
        JPanel panelMedic = new JPanel();
        panelMedic.setLayout(new GridLayout(2, 2, 10, 10)); // Grid cu 2 butoane pe fiecare rând
        panelMedic.setOpaque(false);  // Fă panoul transparent pentru a vedea fundalul
        panelMedic.setBounds(50, 100, 400, 200); // Poziționare și dimensiune

        // Crearea butoanelor
        JButton dateButton = new JButton("Vizualizare Date Personale");
        JButton inregistrareButton = new JButton("Inregistreaza un nou client");
        JButton programareButton = new JButton("Creeaza o noua programare");
        JButton bonButton = new JButton("Emite un bon fiscal");

        // Stil pentru butoane
        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        Color buttonBackground = new Color(116, 0, 0); // Albastru deschis
        Color buttonForeground = Color.WHITE; // Text alb

        // Aplicarea stilului pentru toate butoanele
        JButton[] buttons = {dateButton, inregistrareButton, programareButton, bonButton};
        for (JButton btn : buttons) {
            btn.setFont(buttonFont);
            btn.setBackground(buttonBackground);
            btn.setForeground(buttonForeground);
            btn.setFocusPainted(false); // Elimină conturul când este selectat
            btn.setBorderPainted(false); // Elimină bordura implicită
            btn.setOpaque(true); // Asigură aplicarea culorilor personalizate
            btn.setPreferredSize(new Dimension(150, 40)); // Dimensiuni mai mici pentru butoane
            panelMedic.add(btn); // Adaugă butonul la panou
        }

        // Listener pentru butonul "Inregistrare Client"
        inregistrareButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                afiseazaInterfataInregistrareClient();
            }
        });

        // Listener pentru butonul "Emitere Bon Fiscal"
        bonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                afiseazaInterfataEmitereBonFiscal();
            }
        });

        // Listener pentru butonul "Vizualizare Date Personale"
        dateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                afiseazaDatePersonale(cnp);
            }
        });

        // Listener pentru butonul "Creeaza o noua programare"
        programareButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                afiseazaInterfataProgramare();
            }
        });

        // Adăugarea JPanel-ului cu butoane la JLayeredPane
        layeredPane.add(panelMedic, Integer.valueOf(1));

        // Crearea butonului de logout
        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(buttonFont);
        logoutButton.setBackground(new Color(116, 0, 0)); // Roșu pentru logout
        logoutButton.setForeground(Color.WHITE); // Text alb
        logoutButton.setFocusPainted(false);
        logoutButton.setBorderPainted(false);
        logoutButton.setOpaque(true);
        logoutButton.setPreferredSize(new Dimension(150, 40)); // Dimensiune buton logout
        logoutButton.setBounds(175, 400, 150, 40); // Poziționare la mijloc, jos
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameMedic.dispose(); // Închide fereastra atunci când se apasă logout
            }
        });

        // Adăugarea butonului de logout la JLayeredPane
        layeredPane.add(logoutButton, Integer.valueOf(2));

        // Adăugarea JLayeredPane la fereastra principală
        frameMedic.add(layeredPane);

        // Setarea fundalului general și a locației ferestrei
        frameMedic.getContentPane().setBackground(new Color(116, 0, 0)); // Fundal general
        frameMedic.setLocationRelativeTo(null); // Centrează fereastra pe ecran
        frameMedic.setVisible(true);
    }
    private void afiseazaInterfataProgramare() {
        // Setare JFrame pentru fereastra principală
        JFrame frameAdaugaServicii = new JFrame("Adăugare Programare");
        frameAdaugaServicii.setSize(500, 500);
        frameAdaugaServicii.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameAdaugaServicii.setLayout(new BorderLayout());

        // Creare JPanel pentru fundalul cu imagine
        JPanel panelAdaugaServicii = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    // Încarcă imaginea GIF
                    ImageIcon backgroundIcon = new ImageIcon("C:/Users/pinza/Desktop/FACULTATE/AN2/SEM1/(BD) Baze de date/proiect bd (1) - Copy/proiect bd/hmm.gif"); // Asigură-te că ai setat corect calea
                    Image backgroundImage = backgroundIcon.getImage();
                    g.drawImage(backgroundImage, 0, 0, this); // Desenează imaginea
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        // Adăugare layout pentru panel
        panelAdaugaServicii.setLayout(new GridLayout(6, 2, 10, 10)); // Layout cu spațiu între elemente
        panelAdaugaServicii.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Margină pentru a evita contactul direct cu borderul ferestrei
        frameAdaugaServicii.add(panelAdaugaServicii, BorderLayout.CENTER);

        // Creare câmpuri de input
        JTextField dataField = new JTextField();
        JTextField numeField = new JTextField();
        JTextField prenumeField = new JTextField();
        JTextField serviciuIdField = new JTextField();
        JTextField durataField = new JTextField();

        // Creare etichete și adăugare câmpuri
        JLabel dataLabel = new JLabel("Data:");
        dataLabel.setForeground(Color.WHITE);
        panelAdaugaServicii.add(dataLabel);
        panelAdaugaServicii.add(dataField);

        JLabel numeLabel = new JLabel("Nume:");
        numeLabel.setForeground(Color.WHITE);
        panelAdaugaServicii.add(numeLabel);
        panelAdaugaServicii.add(numeField);

        JLabel prenumeLabel = new JLabel("Prenume:");
        prenumeLabel.setForeground(Color.WHITE);
        panelAdaugaServicii.add(prenumeLabel);
        panelAdaugaServicii.add(prenumeField);

        JLabel serviciuIdLabel = new JLabel("Serviciu ID:");
        serviciuIdLabel.setForeground(Color.WHITE);
        panelAdaugaServicii.add(serviciuIdLabel);
        panelAdaugaServicii.add(serviciuIdField);

        JLabel durataLabel = new JLabel("Durata:");
        durataLabel.setForeground(Color.WHITE);
        panelAdaugaServicii.add(durataLabel);
        panelAdaugaServicii.add(durataField);

        // Creare buton pentru a adăuga programarea
        JButton adaugaButton = new JButton("Adaugă Programare");
        adaugaButton.setPreferredSize(new Dimension(150, 30)); // Setează dimensiunile butonului
        adaugaButton.setFont(new Font("Arial", Font.PLAIN, 12)); // Setează fontul butonului pentru a-l face mai mic
        panelAdaugaServicii.add(adaugaButton);

        // Adăugare acțiune pentru buton
        adaugaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Apelare funcție pentru inserarea programării
                inserareProgramare(dataField.getText(), numeField.getText(), prenumeField.getText(),
                        Integer.parseInt(serviciuIdField.getText()), Integer.parseInt(durataField.getText()));

                // Închidere fereastră
                frameAdaugaServicii.dispose();
            }
        });

        // Setare locație și vizibilitate
        frameAdaugaServicii.setLocationRelativeTo(null); // Centrează fereastra
        frameAdaugaServicii.setVisible(true);
    }
    private void inserareProgramare(String data, String nume, String prenume, int serviciuId, int durata) {
        Connection connection = null;
        CallableStatement statement = null;

        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/proiect2?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC",
                    "root",
                    "LionelMessieTeo1"
            );
            String query = "{CALL InserareProgramare(?, ?, ?, ?, ?)}";
            statement = connection.prepareCall(query);
            statement.setString(1, data);  // data
            statement.setString(2, nume);  // nume
            statement.setString(3, prenume); // prenume
            statement.setInt(4, serviciuId);  // serviciu_id
            statement.setInt(5, durata);     // durata
            statement.execute();
        } catch (SQLException ex) {
            // If an exception occurs, print the error message and show it as a popup
            System.err.println("Error occurred during procedure execution: " + ex.getMessage());

            // If it's a custom error raised from the SIGNAL in the stored procedure, show it as a popup
            if (ex.getSQLState().equals("45000")) {
                String errorMessage = "Custom error: " + ex.getMessage();
                System.err.println(errorMessage); // Print to console
                JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);  // Show popup
            } else {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);  // Show general error message
            }
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
    private void afiseazaInterfataEmitereBonFiscal() {
        JFrame frameBonFiscal = new JFrame("Emitere Bon Fiscal");
        frameBonFiscal.setSize(500, 500);
        frameBonFiscal.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameBonFiscal.setLayout(null); // Folosim layout null pentru un design personalizat

        // Încărcarea unui fundal personalizat
        ImageIcon backgroundIcon = new ImageIcon("C:/Users/pinza/Desktop/FACULTATE/AN2/SEM1/(BD) Baze de date/proiect bd (1) - Copy/proiect bd/hmm.gif"); // Modifică calea
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setBounds(0, 0, 500, 500);

        // Crearea unui JLayeredPane pentru fundal și alte componente
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 500, 500);
        layeredPane.add(backgroundLabel, Integer.valueOf(0));

        // Crearea unui JPanel pentru câmpurile de input
        JPanel panelBonFiscal = new JPanel();
        panelBonFiscal.setLayout(new GridLayout(4, 2, 10, 10)); // Grid pentru câmpurile de input
        panelBonFiscal.setOpaque(false); // Fă panelul transparent pentru a vedea fundalul
        panelBonFiscal.setBounds(50, 150, 400, 200); // Poziționare și dimensiune

        // Câmpuri de text
        JTextField valoareField = new JTextField();
        JTextField dataField = new JTextField();
        JTextField numarConsultatieField = new JTextField();

        // Adăugarea etichetelor și câmpurilor de input
        JLabel valoareLabel = new JLabel("Valoare:");
        valoareLabel.setForeground(Color.WHITE); // Setează culoarea textului la alb
        panelBonFiscal.add(valoareLabel);
        panelBonFiscal.add(valoareField);

        JLabel dataLabel = new JLabel("Data:");
        dataLabel.setForeground(Color.WHITE); // Setează culoarea textului la alb
        panelBonFiscal.add(dataLabel);
        panelBonFiscal.add(dataField);

        JLabel numarConsultatieLabel = new JLabel("Id Consultatie:");
        numarConsultatieLabel.setForeground(Color.WHITE); // Setează culoarea textului la alb
        panelBonFiscal.add(numarConsultatieLabel);
        panelBonFiscal.add(numarConsultatieField);

        // Crearea butonului de realizare
        JButton realizeazaButton = new JButton("Realizeaza");
        panelBonFiscal.add(realizeazaButton);

        // Aplicarea stilului pentru câmpurile de text și buton
        Font inputFont = new Font("Arial", Font.PLAIN, 14);
        Color inputBackground = new Color(240, 240, 240); // Fundal deschis pentru câmpurile de input
        Color buttonBackground = new Color(116, 0, 0); // Fundal pentru buton
        Color buttonForeground = Color.WHITE; // Text alb pe buton

        // Setarea stilului pentru câmpurile de text
        JTextField[] textFields = {valoareField, dataField, numarConsultatieField};
        for (JTextField tf : textFields) {
            tf.setFont(inputFont);
            tf.setBackground(inputBackground);
            tf.setPreferredSize(new Dimension(150, 25)); // Dimensiuni mai mici pentru câmpuri
        }

        // Setarea stilului pentru buton
        realizeazaButton.setFont(new Font("Arial", Font.BOLD, 14));
        realizeazaButton.setBackground(buttonBackground);
        realizeazaButton.setForeground(buttonForeground);
        realizeazaButton.setFocusPainted(false);
        realizeazaButton.setBorderPainted(false);
        realizeazaButton.setOpaque(true);
        realizeazaButton.setPreferredSize(new Dimension(150, 30)); // Dimensiuni buton

        // Acțiunea butonului
        realizeazaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    double valoare = Double.parseDouble(valoareField.getText().trim());
                    String data = dataField.getText().trim();
                    int numarConsultatie = Integer.parseInt(numarConsultatieField.getText().trim());

                    // Verificare câmpuri goale
                    if (data.isEmpty()) {
                        JOptionPane.showMessageDialog(frameBonFiscal, "Toate câmpurile trebuie completate!", "Eroare", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Apelarea funcției MySQL pentru inserarea bonului fiscal
                    inserareBonFiscal(valoare, data, numarConsultatie);

                    // Închiderea ferestrei de emitere bon fiscal
                    frameBonFiscal.dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frameBonFiscal, "Valoarea și Id Consultatie trebuie să fie numere valide!", "Eroare", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Adăugarea panelului la JLayeredPane
        layeredPane.add(panelBonFiscal, Integer.valueOf(1));

        // Adăugarea JLayeredPane la fereastra principală
        frameBonFiscal.add(layeredPane);

        // Setarea locației ferestrei și vizibilitatea
        frameBonFiscal.setLocationRelativeTo(null);
        frameBonFiscal.setVisible(true);
    }
    private void inserareBonFiscal(double valoare, String data, int numarConsultatie) {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/proiect2?useSSL=false&serverTimezone=UTC",
                "root",
                "LionelMessieTeo1"
        )) {
            String insertQuery = "CALL InserareBonFiscal(?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setDouble(1, valoare);
                preparedStatement.setString(2, data);
                preparedStatement.setInt(3, numarConsultatie);

                // Execute the stored procedure
                preparedStatement.execute();
            }
        } catch (SQLException ex) {
            // If an exception occurs, print the error message and show it as a popup
            System.err.println("Error occurred during procedure execution: " + ex.getMessage());

            // If it's a custom error raised from the SIGNAL in the stored procedure, show it as a popup
            if (ex.getSQLState().equals("45000")) {
                String errorMessage = "Custom error: " + ex.getMessage();
                System.err.println(errorMessage); // Print to console
                JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);  // Show popup
            } else {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);  // Show general error message
            }
        }
    }
    private static void afiseazaInterfataInregistrareClient() {
        JFrame frameInregistrare = new JFrame("Inregistrare Client");
        frameInregistrare.setSize(500, 500);
        frameInregistrare.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameInregistrare.setLayout(null); // Folosim layout null pentru a controla mai precis poziționarea

        // Încărcarea unui fundal personalizat
        ImageIcon backgroundIcon = new ImageIcon("C:/Users/pinza/Desktop/FACULTATE/AN2/SEM1/(BD) Baze de date/proiect bd (1) - Copy/proiect bd/hmm.gif"); // Modifică calea
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setBounds(0, 0, 500, 500);

        // Crearea unui JLayeredPane pentru fundal și alte componente
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 500, 500);
        layeredPane.add(backgroundLabel, Integer.valueOf(0));

        // Crearea unui JPanel pentru câmpurile de input
        JPanel panelInregistrare = new JPanel();
        panelInregistrare.setLayout(new GridLayout(6, 2, 10, 10)); // Grid pentru câmpurile de input
        panelInregistrare.setOpaque(false); // Fă panelul transparent pentru a vedea fundalul
        panelInregistrare.setBounds(50, 100, 400, 300); // Poziționare și dimensiune

        // Câmpuri de text
        JTextField numeField = new JTextField();
        JTextField prenumeField = new JTextField();
        JTextField cnpField = new JTextField();
        JTextField varstaField = new JTextField();
        JTextField telefonField = new JTextField();
        // Adăugarea etichetelor și câmpurilor de input
        JLabel numeLabel = new JLabel("Nume:");
        numeLabel.setForeground(Color.WHITE);  // Setează culoarea textului la alb
        panelInregistrare.add(numeLabel);
        panelInregistrare.add(numeField);

        JLabel prenumeLabel = new JLabel("Prenume:");
        prenumeLabel.setForeground(Color.WHITE);  // Setează culoarea textului la alb
        panelInregistrare.add(prenumeLabel);
        panelInregistrare.add(prenumeField);

        JLabel cnpLabel = new JLabel("CNP:");
        cnpLabel.setForeground(Color.WHITE);  // Setează culoarea textului la alb
        panelInregistrare.add(cnpLabel);
        panelInregistrare.add(cnpField);

        JLabel varstaLabel = new JLabel("Varsta:");
        varstaLabel.setForeground(Color.WHITE);  // Setează culoarea textului la alb
        panelInregistrare.add(varstaLabel);
        panelInregistrare.add(varstaField);

        JLabel telefonLabel = new JLabel("Numar de Telefon:");
        telefonLabel.setForeground(Color.WHITE);  // Setează culoarea textului la alb
        panelInregistrare.add(telefonLabel);
        panelInregistrare.add(telefonField);


        // Crearea butonului de înregistrare
        JButton inregistreazaButton = new JButton("Inregistreaza");
        panelInregistrare.add(inregistreazaButton);

        // Aplicarea stilului pentru câmpurile de text și buton
        Font inputFont = new Font("Arial", Font.PLAIN, 14);
        Color inputBackground = new Color(240, 240, 240); // Fundal deschis pentru câmpurile de input
        Color buttonBackground = new Color(116, 0, 0); // Fundal pentru buton
        Color buttonForeground = Color.WHITE; // Text alb pe buton

        // Setarea stilului pentru câmpurile de text
        JTextField[] textFields = {numeField, prenumeField, cnpField, varstaField, telefonField};
        for (JTextField tf : textFields) {
            tf.setFont(inputFont);
            tf.setBackground(inputBackground);
            tf.setPreferredSize(new Dimension(150, 25)); // Dimensiuni mai mici pentru câmpuri
        }

        // Setarea stilului pentru buton
        inregistreazaButton.setFont(new Font("Arial", Font.BOLD, 14));
        inregistreazaButton.setBackground(buttonBackground);
        inregistreazaButton.setForeground(buttonForeground);
        inregistreazaButton.setFocusPainted(false);
        inregistreazaButton.setBorderPainted(false);
        inregistreazaButton.setOpaque(true);
        inregistreazaButton.setPreferredSize(new Dimension(150, 30)); // Dimensiuni buton

        // Validare input și acțiunea butonului
        inregistreazaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nume = numeField.getText().trim();
                String prenume = prenumeField.getText().trim();
                String cnp = cnpField.getText().trim();
                String varstaText = varstaField.getText().trim();
                String telefon = telefonField.getText().trim();

                // Verificare câmpuri goale
                if (nume.isEmpty() || prenume.isEmpty() || cnp.isEmpty() || varstaText.isEmpty() || telefon.isEmpty()) {
                    JOptionPane.showMessageDialog(frameInregistrare, "Toate câmpurile trebuie completate!", "Eroare", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    int varsta = Integer.parseInt(varstaText);
                    if (varsta <= 0) {
                        JOptionPane.showMessageDialog(frameInregistrare, "Vârsta trebuie să fie un număr pozitiv!", "Eroare", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Verificare CNP (dacă este valid)
                    if (!cnp.matches("\\d{13}")) {
                        JOptionPane.showMessageDialog(frameInregistrare, "CNP-ul trebuie să aibă exact 13 caractere!", "Eroare", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Apelarea funcției MySQL pentru inserarea clientului
                    inserareClient(nume, prenume, cnp, varsta, telefon);

                    // Închiderea ferestrei de înregistrare
                    frameInregistrare.dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frameInregistrare, "Vârsta trebuie să fie un număr valid!", "Eroare", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Adăugarea panelului la JLayeredPane
        layeredPane.add(panelInregistrare, Integer.valueOf(1));

        // Adăugarea JLayeredPane la fereastra principală
        frameInregistrare.add(layeredPane);

        // Setarea locației ferestrei și vizibilitatea
        frameInregistrare.setLocationRelativeTo(null);
        frameInregistrare.setVisible(true);
    }
    private static void inserareClient(String nume, String prenume, String cnp, int varsta, String telefon) {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/proiect2?useSSL=false&serverTimezone=UTC",
                "root",
                "LionelMessieTeo1"
        )
        ) {
            String insertQuery = "CALL InserareClient(?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, nume);
                preparedStatement.setString(2, prenume);
                preparedStatement.setString(3, cnp);
                preparedStatement.setInt(4, varsta);
                preparedStatement.setString(5, telefon);

                preparedStatement.execute();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
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

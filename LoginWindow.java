package meniprojet;
import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class LoginWindow extends Frame {

    private TextField usernameField;
    private TextField passwordField;
    private DatabaseConnector databaseConnector;
    private EmployeeDisplay employeeDisplay;
    private Frame editFrame; 

    public LoginWindow() {
        setTitle("Login ");
        setSize(300, 150);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setResizable(false);

        Panel panel = new Panel();
        panel.setLayout(new GridLayout(3, 2));

        Label usernameLabel = new Label("Username:");
        usernameField = new TextField();
        Label passwordLabel = new Label("Password:");
        passwordField = new TextField();
        passwordField.setEchoChar('*');
        Button loginButton = new Button("Valider");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = passwordField.getText();

                if (username.equals("hajar") && password.equals("bouchra")) {
                    showMainWindow();
                    dispose();
                } else {
                    showMessageDialog("Invalid username or password");
                }
            }
        });

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new Label()); // Empty label for spacing
        panel.add(loginButton);

        add(panel, BorderLayout.CENTER);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (databaseConnector != null) {
                    databaseConnector.closeConnection();
                }
                System.exit(0);
            }
        });

        setVisible(true);
    }

    private void showMainWindow() {
        Frame mainFrame = new Frame();
        mainFrame.setTitle("login");
        mainFrame.setSize(450, 250);
        mainFrame.setLayout(new GridLayout(2, 2));
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(false);
        Button addButton = new Button("Ajouter");
        Button removeButton = new Button("Supprimer");
        Button showButton = new Button("Afficher");
        Button editButton = new Button("Modifier");

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idToDelete = JOptionPane.showInputDialog("Entrez l'ID de l'employé à supprimer:");

                if (idToDelete != null && !idToDelete.isEmpty()) {
                    removeEmployee(idToDelete);
                } else {
                    showMessageDialog("Veuillez saisir un ID valide.");
                }
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddForm();
            }
        });

        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                employeeDisplay = new EmployeeDisplay(databaseConnector);
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idToEdit = JOptionPane.showInputDialog("Entrez l'ID de l'employé à modifier:");

                if (idToEdit != null && !idToEdit.isEmpty()) {
                    showEditForm(idToEdit);
                } else {
                    showMessageDialog("Veuillez saisir un ID valide.");
                }
            }
        });

        mainFrame.add(addButton);
        mainFrame.add(removeButton);
        mainFrame.add(showButton);
        mainFrame.add(editButton);

        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (databaseConnector != null) {
                    databaseConnector.closeConnection();
                }
                System.exit(0);
            }
        });

        mainFrame.setVisible(true);
    }
    private void showAddForm() {
        Frame addFrame = new Frame();
        addFrame.setTitle("Ajouter un employé");
        addFrame.setSize(600, 400);
        addFrame.setLayout(new GridBagLayout());
        addFrame.setLocationRelativeTo(null);
        addFrame.setResizable(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel cnLabel = new JLabel("CN:");
        JTextField cnField = new JTextField(20);

        JLabel nomLabel = new JLabel("Nom:");
        JTextField nomField = new JTextField(20);

        JLabel prenomLabel = new JLabel("Prénom:");
        JTextField prenomField = new JTextField(20);

        JLabel ageLabel = new JLabel("Age:");
        JTextField ageField = new JTextField(20);

        JLabel phoneNumberLabel = new JLabel("Numéro de téléphone:");
        JTextField phoneNumberField = new JTextField(20);

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(20);

        JButton validerButton = new JButton("Valider");
        JButton annulerButton = new JButton("Annuler");

        validerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cn = cnField.getText();
                String nom = nomField.getText();
                String prenom = prenomField.getText();
                int age = Integer.parseInt(ageField.getText());
                String phoneNumber = phoneNumberField.getText();
                String email = emailField.getText();

                if (databaseConnector != null) {
                    // Insérer les données dans la base de données
                    insertData(cn, nom, prenom, age, phoneNumber, email);
                }

                // Fermer la fenêtre après l'ajout
                addFrame.dispose();
            }
        });

        annulerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Fermer la fenêtre sans effectuer d'ajout
                addFrame.dispose();
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        addFrame.add(cnLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        addFrame.add(cnField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        addFrame.add(nomLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        addFrame.add(nomField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        addFrame.add(prenomLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        addFrame.add(prenomField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        addFrame.add(ageLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        addFrame.add(ageField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        addFrame.add(phoneNumberLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        addFrame.add(phoneNumberField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        addFrame.add(emailLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        addFrame.add(emailField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 6;
        addFrame.add(validerButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        addFrame.add(annulerButton, gbc);

        addFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                addFrame.dispose();
            }
        });

        addFrame.setVisible(true);
    }
    private void removeEmployee(String id) {
        try {
            String query = "DELETE FROM employee WHERE idEmp = ?";
            PreparedStatement statement = databaseConnector.getConnection().prepareStatement(query);
            statement.setString(1, id);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                showMessageDialog("Employé supprimé avec succès");
                if (employeeDisplay != null) {
                    employeeDisplay.displayEmployees(null);
                }
            } else {
                showMessageDialog("Aucun employé trouvé avec cet ID");
            }

            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    private void showEditForm(String idToEdit) {
        try {
            String query = "SELECT idEmp, Employenom, Employeprenom, age, numero, email FROM employee WHERE idEmp = ?";
            PreparedStatement statement = databaseConnector.getConnection().prepareStatement(query);
            statement.setString(1, idToEdit);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String currentCn = resultSet.getString("idEmp");
                String currentNom = resultSet.getString("Employenom");
                String currentPrenom = resultSet.getString("Employeprenom");
                int currentAge = resultSet.getInt("age");
                String currentPhoneNumber = resultSet.getString("numero");
                String currentEmail = resultSet.getString("email");

                TextField cnField = new TextField(currentCn);
                TextField nomField = new TextField(currentNom);
                TextField prenomField = new TextField(currentPrenom);
                TextField ageField = new TextField(String.valueOf(currentAge));
                TextField phoneNumberField = new TextField(currentPhoneNumber);
                TextField emailField = new TextField(currentEmail);

                Button validerButton = new Button("Valider");
                Button annulerButton = new Button("Annuler");

                validerButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String newCn = cnField.getText();
                        String newNom = nomField.getText();
                        String newPrenom = prenomField.getText();
                        int newAge = Integer.parseInt(ageField.getText());
                        String newPhoneNumber = phoneNumberField.getText();
                        String newEmail = emailField.getText();

                        updateEmployee(idToEdit, newCn, newNom, newPrenom, newAge, newPhoneNumber, newEmail);

                        if (employeeDisplay != null) {
                            employeeDisplay.displayEmployees(null);
                        }

                        // Fermer la fenêtre de modification
                        editFrame.dispose();
                    }
                });

                annulerButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Fermer la fenêtre de modification sans effectuer de modification
                        editFrame.dispose();
                    }
                });

                Panel formPanel = new Panel(new GridLayout(7, 2));
                formPanel.add(new Label("CN:"));
                formPanel.add(cnField);
                formPanel.add(new Label("Nom:"));
                formPanel.add(nomField);
                formPanel.add(new Label("Prénom:"));
                formPanel.add(prenomField);
                formPanel.add(new Label("Age:"));
                formPanel.add(ageField);
                formPanel.add(new Label("Numéro de téléphone:"));
                formPanel.add(phoneNumberField);
                formPanel.add(new Label("Email:"));
                formPanel.add(emailField);

                Panel buttonPanel = new Panel(new FlowLayout());
                buttonPanel.add(validerButton);
                buttonPanel.add(annulerButton);

                // Modifier pour créer une nouvelle fenêtre de modification au lieu de réutiliser la même
                editFrame = new Frame();
                editFrame.setTitle("Modifier Employé");
                editFrame.setSize(400, 300);
                editFrame.setLayout(new BorderLayout());
                editFrame.setLocationRelativeTo(null);
                editFrame.setResizable(false);

                editFrame.add(formPanel, BorderLayout.CENTER);
                editFrame.add(buttonPanel, BorderLayout.SOUTH);

                editFrame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        // Fermer la fenêtre de modification
                        editFrame.dispose();
                    }
                });

                // Afficher la nouvelle fenêtre de modification
                editFrame.setVisible(true);
            } else {
                showMessageDialog("Aucun employé trouvé avec cet ID");
            }

            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void updateEmployee(String id, String newCn, String newNom, String newPrenom, int newAge, String newPhoneNumber, String newEmail) {
        try {
            String query = "UPDATE employee SET idEmp = ?, Employenom = ?, Employeprenom = ?, age = ?, numero = ?, email = ? WHERE idEmp = ?";
            PreparedStatement statement = databaseConnector.getConnection().prepareStatement(query);
            statement.setString(1, newCn);
            statement.setString(2, newNom);
            statement.setString(3, newPrenom);
            statement.setInt(4, newAge);
            statement.setString(5, newPhoneNumber);
            statement.setString(6, newEmail);
            statement.setString(7, id);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                showMessageDialog("Employé mis à jour avec succès");
            } else {
                showMessageDialog("Aucun employé trouvé avec cet ID");
            }

            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void insertData(String cn, String nom, String prenom, int age, String phoneNumber, String email) {
        try {
            String query = "INSERT INTO employee (idEmp, Employenom, Employeprenom, age, numero, email) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = databaseConnector.getConnection().prepareStatement(query);
            statement.setString(1, cn);
            statement.setString(2, nom);
            statement.setString(3, prenom);
            statement.setInt(4, age);
            statement.setString(5, phoneNumber);
            statement.setString(6, email);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void showMessageDialog(String message) {
        Dialog dialog = new Dialog(this, "Message", true);
        dialog.setLayout(new BorderLayout());
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);

        Label label = new Label(message);
        Button okButton = new Button("OK");

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        dialog.add(label, BorderLayout.CENTER);
        dialog.add(okButton, BorderLayout.SOUTH);
        dialog.pack();
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        LoginWindow loginWindow = new LoginWindow();
        loginWindow.databaseConnector = new DatabaseConnector();
    }
}

package meniprojet;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeDisplay {
    private DatabaseConnector databaseConnector;

    public EmployeeDisplay(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Liste des employés");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Créer un JPanel pour contenir les composants
        JPanel panel = new JPanel(new BorderLayout());

        // Créer un JTable pour afficher la liste des employés
        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        // Ajouter les colonnes au modèle de tableau
        model.addColumn("ID");
        model.addColumn("Nom");
        model.addColumn("Prénom");
        model.addColumn("Age");
        model.addColumn("Numéro de téléphone");
        model.addColumn("Email");

        // Ajouter des lignes de données fictives pour la démonstration
        model.addRow(new Object[]{"1", "Nom1", "Prénom1", 25, "123456789", "email1@example.com"});
        model.addRow(new Object[]{"2", "Nom2", "Prénom2", 30, "987654321", "email2@example.com"});

        // Créer un JLabel pour l'image
        ImageIcon icon = new ImageIcon("C:\\Users\\pc\\Pictures\\m.png");
        JLabel imageLabel = new JLabel(icon);

        // Ajouter le tableau à gauche et l'image à droite
        panel.add(scrollPane, BorderLayout.WEST);
       panel.add(imageLabel, BorderLayout.EAST);

        frame.getContentPane().add(panel);
        frame.setSize(900, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        displayEmployees(model);
    }

    void displayEmployees(DefaultTableModel model) {
        try {
            // Effacer les données actuelles du modèle
            model.setRowCount(0);

            String query = "SELECT idEmp, Employenom, Employeprenom, age, numero, email FROM employee";
            PreparedStatement statement = databaseConnector.getConnection().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String id = resultSet.getString("idEmp");
                String nom = resultSet.getString("Employenom");
                String prenom = resultSet.getString("Employeprenom");
                int age = resultSet.getInt("age");
                String phoneNumber = resultSet.getString("numero");
                String email = resultSet.getString("email");

                // Ajouter une nouvelle ligne au modèle avec les données de l'employé
                model.addRow(new Object[]{id, nom, prenom, age, phoneNumber, email});
            }

            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

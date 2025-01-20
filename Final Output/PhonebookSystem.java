import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class PhonebookSystem extends JFrame implements ActionListener {
    private JTextField nameField, phoneField;
    private JTextArea displayArea;
    private String filename = "phonebook.txt";
    private String[] contacts = new String[100];
    private int contactCount = 0;

    public PhonebookSystem() {
        setTitle("Phonebook System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLayout(new BorderLayout());

        // Main Frame Backround Color
        getContentPane().setBackground(new Color(230, 230, 255)); // Very light purple

        // Panel setup
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        inputPanel.setBackground(new Color(200, 180, 255)); // Light lavender
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputPanel.add(createLabel("Name:"));

        nameField = createTextField();
        inputPanel.add(nameField);

        inputPanel.add(createLabel("Phone:"));

        phoneField = createTextField();
        phoneField.setEditable(true);
        inputPanel.add(phoneField);

        add(inputPanel, BorderLayout.NORTH);

        // Main Display Area
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        displayArea.setBackground(new Color(240, 230, 255)); // Light pastel purple
        displayArea.setForeground(new Color(60, 0, 90)); // Dark purple
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(150, 120, 255), 2));
        add(scrollPane, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(200, 180, 255)); // Light lavender

        JButton addButton = createButton("Add");
        buttonPanel.add(addButton);

        JButton searchButton = createButton("Search");
        buttonPanel.add(searchButton);

        JButton deleteButton = createButton("Delete");
        buttonPanel.add(deleteButton);

        JButton editButton = createButton("Edit");
        buttonPanel.add(editButton);

        JButton selectButton = createButton("Select");
        buttonPanel.add(selectButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Load existing contacts
        loadContacts();
        updateDisplay();

        setVisible(true);
    }

    //Helper method to help with ui , for creating button, label and textfield
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(160, 130, 255));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.addActionListener(this);
        return button;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(new Color(60, 0, 90));
        return label;
    }

    private JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setBackground(new Color(240, 230, 255));
        textField.setForeground(new Color(60, 0, 90)); 
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createLineBorder(new Color(150, 120, 255), 1));
        return textField;
    }

    //Action Listener
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals("Add")) {
            addContact();
        } else if (command.equals("Search")) {
            searchContact();
        } else if (command.equals("Delete")) {
            deleteContact();
        } else if (command.equals("Edit")) {
            editContact();
        } else if (command.equals("Select")) {
            selectContact();
        }
    }
    //Function for Adding Contacts
    private void addContact() {
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();

        if (name.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Both name and phone fields must be filled.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!phone.matches("09\\d{9}")) {
            JOptionPane.showMessageDialog(this, "Phone number must start with '09' and be exactly 11 digits.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (int i = 0; i < contactCount; i++) {
            if (contacts[i] != null && (contacts[i].startsWith(name + ",") || contacts[i].endsWith("," + phone))) {
                JOptionPane.showMessageDialog(this, "Duplicate contact found. Please use a unique name or phone number.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        contacts[contactCount++] = name + "," + phone;
        saveContacts();
        updateDisplay();

        nameField.setText("");
        phoneField.setText("");
        JOptionPane.showMessageDialog(this, "Contact added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    //Function for Searching Contacts
    private void searchContact() {
        String name = nameField.getText().trim().toLowerCase();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a name to search.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        StringBuilder results = new StringBuilder();
        for (int i = 0; i < contactCount; i++) {
            if (contacts[i] != null && contacts[i].toLowerCase().contains(name)) {
                results.append(contacts[i]).append("\n");
            }
        }

        if (results.length() > 0) {
            displayArea.setText(results.toString());
        } else {
            JOptionPane.showMessageDialog(this, "No contacts found for: " + name, "Information", JOptionPane.INFORMATION_MESSAGE);
            displayArea.setText("");
        }
    }

    //Function for Deleting Contacts
    private void deleteContact() {
        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a name to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean removed = false;
        for (int i = 0; i < contactCount; i++) {
            if (contacts[i] != null && contacts[i].startsWith(name + ",")) {
                contacts[i] = null;
                removed = true;
            }
        }

        if (removed) {
            saveContacts();
            updateDisplay();
            JOptionPane.showMessageDialog(this, "Contact deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No contact found with the name: " + name, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Function for Editing Existing Contacts
    private void editContact() {
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();

        if (name.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Both name and phone fields must be filled.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!phone.matches("09\\d{9}")) {
            JOptionPane.showMessageDialog(this, "Phone number must start with '09' and be exactly 11 digits.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean found = false;
        for (int i = 0; i < contactCount; i++) {
            if (contacts[i] != null && contacts[i].startsWith(name + ",")) {
                contacts[i] = name + "," + phone;
                found = true;
                break;
            }
        }

        if (!found) {
            JOptionPane.showMessageDialog(this, "No contact found with the name: " + name, "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            saveContacts();
            updateDisplay();
            nameField.setText("");
            phoneField.setText("");
            JOptionPane.showMessageDialog(this, "Contact updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    //Function for Selecting Specific Contact
    private void selectContact() {
        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a name to select.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (int i = 0; i < contactCount; i++) {
            if (contacts[i] != null && contacts[i].startsWith(name + ",")) {
                String[] parts = contacts[i].split(",");
                nameField.setText(parts[0]);
                phoneField.setText(parts[1]);
                phoneField.setEditable(true); // Allow editing after selecting
                JOptionPane.showMessageDialog(this, "Contact selected.", "Information", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }

        JOptionPane.showMessageDialog(this, "No contact found with the name: " + name, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void loadContacts() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            contactCount = 0;
            while ((line = reader.readLine()) != null && contactCount < contacts.length) {
                contacts[contactCount++] = line;
            }
        } catch (IOException e) {
            // File may not exist,yes
        }
    }
  
    private void saveContacts() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (int i = 0; i < contactCount; i++) {
                if (contacts[i] != null) {
                    writer.write(contacts[i]);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving contacts to file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateDisplay() {
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < contactCount; i++) {
            if (contacts[i] != null) {
                content.append(contacts[i]).append("\n");
            }
        }
        displayArea.setText(content.toString());
    }

    public static void main(String[] args) {
        new PhonebookSystem();
    }
}

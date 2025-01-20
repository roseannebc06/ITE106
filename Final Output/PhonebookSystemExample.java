import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class PhonebookSystemExample extends JFrame implements ActionListener {
    private JTextField nameField, phoneField;
    private JTextArea displayArea;
    private String filename = "phonebook.txt";

    public PhonebookSystemExample() {
        setTitle("Phonebook System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        inputPanel.add(new JLabel("Name:"));

        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Phone:"));

        phoneField = new JTextField();
        inputPanel.add(phoneField);

        add(inputPanel, BorderLayout.NORTH);

        displayArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(displayArea);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

        JButton addButton = new JButton("Add");
        addButton.addActionListener(this);
        buttonPanel.add(addButton);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(this);
        buttonPanel.add(searchButton);

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(this);
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);

        loadContacts();
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals("Add")) {
            addContact();
        } else if (command.equals("Search")) {
            searchContact();
        } else if (command.equals("Delete")) {
            deleteContact();
        }
    }

    private void addContact() {
        // Implementation for adding a contact
    }

    private void searchContact() {
        // Implementation for searching a contact
    }

    private void deleteContact() {
        // Implementation for deleting a contact
    }

    private void loadContacts() {
        // Implementation for loading contacts from the file
    }

    public static void main(String[] args) {
        new PhonebookSystemExample();
    }
}

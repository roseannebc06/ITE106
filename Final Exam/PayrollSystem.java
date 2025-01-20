import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class PayrollSystem extends JFrame implements ActionListener {
    // Input Fields
    private JTextField idField, nameField, rateField, hoursField;
    // Pay Summary Fields
    private JTextField grossPayField, taxField, netPayField;
    // Record Area
    private JTextArea recordDisplayArea;
    // Buttons
    private JButton computeButton, saveButton, displayButton, clearButton;
    //File
    private static final String FILE_NAME = "payrollrecords.txt";

    public PayrollSystem() {
        // Colors
        Color primaryColor = new Color(42, 157, 143); // Persian Green
        Color secondaryColor = new Color(38, 70, 83); // Charcoal
        Color backgroundColor = new Color(233, 196, 106); // Saffron
        Color darkBlueColor = new Color(38, 70, 83); // Charcoal
        Color lightPurple = new Color(168, 160, 215); // Light Purple
        //Font
        Font helvetica = new Font("Helvetica", Font.PLAIN, 14);
        Font buttonFont = new Font("Helvetica", Font.BOLD, 12);

        setTitle("Payroll System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 400);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(lightPurple);
        setResizable(false);

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(darkBlueColor, 2), "Employee Details"));
        inputPanel.setBackground(lightPurple);
        inputPanel.setFont(helvetica);

        addLabelAndField(inputPanel, "Employee ID:", darkBlueColor, helvetica);
        idField = createInputField(inputPanel, helvetica);

        addLabelAndField(inputPanel, "Employee Name:", darkBlueColor, helvetica);
        nameField = createInputField(inputPanel, helvetica);

        addLabelAndField(inputPanel, "Rate Per Hour (PHP):", darkBlueColor, helvetica);
        rateField = createInputField(inputPanel, helvetica);

        addLabelAndField(inputPanel, "Hours Worked:", darkBlueColor, helvetica);
        hoursField = createInputField(inputPanel, helvetica);

        computeButton = createStyledButton("Compute Pay", primaryColor, darkBlueColor, buttonFont);
        computeButton.addActionListener(this);
        inputPanel.add(computeButton);

        add(inputPanel, BorderLayout.NORTH);

        // Pay Summary Pane
        JPanel summaryPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        summaryPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(darkBlueColor, 2), "Pay Summary"));
        summaryPanel.setBackground(lightPurple);

        grossPayField = createSummaryField(summaryPanel, "Gross Pay:", darkBlueColor, helvetica);
        taxField = createSummaryField(summaryPanel, "Tax (20%):", darkBlueColor, helvetica);
        netPayField = createSummaryField(summaryPanel, "Net Pay:", darkBlueColor, helvetica);

        add(summaryPanel, BorderLayout.CENTER);

        // Button Pane
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(lightPurple);

        saveButton = createStyledButton("Save Record", primaryColor, darkBlueColor, buttonFont);
        saveButton.addActionListener(this);
        buttonPanel.add(saveButton);

        displayButton = createStyledButton("Display Records", primaryColor, darkBlueColor, buttonFont);
        displayButton.addActionListener(this);
        buttonPanel.add(displayButton);

        clearButton = createStyledButton("Clear Fields", primaryColor, darkBlueColor, buttonFont);
        clearButton.addActionListener(this);
        buttonPanel.add(clearButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Record Display Pane
        JPanel recordPanel = new JPanel(new BorderLayout());
        recordPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(darkBlueColor, 2), "Employee Records"));
        recordPanel.setBackground(lightPurple);

        recordDisplayArea = new JTextArea(10, 40);
        recordDisplayArea.setEditable(false);
        recordDisplayArea.setFont(helvetica);
        recordDisplayArea.setBackground(Color.WHITE);
        recordDisplayArea.setForeground(darkBlueColor);

        JScrollPane scrollPane = new JScrollPane(recordDisplayArea);
        recordPanel.add(scrollPane, BorderLayout.CENTER);

        add(recordPanel, BorderLayout.EAST);

        setVisible(true);
    }

    // Helper Methods
    private void addLabelAndField(JPanel panel, String labelText, Color color, Font font) {
        JLabel label = new JLabel(labelText);
        label.setForeground(color);
        label.setFont(font);
        panel.add(label);
    }

    private JTextField createInputField(JPanel panel, Font font) {
        JTextField field = new JTextField();
        field.setFont(font);
        panel.add(field);
        return field;
    }

    private JTextField createSummaryField(JPanel panel, String labelText, Color color, Font font) {
        JLabel label = new JLabel(labelText);
        label.setForeground(color);
        label.setFont(font);
        panel.add(label);

        JTextField field = new JTextField();
        field.setEditable(false);
        field.setBackground(new Color(238, 238, 238)); // Light gray
        field.setFont(font);
        panel.add(field);
        return field;
    }

    private JButton createStyledButton(String text, Color bgColor, Color textColor, Font font) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(textColor);
        button.setFont(font);
        button.setFocusPainted(false);
        button.setBorder(new LineBorder(textColor, 2, true));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(textColor);
                button.setForeground(bgColor);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
                button.setForeground(textColor);
            }
        });
        return button;
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == computeButton) {
            computePay();
        } else if (source == saveButton) {
            saveRecord();
        } else if (source == displayButton) {
            displayRecords();
        } else if (source == clearButton) {
            clearFields();
        }
    }

    private void computePay() {
        try {
            double rate = Double.parseDouble(rateField.getText().trim());
            double hours = Double.parseDouble(hoursField.getText().trim());
            double grossPay = rate * hours;
            double tax = grossPay * 0.2;
            double netPay = grossPay - tax;

            grossPayField.setText(String.format("%.2f", grossPay));
            taxField.setText(String.format("%.2f", tax));
            netPayField.setText(String.format("%.2f", netPay));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input! Please enter valid numbers for Rate and Hours.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    //Helper for Name Checking
    private boolean isValidName(String name) {
        return name.matches("[a-zA-Z\\s]+");
    }


    private void saveRecord() {
    String id = idField.getText().trim();
    String name = nameField.getText().trim();
    String grossPay = grossPayField.getText().trim();
    String tax = taxField.getText().trim();
    String netPay = netPayField.getText().trim();

    if (id.isEmpty() || name.isEmpty() || grossPay.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Compute pay before saving.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    //Check if Name is Valid
    if (!isValidName(name)) {
        JOptionPane.showMessageDialog(this, "Invalid name! Only letters and spaces are allowed.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    try {
        File file = new File(FILE_NAME);
        StringBuilder updatedContent = new StringBuilder();
        boolean idExists = false;

        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.contains("ID: " + id + ",")) {
                        idExists = true;
                        int choice = JOptionPane.showConfirmDialog(this,
                            "This ID already exists. Do you want to update the record?",
                            "Duplicate ID",
                            JOptionPane.YES_NO_OPTION);

                        if (choice == JOptionPane.NO_OPTION) {
                            return;
                        }
                        // Update existing record
                        updatedContent.append("ID: ").append(id)
                            .append(", Name: ").append(name)
                            .append(", Gross Pay: PHP ").append(grossPay)
                            .append(", Tax: PHP ").append(tax)
                            .append(", Net Pay: PHP ").append(netPay).append("\n");
                    } else {
                        updatedContent.append(line).append("\n");
                    }
                }
            }
        }

        if (!idExists) {
            updatedContent.append("ID: ").append(id)
                .append(", Name: ").append(name)
                .append(", Gross Pay: PHP ").append(grossPay)
                .append(", Tax: PHP ").append(tax)
                .append(", Net Pay: PHP ").append(netPay).append("\n");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            writer.write(updatedContent.toString());
        }

        JOptionPane.showMessageDialog(this, idExists ? "Record updated!" : "Record saved!");

    } catch (IOException ex) {
        JOptionPane.showMessageDialog(this, "Error saving record!", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    private void displayRecords() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            recordDisplayArea.setText(content.toString());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error reading records!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        rateField.setText("");
        hoursField.setText("");
        grossPayField.setText("");
        taxField.setText("");
        netPayField.setText("");
        recordDisplayArea.setText("");
    }

    public static void main(String[] args) {
        new PayrollSystem();
    }
}

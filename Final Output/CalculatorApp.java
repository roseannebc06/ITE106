import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class CalculatorApp extends JFrame implements ActionListener {
    private JTextField display;
    private JTextArea historyArea;
    private double firstNumber = 0;
    private String operator = "";
    private boolean isOperatorClicked = false;

    public CalculatorApp() {
        setTitle("Calculator");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        display = new JTextField();
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.BOLD, 30));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setPreferredSize(new Dimension(350, 70));
        add(display, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 10, 10));

        String[] buttons = {
            "7", "8", "9", "/", 
            "4", "5", "6", "*", 
            "1", "2", "3", "-", 
            ".", "0", "=", "+",
            "C"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 22));
            button.setBackground(new Color(220, 220, 220));
            button.setFocusPainted(false);
            button.addActionListener(this);
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);

        historyArea = new JTextArea();
        historyArea.setEditable(false);
        historyArea.setFont(new Font("Arial", Font.PLAIN, 16));
        JScrollPane scrollPane = new JScrollPane(historyArea);
        scrollPane.setPreferredSize(new Dimension(350, 150));
        add(scrollPane, BorderLayout.SOUTH);

        loadHistory();

        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if ("0123456789".contains(command)) {
            if (isOperatorClicked) {
                display.setText("");
                isOperatorClicked = false;
            }
            display.setText(display.getText() + command);
        } else if ("+-*/".contains(command)) {
            firstNumber = Double.parseDouble(display.getText());
            operator = command;
            isOperatorClicked = true;
        } else if ("=".equals(command)) {
            calculate();
        } else if ("C".equals(command)) {
            display.setText("");
            firstNumber = 0;
            operator = "";
            isOperatorClicked = false;
        }
    }

    private void calculate() {
        try {
            double secondNumber = Double.parseDouble(display.getText());
            double result = 0;

            switch (operator) {
                case "+": result = firstNumber + secondNumber; break;
                case "-": result = firstNumber - secondNumber; break;
                case "*": result = firstNumber * secondNumber; break;
                case "/": 
                    result = secondNumber != 0 ? firstNumber / secondNumber : 0;
                    if (secondNumber == 0) {
                        JOptionPane.showMessageDialog(this, "Cannot divide by zero!");
                    }
                    break;
            }

            String calculation = firstNumber + " " + operator + " " + secondNumber + " = " + result;
            display.setText(String.valueOf(result));
            updateHistory(calculation);
            saveToHistory(calculation);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please try again.");
            display.setText("");
        }
    }

    private void updateHistory(String calculation) {
        historyArea.append(calculation + "\n");
    }

    private void saveToHistory(String calculation) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("calculator_history.txt", true))) {
            writer.write(calculation);
            writer.newLine();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving to history: " + ex.getMessage());
        }
    }

    private void loadHistory() {
        try (BufferedReader reader = new BufferedReader(new FileReader("calculator_history.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                historyArea.append(line + "\n");
            }
        } catch (IOException ex) {
            System.out.println("No previous history found.");
        }
    }

    public static void main(String[] args) {
        new CalculatorApp();
    }
}

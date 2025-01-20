import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CalculatorExample extends JFrame implements ActionListener {
    private JTextField display;
    private double num1, num2, result;
    private String operator;

    public CalculatorExample() {
        setTitle("Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 400);
        setLayout(new BorderLayout());

        display = new JTextField();
        display.setEditable(false);
        add(display, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4));

        String[] buttons = {
            "7", "8", "9", "/", 
            "4", "5", "6", "*", 
            "1", "2", "3", "-", 
            "0", ".", "=", "+"
        };

        for (String text : buttons) {
            JButton b = new JButton(text);
            b.addActionListener(this);
            buttonPanel.add(b);
        }

        add(buttonPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (Character.isDigit(command.charAt(0)) || command.equals(".")) {
            display.setText(display.getText() + command);
        } else if (command.equals("=")) {
            num2 = Double.parseDouble(display.getText());
            calculate();
            display.setText(String.valueOf(result));
        } else {
            operator = command;
            num1 = Double.parseDouble(display.getText());
            display.setText("");
        }
    }

    private void calculate() {
        switch (operator) {
            case "+": 
                result = num1 + num2;
                break;
            case "-": 
                result = num1 - num2; 
                break;
            case "*": 
                result = num1 * num2; 
                break;
            case "/": 
                result = num1 / num2;
                break;
        }
    }

    public static void main(String[] args) {
        new CalculatorExample();
    }
}

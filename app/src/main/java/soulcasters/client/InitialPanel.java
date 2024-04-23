package soulcasters.client;

import java.awt.*;
import javax.swing.*;

public class InitialPanel extends JPanel {

  private JTextField ipField;
  private JLabel connectionStatus;

  // Constructor for the initial panel.
  public InitialPanel(InitialControl ic) {
    // Create the controller.
    // InitialControl controller = new InitialControl(container);

    // Create the information label.
    JLabel label = new JLabel("Account Information", JLabel.CENTER);

    // Create the login button.
    JButton loginButton = new JButton("Login");
    loginButton.addActionListener(ic);
    JPanel loginButtonBuffer = new JPanel();
    loginButtonBuffer.add(loginButton);

    // Create the create account button.
    JButton createButton = new JButton("Create");
    createButton.addActionListener(ic);
    JPanel createButtonBuffer = new JPanel();
    createButtonBuffer.add(createButton);

    // Create the server IP text field and button
    ipField = new JTextField(15);
    JButton connectButton = new JButton("Connect");
    connectButton.addActionListener(ic);
    connectButton.setActionCommand("Connect");

    JPanel ipConfigPanel = new JPanel();
    ipConfigPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
    ipConfigPanel.add(ipField);
    ipConfigPanel.add(connectButton);

    // Create status label.
    connectionStatus = new JLabel("Not Connected");
    JPanel statusBuffer = new JPanel();
    statusBuffer.add(connectionStatus);

    // Arrange the components in a grid.
    JPanel grid = new JPanel(new GridLayout(5, 1, 5, 5));
    grid.add(label);
    grid.add(loginButtonBuffer);
    grid.add(createButtonBuffer);
    grid.add(ipConfigPanel);
    grid.add(statusBuffer);
    this.add(grid);
  }

  public String getIp() {
    return ipField.getText();
  }

  public void setStatus(String status, Color color) {
    this.connectionStatus.setText(status);
    this.connectionStatus.setForeground(color);
  }
}

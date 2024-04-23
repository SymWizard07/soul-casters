package soulcasters.client;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class InitialControl implements ActionListener {
  // Private data field for storing the container.
  private JPanel container;
  private ChatClient client;

  // Constructor for the initial controller.
  public InitialControl(JPanel container, ChatClient client) {
    this.container = container;
    this.client = client;
  }

  // Handle button clicks.
  public void actionPerformed(ActionEvent ae) {
    // Get the name of the button clicked.
    String command = ae.getActionCommand();

    // The Login button takes the user to the login panel.
    if (command.equals("Login")) {
      LoginPanel loginPanel = (LoginPanel) container.getComponent(1);
      loginPanel.setError("");
      CardLayout cardLayout = (CardLayout) container.getLayout();
      cardLayout.show(container, "2");

    }

    // The Create button takes the user to the create account panel.
    else if (command.equals("Create")) {
      CreateAccountPanel createAccountPanel = (CreateAccountPanel) container.getComponent(2);
      createAccountPanel.setError("");
      CardLayout cardLayout = (CardLayout) container.getLayout();
      cardLayout.show(container, "3");
    }

    else if (command.equals("Connect")) {
      if (client.isConnected()) {
        try {
          client.closeConnection();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      InitialPanel initialPanel = (InitialPanel) container.getComponent(0);
      String ip = initialPanel.getIp();
      client.setHost(ip);
      try {
        client.openConnection();
        initialPanel.setStatus("Connected to server!", Color.GREEN);
      } catch (Exception e) {
        initialPanel.setStatus("Could not connect to server.", Color.RED);
        e.printStackTrace();
      }
    }
  }
}

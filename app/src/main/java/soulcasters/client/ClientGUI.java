package soulcasters.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ClientGUI extends JFrame
{
  
  private boolean isFullScreen = false;
  
  // Constructor that creates the client GUI.
  public ClientGUI()
  {
    // Set up the chat client.
   ChatClient client = new ChatClient();
    client.setHost("localhost");
    client.setPort(8300);
    
    // Set the title and default close operation.
    this.setTitle("Chat Client");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLocationRelativeTo(null);

    setupFullScreenToggleKey();
        
    // Create the card layout container.
    CardLayout cardLayout = new CardLayout();
    JPanel container = new JPanel(cardLayout);

    container.setBackground(Color.RED);
    
    //Create the Controllers next
    //Next, create the Controllers
    InitialControl ic = new InitialControl(container,client);
    LoginControl lc = new LoginControl(container,client);
    CreateAccountControl cac = new CreateAccountControl(container,client);
    GamePanelControl gpc = new GamePanelControl(container, client);
    
    //Set the client info
    client.setLoginControl(lc);
    client.setCreateAccountControl(cac);
    client.setGamePanelControl(gpc);
    
    // Create the four views. (need the controller to register with the Panels
    JPanel view1 = new InitialPanel(ic);
    JPanel view2 = new LoginPanel(lc);
    JPanel view3 = new CreateAccountPanel(cac);
    JPanel view4 = new GamePanel(gpc);
    
    // Add the views to the card layout container.
    container.add(view1, "1");
    container.add(view2, "2");
    container.add(view3, "3");
    container.add(view4, "4");
   
    
    // Show the initial view in the card layout.
    cardLayout.show(container, "1");
    
    // Add the card layout container to the JFrame.
    // GridBagLayout makes the container stay centered in the window.
    //this.setLayout(new GridBagLayout());
    this.add(container);

    // Show the JFrame.
    // 320x200
    this.setSize(640, 400);
    this.setVisible(true);
  }

  private void setupFullScreenToggleKey() {
    // Get the root pane and input map for key bindings
    JRootPane rootPane = this.getRootPane();
    InputMap inputMap = rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
    ActionMap actionMap = rootPane.getActionMap();

    // Key stroke for F11
    KeyStroke f11KeyStroke = KeyStroke.getKeyStroke("F11");
    inputMap.put(f11KeyStroke, "FullScreenToggle");

    // Action to toggle full screen
    actionMap.put("FullScreenToggle", new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            toggleFullScreen();
        }
    });
}

  private void toggleFullScreen() {
    GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    if (!isFullScreen) {
        // Enter full screen mode
        if (device.isFullScreenSupported()) {
            dispose(); // Dispose the frame before setting undecorated and full screen
            setUndecorated(true);
            device.setFullScreenWindow(this);
            setVisible(true);
            isFullScreen = true;
        } else {
            System.out.println("Full-screen mode not supported");
        }
    } else {
        // Exit full screen mode
        dispose(); // Dispose the frame before resetting decoration and full screen
        setUndecorated(false);
        device.setFullScreenWindow(null);
        setVisible(true);
        isFullScreen = false;
    }
}

  // Main function that creates the client GUI when the program is started.
  public static void main(String[] args)
  {
    new ClientGUI();
  }
}

import soulcasters.client.ChatClient;
import soulcasters.client.GamePanel;
import soulcasters.client.GamePanelControl;
import soulcasters.client.LoginControl;
import soulcasters.shared.LoginData;

import java.io.IOException;

import javax.swing.*;
import java.awt.*;

public class ClientGUITest {
    public static void main(String[] args) {
        JFrame frame = new JFrame();

        ChatClient client = new ChatClient();
        client.setHost("localhost");
        client.setPort(8300);

        frame.setTitle("Chat Client Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel container = new JPanel(new CardLayout());

        GamePanelControl gpc = new GamePanelControl(container, client);
        JPanel gamePanel = new GamePanel(gpc);
        client.setGamePanelControl(gpc);

        container.add(gamePanel, "4");
        frame.add(container);

        frame.setSize(640, 400);
        frame.setVisible(true);

        LoginControl loginControl = new LoginControl(container, client);
        client.setLoginControl(loginControl);

        try {
            client.openConnection();
            client.sendToServer(new LoginData("1", "123456"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

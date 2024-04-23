package soulcasters.client;

import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.io.IOException;
import java.awt.event.MouseEvent;

import javax.swing.*;

import soulcasters.client.game.GameController;
import soulcasters.client.game.OptionsDisplay;
import soulcasters.shared.CombinedEntityData;
import soulcasters.shared.DataRequest;
import soulcasters.shared.SelectedOptionData;

public class GamePanelControl extends MouseAdapter {

    // Private data fields for the container and chat client.
    private JPanel container;
    private GamePanel gamePanel;
    private ChatClient client;
    private GameController gc;
    private Long sessionToken;

    // Constructor for the game panel controller
    public GamePanelControl(JPanel container, ChatClient client) {
        this.container = container;
        this.client = client;

        gc = new GameController(this);
        gc.start();
    }

    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void sendSelectedOption(int entityId, String selectedOption) {
        if (sessionToken == null) {
            return;
        }
        try {
            client.sendToServer(new SelectedOptionData(sessionToken, entityId, selectedOption));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void requestEntityData() {
        if (sessionToken == null) {
            return;
        }
        try {
            if (client.inGame) {
                client.sendToServer(new DataRequest(sessionToken, "reqEntityData"));
            }
        } catch (IOException e) {
            System.out.println("Could not request entity data");
            e.printStackTrace();
        }
    }

    public void recieveCombinedEntityData(CombinedEntityData combinedEntityData) {
        gc.updateEntityData(combinedEntityData);
    }

    public void addOptionsPanel(OptionsDisplay optionsPanel) {
        gamePanel.addOptionsPanel(optionsPanel);
    }

    public void removeOptionsPanel(OptionsDisplay optionsPanel) {
        gamePanel.removeOptionsPanel(optionsPanel);
    }

    public void setSessionData(long sessionToken, int playerId) {
        this.sessionToken = sessionToken;
        gc.setPlayerId(playerId);
    }

    public void mouseClicked(MouseEvent e) {
        gc.checkClick(e.getX(), e.getY());
    }

    public void render(Graphics2D g) {
        gc.render(g);
    }

    public void repaintPanel() {
        container.repaint();
    }
}

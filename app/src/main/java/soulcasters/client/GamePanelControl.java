package soulcasters.client;

import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.io.IOException;

import javax.swing.*;

import soulcasters.client.game.GameController;
import soulcasters.client.game.OptionsDisplay;
import soulcasters.shared.CombinedEntityData;
import soulcasters.shared.DataRequest;
import soulcasters.shared.SelectedOptionData;

public class GamePanelControl extends MouseAdapter {

    // Private data fields for the container and chat client.
    private JPanel container;
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

    public void sendSelectedOption(int entityId, int optionIndex) {
        if (sessionToken == null) {
            return;
        }
        try {
            client.sendToServer(new SelectedOptionData(sessionToken, entityId, optionIndex));
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
        container.add(optionsPanel);
    }

    public void setSessionToken(long sessionToken) {
        this.sessionToken = sessionToken;
    }

    public void render(Graphics2D g) {
        gc.render(g);
    }

    public void repaintPanel() {
        container.repaint();
    }
}

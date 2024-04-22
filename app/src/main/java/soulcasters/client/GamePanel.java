package soulcasters.client;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import soulcasters.client.game.OptionsDisplay;

import javax.swing.JLabel;

public class GamePanel extends JPanel {

    private GamePanelControl gpc;
    private JPanel optionsOverlay;
    
    public GamePanel(GamePanelControl gpc) {
        this.gpc = gpc;
        addMouseListener(gpc);
        setLayout(new BorderLayout());

        optionsOverlay = new JPanel();
        optionsOverlay.setLayout(null);
        optionsOverlay.setOpaque(false);
        add(optionsOverlay);

        gpc.setGamePanel(this);
    }

    public void addOptionsPanel(OptionsDisplay optionsPanel) {
        optionsOverlay.add(optionsPanel);
    }

    public void removeOptionsPanel(OptionsDisplay optionsPanel) {
        optionsOverlay.remove(optionsPanel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        gpc.render(g2d);
        
    }
}

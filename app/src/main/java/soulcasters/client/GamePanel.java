package soulcasters.client;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.Color;

public class GamePanel extends JPanel {

    private GamePanelControl gpc;
    
    public GamePanel(GamePanelControl gpc) {
        this.gpc = gpc;
        addMouseListener(gpc);
        setLayout(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        gpc.render(g2d);
        
    }
}

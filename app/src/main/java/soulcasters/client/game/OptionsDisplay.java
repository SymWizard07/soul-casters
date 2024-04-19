package soulcasters.client.game;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class OptionsDisplay extends JPanel {

    private EntityHandler entityHandler;
    private String[][] options;
    private int entityId;

    private class OptionIndexListener implements ActionListener {
        private int optionIndex;

        public OptionIndexListener(int optionIndex) {
            this.optionIndex = optionIndex;
        }

        public void actionPerformed(ActionEvent e) {
            sendSelectedOption(optionIndex);
        }
    }
    
    public OptionsDisplay(EntityHandler entityHandler, String[][] options, int entityId) {
        this.entityHandler = entityHandler;
        this.options = options;
        this.entityId = entityId;

        Color panelColor = new Color(48, 42, 53);
        setBackground(panelColor);

        Border buttonBorder = BorderFactory.createLineBorder(new Color(75, 70, 85), 2);

        if (options != null) {
            for (int i = 0; i < options.length; i++) {
                JButton optionButton = new JButton(options[i][1]);
                optionButton.setBackground(panelColor);
                optionButton.setForeground(Color.WHITE);
                optionButton.setBorder(buttonBorder);
                optionButton.setFocusPainted(false);
                optionButton.setOpaque(true);
    
                optionButton.addActionListener(new OptionIndexListener(i));
    
                entityHandler.addOptionsPanel(this);
            }
        }
    }

    private void sendSelectedOption(int optionIndex) {
        entityHandler.sendSelectedOption(entityId, optionIndex);
    }

    public String[][] getOptions() {
        return options;
    }

    public void renderPanel(int x, int y) {
        if (x < 0) {
            x += 200;
        }
        if (y < 0) {
            y += 30 * options.length * 2;
        }
        setBounds(x, y, 100, 30 * options.length);
    }

}

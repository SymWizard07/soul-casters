package soulcasters.client.game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
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
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        Border buttonBorder = BorderFactory.createLineBorder(new Color(75, 70, 85), 2);

        if (options != null) {
            for (int i = 0; i < options.length; i++) {
                JButton optionButton = new JButton(options[i][1]);
                optionButton.setBackground(panelColor);
                optionButton.setForeground(Color.WHITE);
                optionButton.setBorder(buttonBorder);
                optionButton.setFocusPainted(false);
                optionButton.setOpaque(true);
                optionButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align the button
                optionButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
    
                optionButton.addActionListener(new OptionIndexListener(i));

                add(optionButton);
            }
        }
        entityHandler.addOptionsPanel(this);
    }

    private void sendSelectedOption(int optionIndex) {
        entityHandler.sendSelectedOption(entityId, optionIndex);
    }

    public void removePanel() {
        entityHandler.removeOptionsPanel(this);
    }

    public String[][] getOptions() {
        return options;
    }

    public void showUpdatedPanel(int x, int y) {
        if (x < 0) {
            x += 200;
        }
        if (y < 0) {
            y += 30 * options.length * 2;
        }
        setBounds(x, y, 200, 30 * options.length);
        setVisible(true);
        revalidate();
    }

    public void hidePanel() {
        setVisible(false);
    }

}

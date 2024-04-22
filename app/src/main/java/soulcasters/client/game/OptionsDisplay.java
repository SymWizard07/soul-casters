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
    private boolean isText;

    private class OptionIndexListener implements ActionListener {
        private String selectedOption;

        public OptionIndexListener(String selectedOption) {
            this.selectedOption = selectedOption;
        }

        public void actionPerformed(ActionEvent e) {
            sendSelectedOption(selectedOption);
            hidePanel();
        }
    }

    public OptionsDisplay(EntityHandler entityHandler, String[][] options, int entityId, boolean isText) {
        this.entityHandler = entityHandler;
        this.options = options;
        this.entityId = entityId;
        this.isText = isText;

        if (!isText) {
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

                    optionButton.addActionListener(new OptionIndexListener(options[i][0]));

                    add(optionButton);
                }
            }
        }
        else {
            JLabel text = new JLabel();
            Font textFont;
            Color textColor;

            for (String[] strings : options) {
                switch (strings[0]) {
                    case "text":
                        text.setText(strings[1]);
                        break;
                    case "size":
                        textFont = new Font("Arial", Font.PLAIN, Integer.parseInt(strings[1]));
                        text.setFont(textFont);
                        break;
                    case "color":
                        textColor = new Color(Integer.parseInt(strings[1]));
                        text.setForeground(textColor);
                        break;
                }
            }

            setLayout(new FlowLayout(FlowLayout.LEFT));

            add(text);
            setOpaque(false);
        }

        entityHandler.addOptionsPanel(this);
    }

    private void sendSelectedOption(String selectedOption) {
        entityHandler.sendSelectedOption(entityId, selectedOption);
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
        if(!isText) {
            setBounds(x, y, 200, options.length * 30);
        }
        else {
            setBounds(x, y, 500, 500);
        }
        setVisible(true);
        revalidate();
    }

    public void hidePanel() {
        setVisible(false);
    }

}

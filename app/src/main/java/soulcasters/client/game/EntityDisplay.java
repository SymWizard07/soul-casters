package soulcasters.client.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Arrays;

import javax.swing.ImageIcon;

import soulcasters.Constants;

public class EntityDisplay {

    private EntityHandler entityHandler;
    private int id;
    private String type;
    private int screenX, screenY;
    private int virtualX, virtualY;
    private int width, height;
    private double scale;
    private OptionsDisplay options;
    private boolean showOptions;
    private boolean isMenu;
    private Image sprite, resizedSprite;

    public EntityDisplay(EntityHandler entityHandler, int id, String type, int virtualX, int virtualY, int width, int height) {
        this.entityHandler = entityHandler;
        this.id = id;
        this.type = type;
        this.virtualX = virtualX;
        this.virtualY = virtualY;
        this.width = width;
        this.height = height;
        showOptions = false;
        isMenu = false;

        if (!type.equals("text")) {
            loadSprite();
        }
        else {
            showOptions = true;
        }
    }

    private void loadSprite() {
        String imgPath = "sprites/" + type + ".png";
        java.net.URL imgURL = getClass().getClassLoader().getResource(imgPath);
        if (imgURL == null) {
            imgURL = getClass().getClassLoader().getResource("sprites/unknown.png");
        }
        sprite = new ImageIcon(imgURL).getImage();
    }

    public boolean checkClick(int mouseX, int mouseY) {
        if (this.options == null || type.equals("text")) {
            return false;
        }
        if (mouseX > screenX && mouseX < screenX + width * scale && mouseY > screenY && mouseY < screenY + height * scale) {
            showOptions = true;
            return true;
        }
        else {
            showOptions = false;
            options.hidePanel();
            if (isMenu) {
                entityHandler.sendSelectedOption(id, "back");
            }
        }
        return false;
    }

    public void flipSprite() {
        if (type.equals("text")) {
            return;
        }
        sprite = Constants.flipImage(sprite);
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public int getVirtualX() {
        return virtualX;
    }

    public int getVirtualY() {
        return virtualY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPosition(int x, int y) {
        this.virtualX = x;
        this.virtualY = y;
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void setOptions(String[][] options) {
        if (options == null) {
            return;
        }
        showOptions = false;
        isMenu = false;
        if (options.length >= 1 && options[0][0].equals("properties")) {
            for (String property : options[0]) {
                if (property.equals("isMenu")) {
                    isMenu = true;
                    showOptions = true;
                }
            }
            String[][] newOptions = new String[options.length - 1][2];
            for (int i = 1; i < options.length; i++) {
                newOptions[i - 1] = options[i];
            }
            options = newOptions;
        }
        if (this.options != null && options != null && Arrays.deepEquals(this.options.getOptions(), options)) {
            return;
        }
        if (options == null) {
            this.options.hidePanel();
        }
        if (this.options != null) {
            this.options.removePanel();
        }
        this.options = new OptionsDisplay(entityHandler, options, id, type.equals("text"), isMenu);
    }

    public void render(Graphics2D g, int offsetX, int offsetY, double scale) {
        screenX = (int)(virtualX * scale) + offsetX;
        screenY = (int)(virtualY * scale) + offsetY;
        this.scale = scale;

        if (sprite != null) {
            resizedSprite = Constants.resizeImage(sprite, (int)(width * scale), (int)(width * scale));
            g.drawImage(resizedSprite, screenX, screenY, null);
            if (options != null) {
                g.setColor(Color.WHITE);
                g.drawRect(screenX, screenY, (int)(width * scale), (int)(height * scale));
            }
        }

        if (options != null) {
            if (type.equals("text")) {
                options.showUpdatedPanel(screenX, screenY);
            }
            else if (showOptions) {
                options.showUpdatedPanel(screenX, screenY);
            }
        }
    }
}

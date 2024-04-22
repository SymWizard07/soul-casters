package soulcasters.client.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

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
    private boolean isClicked;
    private Image sprite, resizedSprite;

    public EntityDisplay(EntityHandler entityHandler, int id, String type, int virtualX, int virtualY, int width, int height) {
        this.entityHandler = entityHandler;
        this.id = id;
        this.type = type;
        this.virtualX = virtualX;
        this.virtualY = virtualY;
        this.width = width;
        this.height = height;
        isClicked = false;
        showOptions = false;

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

    public void checkClick(int mouseX, int mouseY) {
        if (this.options == null || type.equals("text")) {
            return;
        }
        if (mouseX > screenX && mouseX < screenX + width * scale && mouseY > screenY && mouseY < screenY + height * scale) {
            showOptions = true;
        }
        else {
            options.hidePanel();
        }
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
        if (this.options != null && options != null && Arrays.deepEquals(this.options.getOptions(), options)) {
            return;
        }
        if (options == null) {
            this.options.hidePanel();
        }
        if (this.options != null) {
            this.options.removePanel();
        }
        this.options = new OptionsDisplay(entityHandler, options, id, type.equals("text"));
    }

    public void render(Graphics2D g, int offsetX, int offsetY, double scale) {
        screenX = (int)(virtualX * scale) + offsetX;
        screenY = (int)(virtualY * scale) + offsetY;
        this.scale = scale;

        if (sprite != null) {
            resizedSprite = Constants.resizeImage(sprite, (int)(width * scale), (int)(width * scale));
            g.drawImage(resizedSprite, screenX, screenY, null);
        }

        if (options != null && showOptions) {
            if (!type.equals("text")) {
                options.showUpdatedPanel(screenX, screenY);
                showOptions = false;
            }
            else {
                options.showUpdatedPanel(screenX, screenY);
            }
        }
    }
}

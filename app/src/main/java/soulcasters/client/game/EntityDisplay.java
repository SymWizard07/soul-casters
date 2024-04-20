package soulcasters.client.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

import soulcasters.Constants;

public class EntityDisplay {

    private EntityHandler entityHandler;
    private int id;
    private String type;
    private int x, y;
    private int width, height;
    private OptionsDisplay options;
    private boolean isClicked;
    private Image sprite, resizedSprite;

    public EntityDisplay(EntityHandler entityHandler, int id, String type, int x, int y, int width, int height, String[][] options) {
        this.entityHandler = entityHandler;
        this.id = id;
        this.type = type;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.options = new OptionsDisplay(entityHandler, options, id);
        isClicked = false;

        loadSprite();
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
        if (this.options == null) {
            return;
        }
        if (mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height) {
            
        }
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
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
        this.x = x;
        this.y = y;
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void render(Graphics2D g, int offsetX, int offsetY, double scale) {

        if (sprite != null) {
            resizedSprite = Constants.resizeImage(sprite, (int)(width * scale), (int)(width * scale));
            g.drawImage(resizedSprite, (int)(x * scale) + offsetX, (int)(y * scale) + offsetY, null);
        }
    }
}

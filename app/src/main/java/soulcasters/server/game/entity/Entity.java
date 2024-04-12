package soulcasters.server.game.entity;

import soulcasters.server.game.EntityHandler;

public abstract class Entity {

    protected EntityHandler entityHandler;
    protected int x, y;
    protected int width, height;
    protected int id;
    // Each option has a string identifier, and a text description that is displayed to the user.
    protected String[][] options;

    public Entity(EntityHandler entityHandler, int x, int y) {
        this.entityHandler = entityHandler;
        this.x = x;
        this.y = y;
        this.width = 0;
        this.height = 0;
    }

    public void assignId(int id) {
        this.id = id;
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

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract void optionAction(String selectedOption);

    public abstract void update();
}

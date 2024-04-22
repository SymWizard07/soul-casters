package soulcasters.server.game.entity;

import soulcasters.server.game.EntityHandler;
import soulcasters.shared.EntityData;

public abstract class Entity {

    protected EntityHandler entityHandler;
    protected double x, y;
    protected int width, height;
    protected int id;
    protected boolean updateEntity;
    protected String type;

    public Entity(EntityHandler entityHandler, double x, double y) {
        this.entityHandler = entityHandler;
        this.x = x;
        this.y = y;
        this.width = 0;
        this.height = 0;
        updateEntity = true;
    }

    public void assignId(int id) {
        this.id = id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public boolean isWaiting() {
        return updateEntity;
    }

    public void stopWaiting() {
        updateEntity = false;
    }

    public void remove() {
        entityHandler.removeEntity(this);
    }

    public EntityData convertToEntityData() {
        return new EntityData(id, (int)x, (int)y, width, height, type);
    }

    public abstract void update(double deltaTime);
}

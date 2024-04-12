package soulcasters.server.game;

import java.util.ArrayList;

import soulcasters.server.game.entity.Entity;

public class EntityHandler {
    
    private ArrayList<Entity> entityList;
    private int nextId;

    public EntityHandler() {
        entityList = new ArrayList<>();
        nextId = 0;
    }

    public void addEntity(Entity entity) {
        entityList.add(entity);
        entity.assignId(nextId);
        nextId++;
    }

    public void removeEntity(Entity entity) {
        entityList.remove(entity);
    }

    public Entity getEntity(int index) {
        return entityList.get(index);
    }

    public ArrayList<Entity> getEntityByType(Class<?> type) {
        ArrayList<Entity> entityResults = new ArrayList<>();

        for (Entity entity : entityList) {
            if (type.isInstance(entity)) {
                entityResults.add(entity);
            }
        }

        return entityResults;
    }

    public void update() {
        for (Entity entity : entityList) {
            entity.update();
        }
    }
}

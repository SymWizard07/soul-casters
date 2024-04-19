package soulcasters.server.game;

import java.util.ArrayList;

import soulcasters.server.game.entity.Entity;
import soulcasters.shared.EntityData;

public class EntityHandler {
    
    private ArrayList<Entity> entityList;
    private ArrayList<Entity> removalQueue;
    private int nextId;
    private ArrayList<ArrayList<EntityData>> entityDataQueues;

    public EntityHandler() {
        entityList = new ArrayList<>();
        removalQueue = new ArrayList<>();
        nextId = 0;
        entityDataQueues = new ArrayList<>();
    }

    public void addEntity(Entity entity) {
        entityList.add(entity);
        entity.assignId(nextId);
        nextId++;
    }

    public void removeEntity(Entity entity) {
        removalQueue.add(entity);
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
            if (entity.isWaiting()) {
                queueEntityData(entity.convertToEntityData());
            }
        }

        entityList.removeAll(removalQueue);
        removalQueue.clear();
    }

    public void queueEntityData(EntityData entityData) {
        if (entityData instanceof OwnedEntityData) {
            entityDataQueues.get(((OwnedEntityData)(entityData)).ownerId + 1).add(entityData);
        }
        else {
            entityDataQueues.get(0).add(entityData);
        }
    }

    /**
     * The entities to update per player is stored as a slot in an ArrayList, with the first slot
     * representing entities every player should update and the next slots corresponding to the player-to-update's ID
     * 
     * e.x. ([Entities visible to all players][Entities player 1 can interact with][Entities player 2 can interact with])
     * 
     * The system is built on the principle of not having a set number of players, so architecture must be built this way.
     */
    public ArrayList<ArrayList<EntityData>> getEntityQueues() {
        return entityDataQueues;
    }
}

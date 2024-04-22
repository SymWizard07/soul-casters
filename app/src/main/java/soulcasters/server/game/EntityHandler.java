package soulcasters.server.game;

import java.util.ArrayList;

import soulcasters.server.game.entity.Entity;
import soulcasters.shared.EntityData;
import soulcasters.shared.OwnedEntityData;

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
        entityDataQueues.add(new ArrayList<>());
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

    public void update(double deltaTime) {
        for (int i = 0; i < entityList.size(); i++) {
            entityList.get(i).update(deltaTime);
            if (entityList.get(i).isWaiting()) {
                queueEntityData(entityList.get(i).convertToEntityData());
                entityList.get(i).stopWaiting();
            }
        }

        entityList.removeAll(removalQueue);
        removalQueue.clear();
    }

    public void queueEntityData(EntityData entityData) {
        boolean dataReplaced;
        if (entityData instanceof OwnedEntityData) {
            OwnedEntityData ownedData = (OwnedEntityData) entityData;

            if (ownedData.type.equals("text")) {
                for (int i = ownedData.ownerId + 1; i < entityDataQueues.size(); i++) {
                    dataReplaced = false;
                    for (int j = 0; j < entityDataQueues.get(i).size(); j++) {
                        if (entityDataQueues.get(i).get(j).id == entityData.id) {
                            entityDataQueues.get(i).set(j, entityData);
                            dataReplaced = true;
                        }
                    }
                    if (!dataReplaced) {
                        entityDataQueues.get(i).add(entityData);
                    }
                    if (i == 0) {
                        break;
                    }
                }

                return;
            }

            while (entityDataQueues.size() <= ownedData.ownerId + 1) {
                entityDataQueues.add(new ArrayList<>());
            }
            ArrayList<EntityData> ownedDataQueue = entityDataQueues.get(ownedData.ownerId + 1);
            dataReplaced = false;
            for (int i = 0; i < ownedDataQueue.size(); i++) {
                if (ownedDataQueue.get(i).id == entityData.id) {
                    ownedDataQueue.set(i, entityData);
                    dataReplaced = true;
                    break;
                }
            }
            if (!dataReplaced) {
                ownedDataQueue.add(entityData);
                entityDataQueues.set(ownedData.ownerId + 1, ownedDataQueue);
            }
        }
        ArrayList<EntityData> globalDataQueue = entityDataQueues.get(0);
        dataReplaced = false;
        for (int i = 0; i < globalDataQueue.size(); i++) {
            if (globalDataQueue.get(i).id == entityData.id) {
                globalDataQueue.set(i, entityData);
                dataReplaced = true;
                break;
            }
        }
        if (!dataReplaced) {
            globalDataQueue.add(entityData);
            entityDataQueues.set(0, globalDataQueue);
        }
    }

    /**
     * The entities to update per player is stored as a slot in an ArrayList, with
     * the first slot
     * representing entities every player should update and the next slots
     * corresponding to the player-to-update's ID
     * 
     * e.x. ([Entities visible to all players][Entities player 1 can interact
     * with][Entities player 2 can interact with])
     * 
     * The system is built on the principle of not having a set number of players,
     * so architecture must be built this way.
     */
    public ArrayList<ArrayList<EntityData>> getEntityQueues() {
        return entityDataQueues;
    }
}

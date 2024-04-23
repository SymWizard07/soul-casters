package soulcasters.client.game;

import java.awt.Graphics2D;
import java.util.ArrayList;
import soulcasters.shared.CombinedEntityData;
import soulcasters.shared.EntityData;
import soulcasters.shared.OwnedEntityData;

public class EntityHandler {

    private GameController gc;
    private ArrayList<EntityDisplay> entityList;
    private int playerId;

    public EntityHandler(GameController gc) {
        entityList = new ArrayList<>();
        this.gc = gc;
    }

    public void updateList(CombinedEntityData combinedEntityData) {
        ArrayList<EntityDisplay> safeList = new ArrayList<>();
        boolean entityExists;

        for (EntityData entityData : combinedEntityData.visibleEntities) {
            entityExists = false;
            EntityDisplay entityDisplay = getEntityDisplay(entityData.id);
            if (entityDisplay != null) {
                entityExists = true;
                if (entityData.x != null && entityData.y != null)
                    entityDisplay.setPosition(entityData.x, entityData.y);
                if (entityData.width != null && entityData.height != null)
                    entityDisplay.setSize(entityData.width, entityData.height);
                if (entityData.type != null)
                    entityDisplay.setType(entityData.type);
                if (entityData.type.equals("text")) {
                    entityDisplay.setOptions(((OwnedEntityData)entityData).options);
                }
                safeList.add(entityDisplay);
            }
            if (!entityExists) {
                EntityDisplay newDisplay = new EntityDisplay(this, entityData.id, entityData.type, entityData.x, entityData.y, entityData.width, entityData.height);
                if (entityData instanceof OwnedEntityData) {
                    if (((OwnedEntityData)entityData).ownerId == 0 && !((OwnedEntityData)entityData).type.equals("text")) {
                        newDisplay.flipSprite();
                    }
                }
                addEntity(newDisplay);
                safeList.add(newDisplay);
            }
        }
        if (combinedEntityData.interactableEntities != null) {
            for (EntityData entityData : combinedEntityData.interactableEntities) {
                EntityDisplay entityDisplay = getEntityDisplay(entityData.id);
                if (entityDisplay != null) {
                    entityDisplay.setOptions(((OwnedEntityData)entityData).options);
                    safeList.add(entityDisplay);
                }
            }
        }

        entityList = safeList;
    }

    private void addEntity(EntityDisplay entityDisplay) {
        entityList.add(entityDisplay);
    }

    public void checkClick(int mouseX, int mouseY) {
        for (int i = entityList.size() - 1; i >= 0; i--) {
            if (entityList.get(i).checkClick(mouseX, mouseY)) {
                return;
            }
        }
    }

    public EntityDisplay getEntityDisplay(int id) {
        for (EntityDisplay entityDisplay : entityList) {
            if (entityDisplay.getId() == id) {
                return entityDisplay;
            }
        }
        return null;
    }

    public void addOptionsPanel(OptionsDisplay optionsPanel) {
        gc.addOptionsPanel(optionsPanel);
    }

    public void removeOptionsPanel(OptionsDisplay optionsPanel) {
        gc.removeOptionsPanel(optionsPanel);
    }

    public void sendSelectedOption(int entityId, String selectedOption) {
        gc.sendSelectedOption(entityId, selectedOption);
    }

    public void render(Graphics2D g, int offsetX, int offsetY, double scale) {
        int fixedSize = entityList.size();
        for (int i = 0; i < fixedSize; i++) {
            entityList.get(i).render(g, offsetX, offsetY, scale);
        }
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getPlayerId() {
        return playerId;
    }
}

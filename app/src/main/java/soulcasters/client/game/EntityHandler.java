package soulcasters.client.game;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.*;

import java.awt.Rectangle;

import soulcasters.shared.CombinedEntityData;
import soulcasters.shared.EntityData;
import soulcasters.shared.OwnedEntityData;

public class EntityHandler {

    private GameController gc;
    private ArrayList<EntityDisplay> entityList;

    public EntityHandler(GameController gc) {
        entityList = new ArrayList<>();
        this.gc = gc;
    }

    public void updateList(CombinedEntityData combinedEntityData) {
        ArrayList<EntityDisplay> removalList = new ArrayList<>();
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
                if (entityData.remove) {
                    removalList.add(entityDisplay);
                }
            }
            if (!entityExists) {
                addEntity(new EntityDisplay(this, entityData.id, entityData.type, entityData.x, entityData.y, entityData.width, entityData.height));
            }
        }
        for (EntityData entityData : combinedEntityData.interactableEntities) {
            EntityDisplay entityDisplay = getEntityDisplay(entityData.id);
            if (entityDisplay != null) {
                entityDisplay.setOptions(((OwnedEntityData)entityData).options);
            }
        }
    }

    private void addEntity(EntityDisplay entityDisplay) {
        entityList.add(entityDisplay);
    }

    public void checkClick(int mouseX, int mouseY) {
        for (EntityDisplay entityDisplay : entityList) {
            entityDisplay.checkClick(mouseX, mouseY);
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

    public void sendSelectedOption(int entityId, int optionIndex) {
        gc.sendSelectedOption(entityId, optionIndex);
    }

    public void render(Graphics2D g, int offsetX, int offsetY, double scale) {
        int fixedSize = entityList.size();
        for (int i = 0; i < fixedSize; i++) {
            entityList.get(i).render(g, offsetX, offsetY, scale);
        }
    }
}

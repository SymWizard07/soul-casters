package soulcasters.client.game;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.*;

import java.awt.Rectangle;

import soulcasters.shared.CombinedEntityData;
import soulcasters.shared.EntityData;

public class EntityHandler {

    private GameController gc;
    private ArrayList<EntityDisplay> entityList;

    public EntityHandler() {
        entityList = new ArrayList<>();
    }

    public void updateList(CombinedEntityData combinedEntityData) {
        ArrayList<EntityDisplay> removalList = new ArrayList<>();
        boolean entityExists;

        ArrayList<EntityData> combinedList = new ArrayList<>();
        combinedList.addAll(combinedEntityData.visibleEntities);
        combinedList.addAll(combinedEntityData.interactableEntities);

        try {
            for (EntityData entityData : combinedList) {
                entityExists = false;
                for (EntityDisplay entityDisplay : entityList) {
                    if (entityData.id == entityDisplay.getId()) {
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
                }
                if (!entityExists) {
                    addEntity(new EntityDisplay(this, entityData.id, entityData.type, entityData.x, entityData.y,
                            entityData.width, entityData.height, entityData.options));
                }
            }
        } catch (Exception e) {
            System.out.println(combinedEntityData.visibleEntities);
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

    public void addOptionsPanel(OptionsDisplay optionsPanel) {
        gc.addOptionsPanel(optionsPanel);
    }

    public void sendSelectedOption(int entityId, int optionIndex) {
        gc.sendSelectedOption(entityId, optionIndex);
    }

    public void render(Graphics2D g, int offsetX, int offsetY, double scale) {
        for (EntityDisplay entityDisplay : entityList) {
            entityDisplay.render(g, offsetX, offsetY, scale);
        }
    }
}

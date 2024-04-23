package soulcasters.server.game.entity.units;

import soulcasters.server.game.EntityHandler;
import soulcasters.server.game.entity.*;
import soulcasters.server.game.entity.resource.Wood;

import java.util.ArrayList;
import java.awt.geom.Point2D;

public class Lumberjack extends Unit {

    private boolean haulCutWood, gettingWood;
    private Tree assignedTree;
    private double cutTime;

    public Lumberjack(EntityHandler entityHandler, double x, double y, int ownerId) {
        super(entityHandler, x, y, ownerId);
        width = 16;
        height = 16;
        speed = 12;
        type = "lumberjack";
        haulCutWood = false;
        gettingWood = false;

        options = new String[][] {
                { "chop", "Chop Wood" },
                { "chopHaul", "Chop and Haul Wood" },
                { "return", "Return to Fortress" }
        };

        setActiveJob("return");
    }

    @Override
    public void optionAction(String selectedOption) {
        switch (selectedOption) {
            case "chop":
                if (activeJob.name.equals("woodcutting")) {
                    break;
                }
                calculateJobSite();
                setActiveJob("woodcutting");
                break;
            case "chopHaul":
                haulCutWood = true;
                if (activeJob.name.equals("woodcutting")) {
                    break;
                }
                calculateJobSite();
                setActiveJob("woodcutting");
                break;
            case "return":
                assignedTree.setChopper(null);
                assignedTree = null;
                setActiveJob("return");
            default:
                break;
        }
    }

    @Override
    public void update(double deltaTime) {

        if (gettingWood && inFortress) {
            resource = (Wood)entityHandler.getNearestEntity(x, y, "wood");
            gettingWood = false;
            inFortress = false;
            resource.setCarrier(this);
            setActiveJob("return");
        }

        if (resource != null) {
            speed = 5;
        }
        else {
            speed = 12;
        }

        if (inFortress && haulCutWood && !activeJob.name.equals("woodcutting")) {
            calculateJobSite();
            setActiveJob("woodcutting");
        }

        if (atTarget()) {
            cutTime += deltaTime;

            if (cutTime >= 5.0 && activeJob.name.equals("woodcutting")) {
                int woodX = (int)(x + (Math.random() * 20 - 10));
                int woodY = (int)(y + (Math.random() * 20 - 10));
                entityHandler.addEntity(new Wood(entityHandler, woodX, woodY));
                cutTime = 0;

                if (haulCutWood) {
                    optionAction("return");
                    gettingWood = true;
                    targetX = (int)woodX;
                    targetY = (int)woodY;
                }
            }
        }
        else {
            cutTime = 0.0;
        }

        stepToTarget(deltaTime);

        updateEntity = true;
    }

    @Override
    public void calculateJobSite() {
        ArrayList<Entity> trees = entityHandler.getEntityByType("tree");

        double smallestDistance = Double.MAX_VALUE;
        Tree closestTree = null;
        for (Entity entity : trees) {
            Tree tree = (Tree) entity;
            if (tree.getChopper() != null) {
                continue;
            }
            double treeDistance = new Point2D.Double(x, y).distance(new Point2D.Double(tree.getX(), tree.getY()));
            if (smallestDistance > treeDistance) {
                smallestDistance = treeDistance;
                closestTree = tree;
            }
        }

        if (closestTree != null) {
            closestTree.setChopper(this);
            removeJob("woodcutting");
            assignedTree = closestTree;
            jobs.add(new Job((int) (closestTree.getX()) - 5, (int) (closestTree.getY()) + 16, "woodcutting"));
        } else {
            displayStatus("No Trees Available!", false);
        }
    }

}

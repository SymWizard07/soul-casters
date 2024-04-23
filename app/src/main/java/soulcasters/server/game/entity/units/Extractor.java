package soulcasters.server.game.entity.units;

import soulcasters.server.game.EntityHandler;
import soulcasters.server.game.entity.*;
import soulcasters.server.game.entity.resource.Clay;

import java.util.ArrayList;
import java.awt.geom.Point2D;

public class Extractor extends Unit {

    private boolean haulDugClay, gettingClay;
    private Pond assignedPond;
    private double digTime;

    public Extractor(EntityHandler entityHandler, double x, double y, int ownerId) {
        super(entityHandler, x, y, ownerId);
        width = 16;
        height = 16;
        speed = 12;
        type = "extractor";
        haulDugClay = false;
        gettingClay = false;

        options = new String[][] {
                { "dig", "Dig Clay" },
                { "digHaul", "Dig and Haul Clay" },
                { "return", "Return to Fortress" }
        };

        setActiveJob("return");
    }

    @Override
    public void optionAction(String selectedOption) {
        switch (selectedOption) {
            case "dig":
                if (activeJob.name.equals("digging")) {
                    break;
                }
                calculateJobSite();
                setActiveJob("digging");
                break;
            case "digHaul":
                haulDugClay = true;
                if (activeJob.name.equals("digging")) {
                    break;
                }
                calculateJobSite();
                setActiveJob("digging");
                break;
            case "return":
                assignedPond.setExtractor(null);
                assignedPond = null;
                setActiveJob("return");
            default:
                break;
        }
    }

    @Override
    public void update(double deltaTime) {

        if (gettingClay && inFortress) {
            resource = (Clay)entityHandler.getNearestEntity(x, y, "clay");
            gettingClay = false;
            inFortress = false;
            resource.setCarrier(this);
            setActiveJob("return");
        }

        if (resource != null) {
            speed = 7;
        }
        else {
            speed = 12;
        }

        if (inFortress && haulDugClay && !activeJob.name.equals("digging")) {
            calculateJobSite();
            setActiveJob("digging");
        }

        if (atTarget()) {
            digTime += deltaTime;

            if (digTime >= 2.0 && activeJob.name.equals("digging")) {
                int clayX = (int)(x + (Math.random() * 20 - 10));
                int clayY = (int)(y + (Math.random() * 20 - 10));
                entityHandler.addEntity(new Clay(entityHandler, clayX, clayY));
                digTime = 0;

                if (haulDugClay) {
                    optionAction("return");
                    gettingClay = true;
                    targetX = (int)clayX;
                    targetY = (int)clayY;
                }
            }
        }
        else {
            digTime = 0.0;
        }

        stepToTarget(deltaTime);

        updateEntity = true;
    }

    @Override
    public void calculateJobSite() {
        Entity pondEntity = entityHandler.getNearestEntity(x, y, "pond");
        Pond nearestPond = null;
        if (pondEntity != null) {
            nearestPond = (Pond)entityHandler.getNearestEntity(x, y, "pond");
        }

        if (nearestPond != null) {
            nearestPond.setExtractor(this);
            removeJob("digging");
            assignedPond = nearestPond;
            jobs.add(new Job((int) (assignedPond.getX()), (int) (assignedPond.getY()) - 8, "digging"));
        } else {
            displayStatus("No Ponds Available!", false);
        }
    }

}

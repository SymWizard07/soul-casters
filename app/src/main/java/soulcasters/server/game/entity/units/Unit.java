package soulcasters.server.game.entity.units;

import java.util.ArrayList;

import soulcasters.server.game.EntityHandler;
import soulcasters.server.game.entity.OwnedEntity;
import soulcasters.server.game.entity.StatusMessage;
import soulcasters.server.game.entity.resource.Resource;

public abstract class Unit extends OwnedEntity {

    protected ArrayList<Job> jobs;
    protected Job activeJob;
    protected int targetX, targetY;
    protected double speed;
    protected int maxHealth, health;
    protected boolean inFortress;
    protected Resource resource;

    public Unit(EntityHandler entityHandler, double x, double y, int ownerId) {
        super(entityHandler, x, y, ownerId);
        inFortress = true;
        jobs = new ArrayList<>();

        if (ownerId == 0) {
            jobs.add(new Job((int)(TargetLocations.player1Reserve.getX() + (Math.random() * 10) - 5), (int)(TargetLocations.player1Reserve.getY() + (Math.random() * 10) - 5), "return"));
        }
        if (ownerId == 1) {
            jobs.add(new Job((int)(TargetLocations.player2Reserve.getX() + (Math.random() * 10) - 5), (int)(TargetLocations.player2Reserve.getY() + (Math.random() * 10) - 5), "return"));
        }
    }

    public void setActiveJob(String jobName) {
        for (Job job : jobs) {
            if (job.name.equals(jobName)) {
                activeJob = job;
                break;
            }
        }

        if (inFortress && !jobName.equals("return")) {
            if (ownerId == 0) {
                targetX = (int)TargetLocations.player1Gate.getX();
                targetY = (int)TargetLocations.player1Gate.getY();
            }
            if (ownerId == 1) {
                targetX = (int)TargetLocations.player2Gate.getX();
                targetY = (int)TargetLocations.player2Gate.getY();
            }
        }
        else if (jobName.equals("return") && !inFortress) {
            if (ownerId == 0) {
                targetX = (int)TargetLocations.player1Gate.getX();
                targetY = (int)TargetLocations.player1Gate.getY();
            }
            if (ownerId == 1) {
                targetX = (int)TargetLocations.player2Gate.getX();
                targetY = (int)TargetLocations.player2Gate.getY();
            }
        }
        else {
            targetX = activeJob.x;
            targetY = activeJob.y;
        }
    }

    public void removeJob(String jobName) {
        int removalIndex = -1;
        for (int i = 0; i < jobs.size(); i++) {
            if (jobs.get(i).name.equals(jobName)) {
                removalIndex = i;
            }
        }
        if (removalIndex != -1) {
            if (jobs.get(removalIndex) == activeJob) {
                activeJob = null;
            }
            jobs.remove(removalIndex);
        }
    }

    public abstract void calculateJobSite();

    public void stepToTarget(double deltaTime) {

        double dx = targetX - x;
        double dy = targetY - y;

        double distance = Math.sqrt(dx * dx + dy * dy);

        double unitX = dx / distance;
        double unitY = dy / distance;

        if (Double.isNaN(unitX)) {
            unitX = 0;
        }
        if (Double.isNaN(unitY)) {
            unitY = 0;
        }

        x += unitX * speed * deltaTime;
        y += unitY * speed * deltaTime;

        // Handle overshoot
        if (Math.sqrt(Math.pow(((double)targetX) - x, 2) + Math.pow(((double)targetY) - y, 2)) < this.speed * deltaTime) {
            x = targetX;
            y = targetY;
        }

        if (atTarget()) {
            if (inFortress && !activeJob.name.equals("return")) {
                inFortress = false;
            }
            if (!inFortress && activeJob.name.equals("return")) {
                inFortress = true;
            }
            targetX = activeJob.x;
            targetY = activeJob.y;
        }
    }
    
    public boolean atTarget() {
        return x == (double)targetX && y == (double)targetY;
    }

    public boolean inFortress() {
        return inFortress;
    }

    public void dropResource() {
        resource = null;
    }

    public void displayStatus(String status, boolean isSuccess) {
        entityHandler.addEntity(new StatusMessage(entityHandler, x, y - 5, status, isSuccess, ownerId));
    }

    public void update(double deltaTime) {
        if (activeJob != null) {
            stepToTarget(deltaTime);
        }
    }
}

package soulcasters.server.game.entity.units;

import java.util.ArrayList;

import soulcasters.server.game.EntityHandler;
import soulcasters.server.game.entity.OwnedEntity;

public abstract class Unit extends OwnedEntity {

    protected ArrayList<Job> jobs;
    protected Job activeJob;
    protected int targetX, targetY;
    protected double speed;
    protected int maxHealth, health;

    public Unit(EntityHandler entityHandler, double x, double y, int ownerId) {
        super(entityHandler, x, y, ownerId);
    }

    public void setActiveJob(String jobName) {
        for (Job job : jobs) {
            if (job.name.equals(jobName)) {
                activeJob = job;
                return;
            }
        }
    }

    public void stepToTarget(double deltaTime) {
        double dx = targetX - x;
        double dy = targetY - y;

        double distance = Math.sqrt(dx * dx + dy * dy);

        double unitX = dx / distance;
        double unitY = dy / distance;

        x += unitX * speed * deltaTime;
        y += unitY * speed * deltaTime;

        // Handle overshoot
        if (Math.sqrt(Math.pow(targetX - x, 2) + Math.pow(targetY - y, 2)) < this.speed * deltaTime) {
            x = targetX;
            y = targetY;
        }
    }
    
    public boolean atTarget() {
        return (int)x == targetX && (int)y == targetY;
    }
}

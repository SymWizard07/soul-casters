package soulcasters.server.game.entity;

import soulcasters.server.game.EntityHandler;
import soulcasters.server.game.entity.units.Lumberjack;

public class Tree extends Entity {

    private Lumberjack chopper;

    public Tree(EntityHandler entityHandler, double x, double y) {
        super(entityHandler, x, y);

        width = 32;
        height = 32;
        type = "tree";
    }

    public void setChopper(Lumberjack chopper) {
        this.chopper = chopper;
    }

    public Lumberjack getChopper() {
        return chopper;
    }

    @Override
    public void update(double deltaTime) {
    }
    
}

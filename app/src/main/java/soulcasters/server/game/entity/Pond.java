package soulcasters.server.game.entity;

import soulcasters.server.game.EntityHandler;
import soulcasters.server.game.entity.units.Extractor;

public class Pond extends Entity {

    private Extractor extractor;

    public Pond(EntityHandler entityHandler, double x, double y) {
        super(entityHandler, x, y);
        
        width = 32;
        height = 32;
        type = "pond";
    }

    public void setExtractor(Extractor extractor) {
        this.extractor = extractor;
    }

    public Extractor getExtractor() {
        return extractor;
    }

    @Override
    public void update(double deltaTime) {
    }
    
}

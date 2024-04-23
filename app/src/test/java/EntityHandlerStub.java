import soulcasters.client.game.EntityHandler;
import soulcasters.client.game.GameController;
import soulcasters.client.game.OptionsDisplay;

class EntityHandlerStub extends EntityHandler {
    public EntityHandlerStub(GameController gc) {
        super(gc);
    }

    int lastSentId;
    String lastSentOption;

    @Override
    public void sendSelectedOption(int id, String option) {
        this.lastSentId = id;
        this.lastSentOption = option;
    }
}

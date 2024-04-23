import soulcasters.client.game.EntityHandler;
import soulcasters.client.game.OptionsDisplay;

class OptionsDisplayStub extends OptionsDisplay {
    boolean panelShown = false;
    boolean panelHidden = false;
    String[][] options;

    public OptionsDisplayStub(EntityHandler handler, String[][] options, int id, boolean isText, boolean isMenu) {
        super(handler, options, id, isText, isMenu);
        this.options = options;
    }

    @Override
    public void showUpdatedPanel(int x, int y) {
        this.panelShown = true;
    }

    @Override
    public void hidePanel() {
        this.panelHidden = true;
    }

    @Override
    public void removePanel() {
        this.panelHidden = true;
    }
}
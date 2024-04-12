package soulcasters.server.game.entity;

import java.awt.Color;

import soulcasters.server.game.EntityHandler;

public class TextEntity extends Entity {

    private int visibleTo;

    /**
     * Creates a text entity that one or both players may see.
     * 
     * @param entityHandler The entity handler object.
     * @param x The text's X position.
     * @param y The text's Y position.
     * @param text The text to display.
     * @param size The font size of the text.
     * @param color The text's color.
     * @param visibleTo An int signifying the id of the players the text is visible to. 0 for player 1, 1 for player 2, -1 for both players.
     * @param lifetime Total time in milliseconds text is on screen before disappearing. -1 to last forever, or until the entity is removed.
     */
    public TextEntity(EntityHandler entityHandler, int x, int y, String text, int size, Color color, int visibleTo, int lifetime) {
        super(entityHandler, x, y);
        options = new String[][]{
            {"text", text},
            {"size", "" + size},
            {"color", "" + color.getRGB()},
            {"lifetime", "" + lifetime}
        };
        this.visibleTo = visibleTo;
    }

    public int getVisibility() {
        return visibleTo;
    }

    @Override
    public void optionAction(String selectedOption) {
        return;
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
}

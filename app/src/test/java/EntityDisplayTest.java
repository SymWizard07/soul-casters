import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import soulcasters.client.game.EntityDisplay;

import java.awt.image.BufferedImage;

// Integrated Test Class
public class EntityDisplayTest {
    private EntityDisplay entityDisplay;
    private EntityHandlerStub entityHandlerStub;
    private final int id = 1;
    private final String type = "character";
    private final int virtualX = 100;
    private final int virtualY = 150;
    private final int width = 50;
    private final int height = 60;

    @BeforeAll
    public void setUp() {
        entityHandlerStub = new EntityHandlerStub(null);
        entityDisplay = new EntityDisplay(entityHandlerStub, id, type, virtualX, virtualY, width, height);
        entityDisplay.sprite = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);  // Mock image
    }

    @Test
    public void testCheckClick_OutsideBounds_ReturnsFalse() {
        entityDisplay.scale = 1.0;
        entityDisplay.screenX = 100;
        entityDisplay.screenY = 150;

        assertFalse(entityDisplay.checkClick(200, 200));
    }

    @Test
    public void testCheckClick_InsideBounds_ReturnsTrue() {
        entityDisplay.options = new OptionsDisplayStub(entityHandlerStub, new String[][]{{"Option1"}}, id, false, false);
        entityDisplay.scale = 1.0;
        entityDisplay.screenX = 100;
        entityDisplay.screenY = 150;

        assertTrue(entityDisplay.checkClick(120, 160));
        assertTrue(((OptionsDisplayStub) entityDisplay.options).panelShown);
    }

    @Test
    public void testSetOptions_NullOptions_NoAction() {
        entityDisplay.setOptions(null);
        assertNull(entityDisplay.options);
    }

    @Test
    public void testSetOptions_ValidOptions_CreatesOptionsDisplay() {
        String[][] options = {{"isMenu"}, {"Option1", "Action1"}, {"Option2", "Action2"}};
        entityDisplay.setOptions(options);

        assertNotNull(entityDisplay.options);
        assertTrue(((OptionsDisplayStub) entityDisplay.options).panelShown);
    }

    @Test
    public void testFlipSprite_NonTextType_FlipsImage() {
        entityDisplay.flipSprite();
    }
}

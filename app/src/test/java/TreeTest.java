import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import soulcasters.server.game.EntityHandler;
import soulcasters.server.game.entity.Tree;
import soulcasters.server.game.entity.units.Lumberjack;

public class TreeTest {

    private static Tree tree;
    private static Lumberjack lumberjack;
    private static EntityHandler entityHandler;

    @BeforeAll
    public static void setUp() {
        entityHandler = new EntityHandler(null);
        
        tree = new Tree(entityHandler, 100, 200);
        lumberjack = new Lumberjack(entityHandler, 100, 200, 0);
    }

    @Test
    public void testSetAndGetChopper() {
        assertNull(tree.getChopper(), "Initially, chopper should be null");

        tree.setChopper(lumberjack);
        assertNotNull(tree.getChopper(), "After setting, chopper should not be null");
        assertSame(lumberjack, tree.getChopper(), "The getter should return the instance that was set");
    }
}

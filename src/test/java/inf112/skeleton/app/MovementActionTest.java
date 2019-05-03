package inf112.skeleton.app;

import inf112.skeleton.app.roborally.screens.graphics.MovementAction;
import org.junit.jupiter.api.Test;

public class MovementActionTest {

    @Test
    void timeTest(){
        MovementAction normal = MovementAction.NORMAL;
        MovementAction fast = MovementAction.FAST;
        assert(normal.getActionTime() >= fast.getActionTime());
    }
}

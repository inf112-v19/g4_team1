package inf112.skeleton.app;

import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import inf112.skeleton.app.base.actors.IRobot;
import inf112.skeleton.app.base.actors.Player;
import inf112.skeleton.app.base.actors.Robot;
import inf112.skeleton.app.base.board.Board;
import inf112.skeleton.app.base.board.IBoard;
import inf112.skeleton.app.base.utils.Direction;
import inf112.skeleton.app.base.utils.Pos;
import inf112.skeleton.app.roborally.RoboRally;
import inf112.skeleton.app.roborally.screens.RoboRallyGame;
import inf112.skeleton.app.roborally.screens.graphics.MovementAction;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovementActionTest {

    @Test
    void timeTest(){

        MovementAction normal = MovementAction.NORMAL;
        MovementAction fast = MovementAction.FAST;
        assert(normal.getActionTime() >= fast.getActionTime());
    }
}

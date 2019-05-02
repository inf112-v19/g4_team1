package inf112.skeleton.app.boardelementtests;

import inf112.skeleton.app.base.actors.Player;
import inf112.skeleton.app.base.actors.Robot;
import inf112.skeleton.app.base.board.Board;
import inf112.skeleton.app.base.board.boardelement.DoubleSpeedConveyor;
import inf112.skeleton.app.base.utils.Direction;
import inf112.skeleton.app.base.utils.Pos;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DoubleSpeedConveyorTest {

    @Test
    void robotMove() {
        Board board = new Board(10, 10);
        Pos pos = new Pos(1, 1);
        Player player1 = new Player("tobias");
        Robot robot = new Robot(pos, Direction.EAST, player1, board);
        player1.addRobot(robot);
        board.addTileObject(robot);
        DoubleSpeedConveyor belt = new DoubleSpeedConveyor(Direction.EAST, pos, board);
        board.addTileObject(belt);
        belt.activate();
        assertEquals(robot.getPos().x(), 2);
    }
}

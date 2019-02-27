package inf112.skeleton.app.boardelementtests;

import inf112.skeleton.app.base.actors.*;
import inf112.skeleton.app.base.board.Board;
import inf112.skeleton.app.base.board.boardElement.Conveyor;
import inf112.skeleton.app.base.utils.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class ConveyorTest {

    @Test
    void robotMove() {
        Board board = new Board(10, 10);
        Pos pos = new Pos(1, 1);
        Robot robot = new Robot(pos, Direction.EAST, new Player("tobias"), board);
        board.addTileObject(robot);
        Conveyor belt = new Conveyor(Direction.EAST, pos,  'a', board);
        board.addTileObject(belt);
        belt.activate();
        assertEquals(robot.getPos().x(), 2);
    }
}
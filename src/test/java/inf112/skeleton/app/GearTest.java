package inf112.skeleton.app;

import inf112.skeleton.app.actors.*;
import inf112.skeleton.app.board.*;
import inf112.skeleton.app.board.boardElement.Gear;
import inf112.skeleton.app.board.boardElement.Wall;
import inf112.skeleton.app.utils.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class GearTest {

    @Test
    void robotMove() {
        Board board = new Board(10, 10);
        Robot robot = new Robot(1, 1, Direction.EAST, new Player("tobias"), board);
        board.addTileObject(1, 1, robot);
        Gear gear = new Gear(Direction.EAST, 1, 1, 'a', board);
        board.addTileObject(1, 1, gear);
        gear.activate();
        assertEquals(robot.getDir(), Direction.SOUTH);
    }
}
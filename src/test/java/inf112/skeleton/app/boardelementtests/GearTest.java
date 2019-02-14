package inf112.skeleton.app.boardelementtests;

import inf112.skeleton.app.actors.*;
import inf112.skeleton.app.board.*;
import inf112.skeleton.app.board.boardElement.Gear;
import inf112.skeleton.app.board.boardElement.Wall;
import inf112.skeleton.app.utils.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class GearTest {

    @Test
    void gearTurn() {
        Board board = new Board(10, 10);
        Robot robot = new Robot(1, 1, Direction.EAST, new Player("tobias"), board);
        board.addTileObject(robot);
        Gear gear = new Gear(Direction.EAST, 1, 1, 'a', board);
        board.addTileObject(gear);
        gear.activate();
        assertEquals(robot.getDir(), Direction.SOUTH);
    }
}
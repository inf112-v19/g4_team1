package inf112.skeleton.app.boardelementtests;

import inf112.skeleton.app.base.actors.*;
import inf112.skeleton.app.base.board.Board;
import inf112.skeleton.app.base.board.boardElement.Gear;
import inf112.skeleton.app.base.utils.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class GearTest {

    @Test
    void gearTurn() {
        Board board = new Board(10, 10);
        Pos pos = new Pos(0, 0);
        Robot robot = new Robot(pos, Direction.EAST, new Player("tobias"), board);
        board.addTileObject(robot);
        Gear gear = new Gear(Direction.EAST,  new Pos(0, 0), 'a', board);
        board.addTileObject(gear);

        gear.activate();
        assertEquals(robot.getDir(), Direction.SOUTH);
    }
}
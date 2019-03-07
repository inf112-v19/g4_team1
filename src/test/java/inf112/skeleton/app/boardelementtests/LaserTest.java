package inf112.skeleton.app.boardelementtests;

import inf112.skeleton.app.base.actors.*;
import inf112.skeleton.app.base.board.Board;
import inf112.skeleton.app.base.board.boardElement.Gear;
import inf112.skeleton.app.base.board.boardElement.Laser;
import inf112.skeleton.app.base.utils.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class LaserTest {

    @Test
    void laserFire() {
        Board board = new Board(10, 10);
        Robot robot = new Robot(new Pos(0, 0), Direction.EAST, new Player("tobias"), board);
        board.addTileObject(robot);
        Laser laser = new Laser(Direction.SOUTH,  new Pos(0, 1), 'a', board);
        board.addTileObject(laser);
        int hp = robot.getHealth();
        laser.activate();
        assertEquals(robot.getHealth(), hp-1);
    }
}
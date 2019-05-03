package inf112.skeleton.app.boardelementtests;

import inf112.skeleton.app.base.actors.*;
import inf112.skeleton.app.base.board.Board;
import inf112.skeleton.app.base.board.boardelement.Laser;
import inf112.skeleton.app.base.board.boardelement.Wall;
import inf112.skeleton.app.base.utils.*;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class LaserTest {
    Board board;

    @BeforeEach
    void before(){
        board = new Board(10, 10);
    }
    @Test
    void laserFire() {
        Robot robot = new Robot(new Pos(0, 0), Direction.EAST, new Player("tobias"), board);
        board.addTileObject(robot);
        Laser laser = new Laser(Direction.SOUTH,  new Pos(0, 1),  board);
        board.addTileObject(laser);
        int hp = robot.getHealth();
        laser.activate();
        assertEquals(hp-1, robot.getHealth());
    }
    @Test
    void laserWall(){
        Robot robot = new Robot(new Pos(2, 0), Direction.EAST, new Player("tobias"), board);
        board.addTileObject(robot);
        Laser laser = new Laser(Direction.EAST,  new Pos(0, 0),  board);
        board.addTileObject(laser);
        Wall wall = new Wall(Direction.EAST, new Pos(1, 0), board);
        board.addTileObject(wall);
        int hp = robot.getHealth();
        laser.activate();
        assertEquals(hp, robot.getHealth());
    }
    @Test
    void laserWall2(){
        Robot robot = new Robot(new Pos(2, 0), Direction.EAST, new Player("tobias"), board);
        board.addTileObject(robot);
        Laser laser = new Laser(Direction.EAST,  new Pos(0, 0),  board);
        board.addTileObject(laser);
        Wall wall = new Wall(Direction.WEST, new Pos(1, 0), board);
        board.addTileObject(wall);

        int hp = robot.getHealth();
        laser.activate();
        assertEquals(hp, robot.getHealth());
    }
}
package inf112.skeleton.app.boardelementtests;

import inf112.skeleton.app.base.actors.*;
import inf112.skeleton.app.base.board.Board;
import inf112.skeleton.app.base.board.boardElement.Wall;
import inf112.skeleton.app.roborally.utils.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class RobotTest {

    @Test
    void robotMove() {
        Board board = new Board(10, 10);
        Robot robot = new Robot(1, 1, Direction.EAST, new Player("tobias"), board);
        board.addTileObject(robot);

        robot.moveForward(1);
        assertEquals(robot.getX(), 2);
        assertEquals(robot.getY(), 1);

        robot.turnLeft();
        assertEquals(robot.getY(), 1);
        assertEquals(robot.getX(), 2);

        robot.moveForward(2);
        assertEquals(robot.getY(), 3);
        assertEquals(robot.getX(), 2);
    }

    @Test
    void pushRobot() {
        Board board = new Board(10, 10);
        Robot robot1 = new Robot(1, 1, Direction.EAST, new Player("tobias"), board);
        Robot robot2 = new Robot(2, 1, Direction.EAST, new Player("tobias"), board);

        board.addTileObject(robot1);
        board.addTileObject(robot2);
        robot1.moveForward(1);
        assertEquals(robot1.getX(), 2);
        assertEquals(robot2.getX(), 3);

        robot2.moveBackwards();
        assertEquals(robot1.getX(), 1);
        assertEquals(robot2.getX(), 2);
    }
    @Test
    void wallCollision1(){
        Board board = new Board(10, 10);
        Robot robot1 = new Robot(1, 1, Direction.EAST, new Player("tobias"), board);
        board.addTileObject(new Wall(Direction.WEST, 2,1, 'w', board));
        robot1.moveForward(1);
        assertEquals(robot1.getX(), 1);
        assertEquals(robot1.getY(), 1);

    }
    @Test
    void wallCollision2(){
        Board board = new Board(10, 10);
        Robot robot1 = new Robot(1, 1, Direction.EAST, new Player("tobias"), board);
        board.addTileObject(new Wall(Direction.EAST, 2,1, 'w', board));
        robot1.moveForward(1);
        assertEquals(robot1.getX(), 2);
        assertEquals(robot1.getY(), 1);
    }
    @Test
    void wallCollision3(){
        Board board = new Board(10, 10);
        Robot robot1 = new Robot(1, 1, Direction.EAST, new Player("tobias"), board);
        board.addTileObject(new Wall(Direction.EAST, 1,1, 'w', board));
        robot1.moveForward(1);
        assertEquals(robot1.getX(), 1);
        assertEquals(robot1.getY(), 1);
    }
    @Test
    void wallCollision4(){
        Board board = new Board(10, 10);
        Robot robot1 = new Robot(1, 1, Direction.EAST, new Player("tobias"), board);
        board.addTileObject(new Wall(Direction.WEST, 1,1, 'w', board));
        robot1.moveForward(1);
        assertEquals(robot1.getX(), 2);
        assertEquals(robot1.getY(), 1);
    }@Test
    void pushIntoWall(){
        Board board = new Board(10, 10);
        Robot robot1 = new Robot(1, 1, Direction.EAST, new Player("tobias"), board);
        Robot robot2 = new Robot(2, 1, Direction.EAST, new Player("tobias"), board);
        board.addTileObject( new Wall(Direction.WEST, 3,1, 'w', board));
        board.addTileObject( robot1);
        board.addTileObject(robot2);
        robot1.moveForward(1);
        assertEquals(robot1.getX(), 1);
        assertEquals(robot2.getX(), 2);
    }

}

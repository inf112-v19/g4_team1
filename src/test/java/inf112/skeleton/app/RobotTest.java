package inf112.skeleton.app;

import inf112.skeleton.app.actors.*;
import inf112.skeleton.app.board.*;
import inf112.skeleton.app.board.boardElement.Wall;
import inf112.skeleton.app.utils.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class RobotTest {

    @Test
    void robotMove() {
        Board board = new Board(10, 10);
        Robot robot = new Robot(1, 1, Direction.EAST, new Player("tobias"), board);
        board.addTileObject(1, 1, robot);

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

        board.addTileObject(1, 1, robot1);
        board.addTileObject(2, 1, robot2);
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
        board.addTileObject(2, 1, new Wall(Direction.WEST, 2,1, 'w', board));
        robot1.moveForward(1);
        assertEquals(robot1.getX(), 1);
        assertEquals(robot1.getY(), 1);

    }
    @Test
    void wallCollision2(){
        Board board = new Board(10, 10);
        Robot robot1 = new Robot(1, 1, Direction.EAST, new Player("tobias"), board);
        board.addTileObject(2, 1, new Wall(Direction.EAST, 2,1, 'w', board));
        robot1.moveForward(1);
        assertEquals(robot1.getX(), 2);
        assertEquals(robot1.getY(), 1);

    }
}

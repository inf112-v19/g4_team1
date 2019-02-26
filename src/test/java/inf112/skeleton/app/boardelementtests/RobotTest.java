package inf112.skeleton.app.boardelementtests;

import inf112.skeleton.app.base.actors.*;
import inf112.skeleton.app.base.board.Board;
import inf112.skeleton.app.base.board.boardElement.Wall;
import inf112.skeleton.app.base.utils.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class RobotTest {

    @Test
    void robotMove() {
        Board board = new Board(10, 10);
        Pos pos = new Pos(1, 1);
        Robot robot = new Robot(pos, Direction.EAST, new Player("tobias"), board);
        board.addTileObject(robot);

        robot.moveForward(1);
        assertEquals(robot.getPos(), new Pos(2, 1));

        robot.turnLeft();
        assertEquals(robot.getPos(), new Pos(2, 1));

        robot.moveForward(2);
        assertEquals(robot.getPos(), new Pos(3, 2));
    }

    @Test
    void pushRobot() {
        Board board = new Board(10, 10);
        Robot robot1 = new Robot(new Pos(1, 1), Direction.EAST, new Player("tobias"), board);
        Robot robot2 = new Robot(new Pos(2, 1), Direction.EAST, new Player("tobias"), board);

        board.addTileObject(robot1);
        board.addTileObject(robot2);
        robot1.moveForward(1);
        assertEquals(robot1.getPos().x(), 2);
        assertEquals(robot2.getPos().x(), 3);

        robot2.moveBackwards();
        assertEquals(robot1.getPos().x(), 1);
        assertEquals(robot2.getPos().x(), 2);
    }
    @Test
    void wallCollision1(){
        Board board = new Board(10, 10);
        Robot robot1 = new Robot(new Pos(1, 1), Direction.EAST, new Player("tobias"), board);
        board.addTileObject(new Wall(Direction.WEST, new Pos(2, 1), 'w', board));
        robot1.moveForward(1);
        assertEquals(robot1.getPos().x(), 1);
        assertEquals(robot1.getPos().x(), 1);

    }
    @Test
    void wallCollision2(){
        Board board = new Board(10, 10);
        Robot robot1 = new Robot(new Pos(1, 1), Direction.EAST, new Player("tobias"), board);
        board.addTileObject(new Wall(Direction.EAST, new Pos(2, 1), 'w', board));
        robot1.moveForward(1);
        assertEquals(robot1.getPos().x(), 2);
        assertEquals(robot1.getPos().y(), 1);
    }
    @Test
    void wallCollision3(){
        Board board = new Board(10, 10);
        Robot robot1 = new Robot(new Pos(1, 1), Direction.EAST, new Player("tobias"), board);
        board.addTileObject(new Wall(Direction.EAST, new Pos(1, 1), 'w', board));
        robot1.moveForward(1);
        assertEquals(robot1.getPos().x(), 1);
        assertEquals(robot1.getPos().y(), 1);
    }
    @Test
    void wallCollision4(){
        Board board = new Board(10, 10);
        Robot robot1 = new Robot(new Pos(1, 1), Direction.EAST, new Player("tobias"), board);
        board.addTileObject(new Wall(Direction.WEST, new Pos(1, 1), 'w', board));
        robot1.moveForward(1);
        assertEquals(robot1.getPos().x(), 2);
        assertEquals(robot1.getPos().y(), 1);
    }@Test
    void pushIntoWall(){
        Board board = new Board(10, 10);
        Robot robot1 = new Robot(new Pos(1, 1), Direction.EAST, new Player("tobias"), board);
        Robot robot2 = new Robot(new Pos(2, 1), Direction.EAST, new Player("tobias"), board);
        board.addTileObject( new Wall(Direction.WEST, new Pos(3, 1), 'w', board));
        board.addTileObject( robot1);
        board.addTileObject(robot2);
        robot1.moveForward(1);
        assertEquals(robot1.getPos().x(), 1);
        assertEquals(robot2.getPos().x(), 2);
    }
}

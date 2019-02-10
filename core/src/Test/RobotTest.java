import com.mygdx.frick.utils.Direction;
import com.mygdx.frick.actors.Player;
import com.mygdx.frick.actors.Robot;
import com.mygdx.frick.board.Board;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class RobotTest {
    @Test
    void robotMove(){
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
    void pushRobot(){
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


}

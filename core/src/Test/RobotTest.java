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
}

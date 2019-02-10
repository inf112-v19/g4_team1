import com.mygdx.frick.actors.Player;
import com.mygdx.frick.actors.Robot;
import com.mygdx.frick.actors.TileObject;
import com.mygdx.frick.board.Board;
import com.mygdx.frick.board.Tile;

import com.mygdx.frick.utils.Direction;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void putGetTest() {
        Board board = new Board(10, 10);
        Tile test = new Tile();
        board.setTile(0, 0, test);
        assertEquals(test, board.get(0, 0));
    }

    @Test
    void boardSizeTest(){
        Board board = new Board(2, 2);
        assertEquals(board.getSize(), 4);
        assertEquals(board.getHeight(), 2);
        assertEquals(board.getWidth(), 2);
    }
    @Test
    void boardObjectTest(){

        Board board = new Board(5, 5);
        Tile tile = new Tile();
        board.setTile(0, 0, tile);
        Robot robot = new Robot(0, 0, Direction.EAST, new Player("test"), board);
        board.get(0, 0).addObject(robot);
        assertEquals(board.getRobot(0, 0), robot);
    }
    @Test
    void validPosTest(){
        Board board = new Board(10, 10);
        assertEquals(board.isValidPos(0, 0), true);
        assertEquals(board.isValidPos(9, 9), true);
        assertEquals(board.isValidPos(1, 10), false);
        assertEquals(board.isValidPos(10, 0), false);
    }

}

package inf112.skeleton.app;

import inf112.skeleton.app.base.actors.*;
import inf112.skeleton.app.base.board.Board;
import inf112.skeleton.app.base.board.Tile;
import inf112.skeleton.app.base.utils.*;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    @Test
    void putGetTest() {
        Board board = new Board(10, 10);
        Tile test = new Tile();
        Pos pos =new Pos(0, 0);
        board.setTile(pos,  test);
        assertEquals(test, board.get(pos));
    }

    @Test
    void boardSizeTest() {
        Board board = new Board(2, 2);

        assertEquals(board.getSize(), 4);
        assertEquals(board.getHeight(), 2);
        assertEquals(board.getWidth(), 2);
    }

    @Test
    void boardObjectTest() {
        Board board = new Board(5, 5);
        Tile tile = new Tile();
        Pos pos =new Pos(0, 0);

        board.setTile(pos, tile);
        Player player1 = new Player("tobias");
        Robot robot = new Robot(pos, Direction.EAST, player1, board);
        player1.addRobot(robot);

        board.get(pos).addObject(robot);
        assertEquals(board.getRobot(pos), robot);
    }

    @Test
    void validPosTest() {
        Board board = new Board(10, 10);

        assertFalse(board.outOfBounds(new Pos(0, 0)));
        assertFalse(board.outOfBounds(new Pos(9, 9)));
        assertTrue(board.outOfBounds(new Pos(0, -1)));
        assertTrue(board.outOfBounds(new Pos(10, 5)));
    }
    @Test
    void readFromFile(){
        try {
            Board board = new Board("assets/roborally/board1.txt");
            assert(board.containsPit(new Pos(0, 0)));
            assertEquals(board.getWidth(), 10);
            assertEquals(board.getHeight(), 10);
            assert(board.outOfBounds(new Pos(10, 10)));
        } catch (IOException e) {
            fail();
        }
    }
}

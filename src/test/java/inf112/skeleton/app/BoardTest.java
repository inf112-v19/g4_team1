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
        Robot robot = new Robot(pos, Direction.EAST, new Player("test"), board);

        board.get(pos).addObject(robot);
        assertEquals(board.getRobot(pos), robot);
    }

    @Test
    void validPosTest() {
        Board board = new Board(10, 10);

        assertTrue(!board.outOfBounds(0, 0));
        assertTrue(!board.outOfBounds(9, 9));
        assertFalse(!board.outOfBounds(1, 10));
        assertFalse(!board.outOfBounds(10, 0));
    }
    @Test
    void readFromFile(){
        try {
            Board board = new Board("assets/board1.txt");
            assert(board.containsPit(0, 0));
            assertEquals(board.getWidth(), 10);
            assertEquals(board.getHeight(), 10);
            assert(board.outOfBounds(10, 10));
        } catch (IOException e) {
            fail();
        }
    }
}

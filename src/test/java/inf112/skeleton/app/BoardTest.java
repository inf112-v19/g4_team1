package inf112.skeleton.app;

import inf112.skeleton.app.base.actors.*;
import inf112.skeleton.app.base.board.Board;
import inf112.skeleton.app.base.board.Tile;
import inf112.skeleton.app.roborally.utils.*;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    @Test
    void putGetTest() {
        Board board = new Board(10, 10);
        Tile test = new Tile();

        board.setTile(0, 0, test);
        assertEquals(test, board.get(0, 0));
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
        board.setTile(0, 0, tile);
        Robot robot = new Robot(0, 0, Direction.EAST, new Player("test"), board);

        board.get(0, 0).addObject(robot);
        assertEquals(board.getRobot(0, 0), robot);
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
            Board board = new Board("assets/old/board1.txt", 96, 96);
            assert(board.containsPit(0, 0));
            assertEquals(board.getWidth(), 10);
            assertEquals(board.getHeight(), 10);
            assert(board.outOfBounds(10, 10));
        } catch (IOException e) {
            fail();
        }
    }
}

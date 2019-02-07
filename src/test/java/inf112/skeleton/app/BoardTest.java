package inf112.skeleton.app;

import inf112.skeleton.app.board.Board;
import inf112.skeleton.app.board.Tile;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void putGetTest() {
        Board board = new Board(10, 10);
        Tile test = new Tile(0, 0);

        board.setTile(0, 0, test);

        assertEquals(test, board.get(0, 0));
    }

}

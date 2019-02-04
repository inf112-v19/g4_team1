package Test;

import com.mygdx.frick.Board;
import com.mygdx.frick.Tile;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void put() {
        Board board = new Board(10, 10);
        Tile test = new Tile();

        board.put(test, 0, 0);

        assertEquals(test, board.getBoard()[0][0]);



    }
}
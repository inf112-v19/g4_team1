package Test;

import com.mygdx.frick.Board;
import com.mygdx.frick.Tile;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    void put() {
        Board board = new Board(10, 10);
        Tile test = new Tile();

        board.put(something, 0, 0);

        assertEquals(test, board[0][0]);



    }
}
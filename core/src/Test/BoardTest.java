
import com.mygdx.frick.Board;
import com.mygdx.frick.Tile;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void put() {
        Board board = new Board(10, 10);
        Tile test = new Tile();

        board.set(0, 0, test);

        assertEquals(test, board.get(0, 0));
    }
}
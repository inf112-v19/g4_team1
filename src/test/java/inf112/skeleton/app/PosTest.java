package inf112.skeleton.app;

import inf112.skeleton.app.base.actors.*;
import inf112.skeleton.app.base.board.Board;
import inf112.skeleton.app.base.cards.Card;
import inf112.skeleton.app.base.cards.CardType;
import inf112.skeleton.app.base.utils.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PosTest {

    @Test
    void upAndDown() {
        Pos pos1 = new Pos(1, 1);
        Pos pos2 = pos1.getAdjacent(Direction.NORTH).getAdjacent(Direction.SOUTH);
        assertEquals(pos1, pos2);
    }
    @Test
    void adjacent(){
        Pos pos = new Pos(1, 1);
        assertEquals(new Pos (1, 2), pos.getAdjacent(Direction.NORTH));
        assertEquals(new Pos (2, 1), pos.getAdjacent(Direction.EAST));
        assertEquals(new Pos (0, 1), pos.getAdjacent(Direction.WEST));
        assertEquals(new Pos (1, 0), pos.getAdjacent(Direction.SOUTH));
    }
}

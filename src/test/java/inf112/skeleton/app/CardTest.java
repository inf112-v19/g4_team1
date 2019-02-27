package inf112.skeleton.app;

import inf112.skeleton.app.base.actors.*;
import inf112.skeleton.app.base.board.Board;
import inf112.skeleton.app.base.cards.Card;
import inf112.skeleton.app.base.cards.CardType;
import inf112.skeleton.app.base.utils.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CardTest {

    @Test
    void executeTest() {
        Board board = new Board(10, 10);
        Robot robot = new Robot(new Pos(1, 1), Direction.NORTH, new Player("tobias"), board);
        board.addTileObject(robot);

        Card card1 = new Card(CardType.MOVE_1_TILE, 100);
        card1.execute(robot);
        assertEquals(robot.getPos().x(), 1);
        assertEquals(robot.getPos().y(), 2);

        Card card2 = new Card(CardType.TURN_RIGHT, 100);
        card2.execute(robot);
        assertEquals(robot.getPos().x(), 1);
        assertEquals(robot.getPos().y(), 2);

        Card card3 = new Card(CardType.MOVE_2_TILE, 100);
        card3.execute(robot);
        assertEquals(robot.getPos().x(), 3);
        assertEquals(robot.getPos().y(), 2);

        Card card4 = new Card(CardType.MOVE_BACK, 100);
        card4.execute(robot);
        assertEquals(robot.getPos().x(), 2);
        assertEquals(robot.getPos().y(), 2);
    }
}

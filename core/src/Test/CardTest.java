import com.mygdx.frick.actors.Direction;
import com.mygdx.frick.actors.Robot;
import com.mygdx.frick.board.Board;
import com.mygdx.frick.board.Tile;

import com.mygdx.frick.cards.Card;
import com.mygdx.frick.cards.CardType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class CardTest {

    @Test
    void executeTest() {
        Board board = new Board(10, 10);
        Robot robot = new Robot(1, 1, Direction.NORTH, "tobias", board);
        board.addTileObject(1, 1, robot);

        Card card1 = new Card(CardType.MOVE_1_TILE, 100);
        card1.execute(robot);
        assertEquals(robot.getX(), 1);
        assertEquals(robot.getY(), 2);

        Card card2 = new Card(CardType.TURN_RIGHT, 100);
        card2.execute(robot);
        assertEquals(robot.getX(), 1);
        assertEquals(robot.getY(), 2);

        Card card3 = new Card(CardType.MOVE_2_TILE, 100);
        card2.execute(robot);
        assertEquals(robot.getX(), 3);
        assertEquals(robot.getY(), 2);
    }

}

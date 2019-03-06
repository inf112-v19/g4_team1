package inf112.skeleton.app.boardelementtests;

import inf112.skeleton.app.base.actors.Player;
import inf112.skeleton.app.base.actors.Robot;
import inf112.skeleton.app.base.board.Board;
import inf112.skeleton.app.base.board.Tile;
import inf112.skeleton.app.base.board.boardElement.WrenchTile;
import inf112.skeleton.app.base.utils.Direction;
import inf112.skeleton.app.base.utils.Pos;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WrenchTileTest {

    @Test
    void WrenchTileTurn() {
        Board board = new Board(10, 10);
        Pos pos = new Pos(0,0);
        Tile tile = new Tile();
        WrenchTile wrench = new WrenchTile(pos,'w',board);

        board.addTileObject(wrench);
        Robot robot = new Robot(pos, Direction.EAST,new Player("player"),board);
        board.addTileObject(robot);

        assertEquals(robot.getPos(), wrench.getPos());
        wrench.activate();






    }
}


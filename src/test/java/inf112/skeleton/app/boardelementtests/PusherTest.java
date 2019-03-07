package inf112.skeleton.app.boardelementtests;
import inf112.skeleton.app.base.actors.*;
import inf112.skeleton.app.base.board.Board;
import inf112.skeleton.app.base.board.Tile;
import inf112.skeleton.app.base.board.boardelement.Pusher;
import inf112.skeleton.app.base.utils.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PusherTest {

    @Test
    void pushTurn() {
        Board board = new Board(10, 10);
        Pos pos = new Pos(0,0);


        Tile tile = new Tile();
        Pusher pusher = new Pusher(Direction.NORTH,pos,'a', board);
        board.addTileObject(pusher);


        Robot robot = new Robot(pos,Direction.EAST,new Player("player"),board);
        board.addTileObject(robot);

        assertEquals(robot.getPos(), pusher.getPos());
        Pos newPos = new Pos(0,1);

        pusher.activate();

        //should be positon after pushing EAST.
        assertEquals(robot.getPos(),newPos);

    }

}

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

        Pusher pusher = new Pusher(Direction.NORTH,pos, board);
        board.addTileObject(pusher);


        Robot robot = new Robot(pos,Direction.EAST,new Player("player"),board);
        board.addTileObject(robot);

        assertEquals(robot.getPos(), pusher.getPos());
        Pos newPos = new Pos(0,1);

        pusher.activate();

        //should be positon after pushing EAST.
        assertEquals(robot.getPos(),newPos);

    }
    @Test
    void wallTest(){
        Board board = new Board(10, 10);
        Pos pos = new Pos(1,1);

        Pusher pusher = new Pusher(Direction.NORTH,pos, board);
        board.addTileObject(pusher);


        Robot robot = new Robot(new Pos(1, 0),Direction.NORTH,new Player("player"),board);
        board.addTileObject(robot);
        robot.moveForward(1);
        pusher.activate();

        //should be positon after being blocked.
        assertEquals(robot.getPos(), new Pos(1, 0));
    }

}

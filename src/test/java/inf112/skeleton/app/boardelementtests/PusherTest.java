package inf112.skeleton.app.boardelementtests;
import inf112.skeleton.app.base.actors.*;
import inf112.skeleton.app.base.board.Board;
import inf112.skeleton.app.base.board.Tile;
import inf112.skeleton.app.base.board.boardElement.Pusher;
import inf112.skeleton.app.base.utils.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PusherTest {

    @Test
    void pushTurn() {
        Board board = new Board(10, 10);
        Pos pos = new Pos(0,0);
        Tile tile = new Tile();
        Pusher pusher = new Pusher(Direction.NORTH,new Pos(0,0),'a', board);
        Robot robot = new Robot(pos,Direction.EAST,new Player("player"),board);
        if(tile.contains(robot)){
            pusher.activate();
        }
        //if(tile.contains(robot)){fail();}

        Pos pos1 = new Pos(0,1);
        if(robot.getPos().equals(pos)){fail();}
        if(!robot.getPos().equals(pos1)){fail();}
    }

}

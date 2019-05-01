package inf112.skeleton.app.boardelementtests;

import inf112.skeleton.app.base.actors.*;
import inf112.skeleton.app.base.board.Board;
import inf112.skeleton.app.base.board.boardelement.Flag;
import inf112.skeleton.app.base.utils.*;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;



public class FlagTest {

    @Test
    void flagRespawn() {
        Board board = new Board(10, 10);
        Robot robot = new Robot(new Pos(0, 0), Direction.EAST,  new Player("tobias"), board);
        board.addTileObject(robot);
        Flag flag= new Flag(new Pos(1, 0),  board, 1);
        board.addTileObject(flag);
        robot.moveForward(1);
        flag.setRespawn();
        for (int i = 0; i < 12; i++) {
            robot.damage();
        }
        assertEquals(robot.getPos(), flag.getPos());
        assertEquals(robot.getFlags().size(), 1);
    }
}
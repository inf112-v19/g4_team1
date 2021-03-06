package inf112.skeleton.app.boardelementtests;

import inf112.skeleton.app.base.actors.Player;
import inf112.skeleton.app.base.actors.Robot;
import inf112.skeleton.app.base.board.Board;
import inf112.skeleton.app.base.board.boardelement.Flag;
import inf112.skeleton.app.base.utils.Direction;
import inf112.skeleton.app.base.utils.Pos;
import inf112.skeleton.app.roborally.screens.graphics.MovementAction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WinTest {
    @Test
    void WinTest(){

        Board board = new Board(10, 10);
        Pos pos = new Pos(1, 1);
        Player player1 = new Player("tobias");
        Robot robot = new Robot(pos, Direction.EAST, player1, board);

        player1.addRobot(robot);
        board.addTileObject(robot);

        Pos flag1Pos = new Pos(2,1);
        Pos flag2Pos = new Pos(3,1);
        Pos flag3Pos = new Pos(4,1);

        Flag flag1 = new Flag(flag1Pos, board, 1);
        Flag flag2 = new Flag(flag2Pos, board, 2);
        Flag flag3 = new Flag(flag3Pos, board, 3);

        board.addTileObject(flag1);
        board.addTileObject(flag2);
        board.addTileObject(flag3);


        assertEquals(board.getFlags().size(), 3);

        robot.tryToMove(Direction.EAST, MovementAction.FAST);
        flag1.setRespawn();
        robot.tryToMove(Direction.EAST, MovementAction.FAST);
        flag2.setRespawn();
        robot.tryToMove(Direction.EAST, MovementAction.FAST);
        flag3.setRespawn();


        assertEquals(3, robot.getFlags().size());





    }
}

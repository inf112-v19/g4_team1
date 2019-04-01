package inf112.skeleton.app.boardelementtests;

import inf112.skeleton.app.base.actors.IRobot;
import inf112.skeleton.app.base.actors.Player;
import inf112.skeleton.app.base.actors.Robot;
import inf112.skeleton.app.base.board.Board;
import inf112.skeleton.app.base.board.Tile;
import inf112.skeleton.app.base.board.boardelement.TurnConveyor;
import inf112.skeleton.app.base.utils.Direction;
import inf112.skeleton.app.base.utils.Pos;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TurnConveyorTest {


    @Test
    void robotMove() {
        Board board = new Board(10, 10);
        Pos pos = new Pos(1, 0);

        TurnConveyor turnconveyor = new TurnConveyor(Direction.NORTH, pos, board);
        board.addTileObject(turnconveyor);

        Robot robot = new Robot(pos, Direction.EAST, new Player("player"), board);
        board.addTileObject(robot);


        assertEquals(robot.getPos(), turnconveyor.getPos());
        Pos newPos = new Pos(1, 1);

        turnconveyor.activate();

        assertEquals(robot.getPos(),newPos);

    }


}




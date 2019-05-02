package inf112.skeleton.app.base.board.boardelement;

import inf112.skeleton.app.base.actors.IRobot;
import inf112.skeleton.app.base.board.Board;
import inf112.skeleton.app.base.utils.Direction;
import inf112.skeleton.app.base.utils.Pos;
import inf112.skeleton.app.roborally.screens.graphics.MovementAction;

public class Conveyor extends BoardElement implements IActiveElement {
    private Direction dir;

    public Conveyor(Direction dir, Pos pos, Board board) {
        super(pos, board);
        this.dir = dir;
    }

    @Override
    public IRobot activate() {
        if (board.containsRobot(pos)&& board.getRobot(pos).hasNotMoved()) {
            IRobot robot = board.getRobot(pos);
            robot.tryToMove(this.dir, MovementAction.FAST);
            robot.setMoved(true);
            return robot;

        }
        return null;
    }

}

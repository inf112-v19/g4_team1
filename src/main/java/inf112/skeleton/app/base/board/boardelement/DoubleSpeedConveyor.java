package inf112.skeleton.app.base.board.boardelement;

import inf112.skeleton.app.base.actors.IRobot;
import inf112.skeleton.app.base.board.Board;
import inf112.skeleton.app.base.board.IBoard;
import inf112.skeleton.app.base.utils.Direction;
import inf112.skeleton.app.base.utils.Pos;
import inf112.skeleton.app.roborally.screens.graphics.MovementAction;

public class DoubleSpeedConveyor extends BoardElement implements IActiveElement{
    private Direction dir;

    public DoubleSpeedConveyor(Direction dir, Pos pos,  IBoard board) {
        super(pos, board);
        this.dir = dir;
    }

    @Override
    public IRobot activate() {
        //double distance conveyors should be activated twice
        if (board.containsRobot(pos)&& board.getRobot(pos).hasNotMoved()) {
            IRobot robot = board.getRobot(pos);
            robot.setMoved(true);
            robot.move(this.dir, MovementAction.FAST);
            return robot;
        }
        return null;
    }
}

package inf112.skeleton.app.base.board.boardelement;

import inf112.skeleton.app.base.actors.IRobot;
import inf112.skeleton.app.base.board.IBoard;
import inf112.skeleton.app.base.utils.Direction;
import inf112.skeleton.app.base.utils.Pos;
import inf112.skeleton.app.roborally.screens.graphics.MovementAction;

public class DoubleSpeedTurnConveyor extends DoubleSpeedConveyor implements IActiveElement{
    private Direction dir;
    private final Direction turndir;

    public DoubleSpeedTurnConveyor(Direction dir,Direction turndir,  Pos pos, IBoard board) {
        super(dir, pos, board);
        if (turndir == Direction.NORTH || turndir == Direction.SOUTH){
            throw new IllegalArgumentException();
        }

        this.dir = dir;
        this.turndir = turndir;
    }

    @Override
    public IRobot activate() {
        if (board.containsRobot(pos)&& board.getRobot(pos).hasNotMoved()) {
            IRobot robot = board.getRobot(pos);
            robot.setMoved(true);
            //fix so the direction and position or robot is updated at the same time, in same animation. do not use turnright()
            if(turndir == Direction.EAST) {
                robot.setDir(robot.getDir().right());
            }else{
                robot.setDir(robot.getDir().left());
            }
            robot.move(dir, MovementAction.FAST);
            return robot;
        }
        return null;
    }
}

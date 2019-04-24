package inf112.skeleton.app.base.board.boardelement;

import inf112.skeleton.app.base.actors.IRobot;
import inf112.skeleton.app.base.board.IBoard;
import inf112.skeleton.app.base.utils.Direction;
import inf112.skeleton.app.base.utils.Pos;
import inf112.skeleton.app.roborally.screens.graphics.MovementAction;

public class TurnConveyor extends BoardElement implements IActiveElement{
    private Direction dir;
    private final Direction turndir;

    public TurnConveyor(Direction dir,Direction turndir,  Pos pos, IBoard board) {
        super(pos, board);
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
            robot.move(dir, MovementAction.FAST);
            robot.setMoved(true);
            if(turndir == Direction.EAST) {
                robot.turnRight();
            }else{
                robot.turnLeft();
            }
            return robot;
        }
        return null;
    }
}

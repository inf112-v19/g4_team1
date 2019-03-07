package inf112.skeleton.app.base.board.boardelement;

import inf112.skeleton.app.base.actors.IRobot;
import inf112.skeleton.app.base.board.Board;
import inf112.skeleton.app.base.utils.Direction;
import inf112.skeleton.app.base.utils.Pos;

public class Conveyor extends BoardElement implements IActiveElement {
    private Direction dir;

    public Conveyor(Direction dir, Pos pos, char symbol, Board board) {
        super(pos, symbol, board);
        this.dir = dir;
    }

    @Override
    public void activate() {
        //double distance conveyors should be activated twice
        //TODO:
        //curved conveyors
        if (board.containsRobot(pos)) {
            IRobot robot = board.getRobot(pos);
            robot.move(this.dir);
        }
    }

}

package inf112.skeleton.app.base.board.boardelement;

import inf112.skeleton.app.base.actors.Robot;
import inf112.skeleton.app.base.board.Board;
import inf112.skeleton.app.base.utils.Direction;
import inf112.skeleton.app.base.utils.Pos;

public class Pusher extends Wall implements  IActiveElement {
    private Direction pushDir;
    private Board board;

    public Pusher(Direction dir, Pos pos, Board board) {
        super(dir.opposite(), pos, board);
        this.pushDir = dir;
        this.board = board;
    }

    @Override
    public void activate() {
        if (board.containsRobot(pos) && board.getRobot(pos).hasNotMoved()) {
            Robot robot = (Robot) board.getRobot(pos);
            robot.setMoved(true);
            robot.move(pushDir);
        }
    }
}

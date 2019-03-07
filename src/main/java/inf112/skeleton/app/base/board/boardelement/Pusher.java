package inf112.skeleton.app.base.board.boardelement;

import inf112.skeleton.app.base.actors.Robot;
import inf112.skeleton.app.base.board.Board;
import inf112.skeleton.app.base.utils.Direction;
import inf112.skeleton.app.base.utils.Pos;

public class Pusher extends BoardElement implements  IActiveElement {
    private Direction pushDir;
    private Board board;

    public Pusher(Direction dir, Pos pos, char symbol, Board board) {
        super(pos, symbol, board);
        this.pushDir = dir;
        this.board = board;
    }

    @Override
    public void activate() {
        if (board.containsRobot(pos)) {
            Robot robot = (Robot) board.getRobot(pos);
            int x = robot.getPos().x();
            int y = robot.getPos().y();

            if (pushDir == Direction.EAST)
                robot.move(Direction.EAST);

            if (pushDir == Direction.WEST)
                robot.move(Direction.WEST);

            if (pushDir == Direction.NORTH)
                robot.move(Direction.NORTH);

            if (pushDir == Direction.SOUTH)
                robot.move(Direction.SOUTH);
        }
    }

}

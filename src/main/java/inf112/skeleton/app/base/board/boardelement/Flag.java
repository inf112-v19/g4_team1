package inf112.skeleton.app.base.board.boardelement;

import inf112.skeleton.app.base.actors.Robot;
import inf112.skeleton.app.base.board.Board;
import inf112.skeleton.app.base.utils.Pos;

public class Flag extends BoardElement{
    private Board board;
    private Pos pos;

    public Flag(Pos pos, Board board) {
        super(pos, board);
        this.board = board;
        this.pos = pos;
    }

    // updates respawn of robot
    public void setRespawn() {
        if (board.containsRobot(pos)) {
            Robot robot = (Robot) board.getRobot(pos);
            robot.setRespawn();
            robot.addFlag(this);
        }
    }

}

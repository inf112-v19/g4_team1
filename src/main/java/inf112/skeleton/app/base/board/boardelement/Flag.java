package inf112.skeleton.app.base.board.boardelement;

import inf112.skeleton.app.base.actors.Robot;
import inf112.skeleton.app.base.board.Board;
import inf112.skeleton.app.base.utils.Pos;

public class Flag extends BoardElement{
    private Board board;
    private Pos pos;
    private int flagNr;

    public Flag(Pos pos, Board board, int flagNr) {
        super(pos, board);
        this.board = board;
        this.pos = pos;
        this.flagNr = flagNr;
    }

    // updates respawn of robot
    public void setRespawn() {
        if (board.containsRobot(pos)) {
            Robot robot = (Robot) board.getRobot(pos);
            if(robot.getFlags().size() == flagNr-1) {
                robot.setRespawn();
                robot.addFlag(this);
            }
        }
    }

    public int getFlagNr() {
        return flagNr;
    }

}

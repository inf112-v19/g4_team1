package inf112.skeleton.app.base.board.boardelement;

import inf112.skeleton.app.base.actors.Robot;
import inf112.skeleton.app.base.board.Board;
import inf112.skeleton.app.base.utils.Pos;

public class WrenchTile extends BoardElement implements  IActiveElement {
    private Board board;
    private Pos pos;

    public WrenchTile(Pos pos, char symbol, Board board) {
        super(pos, symbol, board);
        this.board = board;
        this.pos = pos;
    }

    // updates respawn of robot
    public void setRespawn() {
        if (board.containsRobot(pos)) {
            Robot robot = (Robot) board.getRobot(pos);
            robot.setRespawn();
        }
    }

    @Override
    public void activate() {
        if (board.containsRobot(pos)) {
            Robot robot = (Robot) board.getRobot(pos);

            if (robot.getHealth() < 10)
                robot.gainHealth();
        }
    }

}

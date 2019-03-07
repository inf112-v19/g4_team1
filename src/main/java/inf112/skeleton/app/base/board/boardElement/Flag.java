package inf112.skeleton.app.base.board.boardElement;


import inf112.skeleton.app.base.actors.Robot;
import inf112.skeleton.app.base.board.Board;
import inf112.skeleton.app.base.utils.Pos;

public class Flag extends BoardElement{
    private Board board;
    private Pos pos;

    public Flag(Pos pos, char symbol, Board board) {
        super(pos, symbol, board);
        this.board = board;
        this.pos = pos;

    }

    //oppdaterer respawn i robot
    public void setRespawn(){
        if (board.containsRobot(pos)) {
            Robot robot = (Robot) board.getRobot(pos);
            robot.respawned();
        }

    }
    

}

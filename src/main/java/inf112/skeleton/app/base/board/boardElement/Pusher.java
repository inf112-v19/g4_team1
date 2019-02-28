package inf112.skeleton.app.base.board.boardElement;

import inf112.skeleton.app.base.board.Board;
import inf112.skeleton.app.base.utils.Direction;
import inf112.skeleton.app.base.utils.Pos;

public class Pusher extends BoardElement implements  IActiveElement{
    private Direction pushDir;
    private Board board;


    public Pusher(Direction dir, Pos pos, char symbol, Board board) {
        super(pos, symbol, board);
        this.pushDir = dir;
    }

    public void activate() {
        if(board.containsRobot(pos)){
            board.getRobot(pos).move(pushDir);
        }
    }
}

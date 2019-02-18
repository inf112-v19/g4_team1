package inf112.skeleton.app.board.boardElement;

import inf112.skeleton.app.actors.IRobot;
import inf112.skeleton.app.board.Board;
import inf112.skeleton.app.utils.Direction;

public class Pusher extends ActiveElement{

    private Direction pushDir;
    private Board board;


    public Pusher(Direction pushDir, int x, int y, char symbol, String name, Board board) {
        this.pushDir = pushDir;
        this.x = x;
        this.y = y;
        this.symbol = symbol;
        this.name = "Gear";
        this.board = board;
    }

    @Override
    public void activate() {
        if(board.containsRobot(x, y)){
            board.getRobot(x, y).move(pushDir);
        }
    }
}

package inf112.skeleton.app.board.boardElement;

import inf112.skeleton.app.actors.IRobot;
import inf112.skeleton.app.board.Board;
import inf112.skeleton.app.utils.Direction;

public class Pusher extends ActiveElement{

    private Direction dir;
    private int x;
    private int y;
    private char symbol;
    private String name;
    private Board board;


    public Pusher(Direction dir, int x, int y, char symbol, String name, Board board) {
        this.dir = dir;
        this.x = x;
        this.y = y;
        this.symbol = symbol;
        this.name = "Gear";
        this.board = board;
    }

    @Override
    public void activate() {
        //TODO:
    }
}

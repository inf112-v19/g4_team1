package inf112.skeleton.app.board.boardElement;

import inf112.skeleton.app.actors.IRobot;
import inf112.skeleton.app.board.Board;
import inf112.skeleton.app.utils.Direction;

public class Conveyor extends ActiveElement {

    private Direction dir;
    private int x;
    private int y;
    private char symbol;
    private String name;
    private Board board;


    public Conveyor(Direction dir, int x, int y, char symbol, Board board) {
        this.dir = dir;
        this.x = x;
        this.y = y;
        this.symbol = symbol;
        this.name = "Conveyor";
        this.board = board;
    }

    @Override
    public void activate() {
        //double distance conveyors should be activated twice
        //TODO:
        //curved conveyors
        if(board.containsRobot(x, y)){
            IRobot robot = board.getRobot(x, y);
            robot.move(this.dir);
        }
    }
}

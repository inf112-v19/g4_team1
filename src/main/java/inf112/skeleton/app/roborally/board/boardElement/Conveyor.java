package inf112.skeleton.app.roborally.board.boardElement;

import inf112.skeleton.app.roborally.actors.IRobot;
import inf112.skeleton.app.roborally.board.Board;
import inf112.skeleton.app.roborally.utils.Direction;

public class Conveyor extends ActiveElement {
    private Direction dir;
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
        if (board.containsRobot(x, y)) {
            IRobot robot = board.getRobot(x, y);
            robot.move(this.dir);
        }
    }
}

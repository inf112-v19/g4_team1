package inf112.skeleton.app.roborally.board.boardElement;

import inf112.skeleton.app.roborally.actors.IRobot;
import inf112.skeleton.app.roborally.board.Board;
import inf112.skeleton.app.roborally.utils.Direction;

public class Gear extends ActiveElement {
    private Direction dir;
    private Board board;

    public Gear(Direction dir, int x, int y, char symbol, Board board) {
        if (dir == Direction.NORTH || dir == Direction.SOUTH)
            throw new IllegalArgumentException("Use arguments EAST OR WEST for gears.");

        this.dir = dir;
        this.x = x;
        this.y = y;
        this.symbol = symbol;
        this.name = "Gear";
        this.board = board;
    }

    @Override
    public void activate() {
        if (board.containsRobot(x, y)) {
            IRobot robot = board.getRobot(x, y);
            if (dir == Direction.EAST) robot.turnRight();
            else if (dir == Direction.WEST) robot.turnLeft();
            else throw new IllegalArgumentException("Use arguments EAST OR WEST for gears.");

        }
    }
}

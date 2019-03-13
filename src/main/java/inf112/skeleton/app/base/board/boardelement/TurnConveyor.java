package inf112.skeleton.app.base.board.boardelement;

import inf112.skeleton.app.base.actors.IRobot;
import inf112.skeleton.app.base.actors.Robot;
import inf112.skeleton.app.base.board.Board;
import inf112.skeleton.app.base.utils.Direction;
import inf112.skeleton.app.base.utils.Pos;

public class TurnConveyor extends BoardElement implements IActiveElement{
    private Direction dir;

    public TurnConveyor(Pos pos, char symbol, Board board) {
        super(pos, symbol, board);
        if (dir == Direction.NORTH || dir == Direction.SOUTH)
            throw new IllegalArgumentException("Use arguments EAST or WEST for gears");

        this.dir = dir;
    }

    @Override
    public void activate() {
        if (board.containsRobot(pos)) {
            IRobot robot = (Robot)board.getRobot(pos);

            if (dir == Direction.EAST) {
                robot.turnRight();
                robot.move(Direction.EAST);
            }
            if (dir == Direction.NORTH) {
                robot.turnRight();
                robot.move(Direction.NORTH);
            }
            if (dir == Direction.WEST) {
                robot.turnRight();
                robot.move(Direction.WEST);
            }
            if (dir == Direction.SOUTH) {
                robot.turnRight();
                robot.move(Direction.SOUTH);
            }

            else throw new IllegalArgumentException("Use arguments EAST or WEST for gears");



        }

    }
}

package inf112.skeleton.app.base.board.boardElement;

import inf112.skeleton.app.base.actors.IRobot;
import inf112.skeleton.app.base.board.Board;
import inf112.skeleton.app.base.utils.Direction;
import inf112.skeleton.app.base.utils.Pos;

public class Gear extends BoardElement implements IActiveElement {

    private Direction dir;


    public Gear(Direction dir, Pos pos, char symbol, Board board) {
        super(pos, symbol, board);
        if(dir == Direction.NORTH || dir == Direction.SOUTH)
            throw new IllegalArgumentException("Use arguments EAST OR WEST for gears");
        this.dir = dir;
    }

    @Override
    public void activate() {
        if(board.containsRobot(pos)){

            IRobot robot = board.getRobot(pos);
            if (dir==Direction.EAST){
                robot.turnRight();
            }
            else if (dir == Direction.WEST){
                robot.turnLeft();
            }else{
                throw new IllegalArgumentException("Use arguments EAST OR WEST for gears");
            }

        }
    }

}

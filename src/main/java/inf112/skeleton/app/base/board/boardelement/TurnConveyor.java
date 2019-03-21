package inf112.skeleton.app.base.board.boardelement;

import inf112.skeleton.app.base.actors.IRobot;
import inf112.skeleton.app.base.board.Board;
import inf112.skeleton.app.base.utils.Direction;
import inf112.skeleton.app.base.utils.Pos;

public class TurnConveyor extends BoardElement implements IActiveElement{
    private Direction dir;

    public TurnConveyor(Direction dir, Pos pos, char symbol, Board board) {
        super(pos, symbol, board);

        this.dir = dir;
    }

    @Override
    public void activate() {
        if (board.containsRobot(pos)) {
            IRobot robot = board.getRobot(pos);

            if (dir == Direction.EAST) {
                robot.move(Direction.EAST);
                robot.turnLeft();
            }else{robot.turnRight();}

            if (dir == Direction.NORTH) {
                robot.turnRight();
                robot.move(Direction.NORTH);
            }else{robot.turnLeft();}

            if (dir == Direction.WEST) {
                robot.turnRight();
                robot.move(Direction.WEST);
            }else{robot.turnLeft();}

            if(dir == Direction.SOUTH){
                robot.turnRight();
                robot.move(Direction.SOUTH);
            }else{robot.turnLeft();}
            // trenger vi 8 forskjellige turncoveyor?

        }

    }
}

package inf112.skeleton.app.base.board.boardelement;

import inf112.skeleton.app.base.actors.IRobot;
import inf112.skeleton.app.base.board.IBoard;
import inf112.skeleton.app.base.utils.Direction;
import inf112.skeleton.app.base.utils.Pos;

public class TurnConveyor extends BoardElement implements IActiveElement{
    private Direction dir;
    private final Direction turndir;

    public TurnConveyor(Direction dir,Direction turndir,  Pos pos, IBoard board) {
        super(pos, board);

        this.dir = dir;
        this.turndir = turndir;
    }

    @Override
    public void activate() {
        if (board.containsRobot(pos)) {
            IRobot robot = board.getRobot(pos);
                /*int startPosX= getPos().x();
                int startPosY= getPos().y();

            if (dir == Direction.EAST &&) {
                robot.move(Direction.EAST);
                if(startPosY<getPos().y());
                robot.turnLeft();
            }else{robot.turnRight();}

            if (dir == Direction.NORTH) {
                robot.move(Direction.NORTH);
                if (startPosX<getPos().x())
                robot.turnRight();
                else{robot.turnLeft();}
            }

            if (dir == Direction.WEST) {
                robot.move(Direction.WEST);
                if(startPosY<getPos().y());
                robot.turnRight();
            }else{robot.turnLeft();}

            if(dir == Direction.SOUTH){
                robot.move(Direction.SOUTH);
                if (startPosX<getPos().x())
                    robot.turnLeft();
                else{robot.turnRight(); }
            }*/

            robot.move(dir);
            if(turndir == Direction.EAST) {
                robot.turnRight();
            }else if(turndir== Direction.WEST){
                robot.turnLeft();
            }
        }

    }
}

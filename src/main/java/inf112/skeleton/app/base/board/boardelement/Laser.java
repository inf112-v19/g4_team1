package inf112.skeleton.app.base.board.boardelement;

import inf112.skeleton.app.base.actors.IRobot;
import inf112.skeleton.app.base.board.Board;
import inf112.skeleton.app.base.board.IBoard;
import inf112.skeleton.app.base.utils.Direction;
import inf112.skeleton.app.base.utils.Pos;

public class Laser extends Wall implements IActiveElement {
    private Direction dir;
    private Pos destination;

    public Laser(Direction dir, Pos pos, IBoard board) {
        super(dir.opposite(), pos, board);
        this.dir = dir;
    }

    @Override
    public IRobot activate() {
        Pos laserPos = pos;

        while (true) {
            if(board.outOfBounds(laserPos)){
                destination = laserPos;
                return null;
            }
            // checks for wall at the near side of the tile
            if (board.getWallDir(laserPos) != null && !laserPos.equals(pos))
                if (dir.opposite() == board.getWallDir(laserPos)) {
                    destination = laserPos.getAdjacent(dir.opposite());
                    return null;
                }

            // damages robot at the tile
            if (board.containsRobot(laserPos)) {
                // shoots robot
                IRobot robot = board.getRobot(laserPos);
                robot.damage();
                destination = laserPos.getAdjacent(dir.opposite());
                return robot;
            }

            // checks for wall at the near side of the tile
            if (board.getWallDir(laserPos) != null)
                if (dir == board.getWallDir(laserPos)) {
                    destination = laserPos;
                    return null;
                }

            // checks next tile in the loop
            if (board.outOfBounds(laserPos.getAdjacent(dir))) {
                destination = laserPos;
                return null;
            }
            laserPos = laserPos.getAdjacent(dir);
        }
    }
    public Pos getDestination() {
        return destination;
    }

}

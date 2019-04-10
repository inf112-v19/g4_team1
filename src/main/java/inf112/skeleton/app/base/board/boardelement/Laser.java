package inf112.skeleton.app.base.board.boardelement;

import inf112.skeleton.app.base.actors.IRobot;
import inf112.skeleton.app.base.board.Board;
import inf112.skeleton.app.base.board.IBoard;
import inf112.skeleton.app.base.utils.Direction;
import inf112.skeleton.app.base.utils.Pos;

public class Laser extends Wall implements IActiveElement {
    private Direction dir;

    public Laser(Direction dir, Pos pos, IBoard board) {
        super(dir.opposite(), pos, board);
        this.dir = dir;
    }

    @Override
    public IRobot activate() {
        Pos laserPos = pos;

        while (true) {
            // checks for wall at the near side of the tile
            if (board.getWallDir(laserPos) != null)
                if (dir == board.getWallDir(laserPos).opposite())
                    return null;

            // damages robot at the tile
            if (board.containsRobot(laserPos)) {
                // shoots robot
                IRobot robot = board.getRobot(laserPos);
                robot.damage();
                return robot;
            }

            // check if hits wall at the far side of the tile
            if (board.getWallDir(laserPos) != null)
                if (dir == board.getWallDir(laserPos))
                    return null;

            // checks next tile in the loop
            laserPos = laserPos.getAdjacent(dir);

            if (board.outOfBounds(laserPos)) return null;
        }
    }

}

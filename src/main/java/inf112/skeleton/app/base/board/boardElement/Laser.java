package inf112.skeleton.app.base.board.boardElement;

import inf112.skeleton.app.base.actors.IRobot;
import inf112.skeleton.app.base.board.Board;
import inf112.skeleton.app.base.utils.Direction;
import inf112.skeleton.app.base.utils.Pos;

public class Laser extends BoardElement implements IActiveElement {

    private Direction dir;


    public Laser(Direction dir, Pos pos, char symbol, Board board) {
        super(pos, symbol, board);
        this.dir = dir;
    }

    @Override
    public void activate() {
        Pos laserPos = pos;

        while (true) {
            //checks for wall at the near side of the tile
            if (board.getWallDir(laserPos) != null) {
                if (dir == board.getWallDir(laserPos).opposite()) {
                    return;
                }
            }
            //damages robot at the tile
            if (board.containsRobot(laserPos)) {
                //shoots robot
                board.getRobot(laserPos).damage();
                return;
            }

            //check if hits wall at the far side of the tile
            if (board.getWallDir(laserPos) != null) {
                if (dir == board.getWallDir(laserPos)) {
                    return;
                }
            }
            //checks next tile in the loop

            laserPos = laserPos.getAdjacent(dir);
            if(board.outOfBounds(laserPos)){
                return;
            }
        }

    }
}


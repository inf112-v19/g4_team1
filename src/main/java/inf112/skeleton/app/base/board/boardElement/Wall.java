package inf112.skeleton.app.base.board.boardElement;

import inf112.skeleton.app.base.board.Board;
import inf112.skeleton.app.base.board.ITileObject;
import inf112.skeleton.app.base.utils.Direction;
import inf112.skeleton.app.base.utils.Pos;

public class Wall extends BoardElement implements ITileObject {

    private Direction wallDir;

    public Wall(Direction dir, Pos pos, char symbol, Board board) {
        super(pos, symbol, board);
        this.wallDir = dir;
    }

    public Direction getWallDir() {
        return wallDir;
    }
}

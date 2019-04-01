package inf112.skeleton.app.base.board.boardelement;

import inf112.skeleton.app.base.board.Board;
import inf112.skeleton.app.base.board.IBoardElement;
import inf112.skeleton.app.base.utils.Direction;
import inf112.skeleton.app.base.utils.Pos;

public class Wall extends BoardElement implements IBoardElement {
    private Direction wallDir;

    public Wall(Direction dir, Pos pos, Board board) {
        super(pos, board);
        this.wallDir = dir;
    }

    public Direction getWallDir() {
        return wallDir;
    }

}

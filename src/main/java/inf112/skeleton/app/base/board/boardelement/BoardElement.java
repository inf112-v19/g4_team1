package inf112.skeleton.app.base.board.boardelement;

import inf112.skeleton.app.base.board.Board;
import inf112.skeleton.app.base.board.IBoard;
import inf112.skeleton.app.base.board.IBoardElement;
import inf112.skeleton.app.base.utils.Pos;

public abstract class BoardElement implements IBoardElement {
    protected final IBoard board;
    protected Pos pos;

    public BoardElement(Pos pos, IBoard board) {
        this.pos = pos;
        this.board = board;
    }

    @Override
    public Pos getPos() {
        return pos;
    }

}

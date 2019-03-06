package inf112.skeleton.app.base.board.boardElement;

import inf112.skeleton.app.base.board.Board;
import inf112.skeleton.app.base.utils.Direction;
import inf112.skeleton.app.base.utils.Pos;

public class Wrenchtile extends BoardElement implements  IActiveElement {

    private Board board;

    public Wrenchtile(Pos pos, char symbol, Board board) {
        super(pos, symbol, board);
        this.board = board;

    }



    @Override
    public void activate() {


    }
}

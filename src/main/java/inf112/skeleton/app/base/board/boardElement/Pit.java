package inf112.skeleton.app.base.board.boardElement;

import inf112.skeleton.app.base.board.Board;
import inf112.skeleton.app.base.board.ITileObject;
import inf112.skeleton.app.base.utils.Pos;

public class Pit extends BoardElement {

    private int x;
    private int y;
    private char symbol;
    private String name;
    private Board board;

    public Pit(Pos pos, char symbol, Board board) {
        super(pos, symbol, board);
    }
}


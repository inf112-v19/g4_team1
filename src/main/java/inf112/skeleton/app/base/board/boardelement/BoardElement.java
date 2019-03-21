package inf112.skeleton.app.base.board.boardelement;

import inf112.skeleton.app.base.board.Board;
import inf112.skeleton.app.base.board.IBoard;
import inf112.skeleton.app.base.board.IBoardElement;
import inf112.skeleton.app.base.utils.Pos;

public abstract class BoardElement implements IBoardElement {
    protected final IBoard board;
    protected Pos pos;
    protected char symbol;
    protected String name;

    public BoardElement(Pos pos, char symbol, IBoard board) {
        this.pos = pos;
        this.symbol = symbol;
        this.name = name;
        this.board = board;
    }

    @Override
    public Pos getPos() {
        return pos;
    }

    @Override
    public char getSymbol() {
        return symbol;
    }

    @Override
    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

}

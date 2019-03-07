package inf112.skeleton.app.base.board.boardElement;

import inf112.skeleton.app.base.board.Board;
import inf112.skeleton.app.base.board.ITileObject;
import inf112.skeleton.app.base.utils.Pos;

public abstract class BoardElement implements ITileObject {

    protected final Board board;
    protected Pos pos;
    protected char symbol;
    protected String name;

    public BoardElement(Pos pos, char symbol,  Board board){

        this.pos = pos;
        this.symbol = symbol;
        this.name = name;
        this.board = board;
    }

    public Pos getPos(){
       return pos;
   }


    public char getSymbol() {
        return symbol;
    }


    public void setSymbol(char symbol) {
        this.symbol=symbol;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name=name;
   }
}
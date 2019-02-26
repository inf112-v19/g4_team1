package inf112.skeleton.app.base.board.boardElement;

import inf112.skeleton.app.base.board.Board;
import inf112.skeleton.app.base.utils.Pos;

public abstract class ActiveElement implements  IActiveElement {
    protected Pos pos;
    protected char symbol;
    protected String name;
    protected Board board;

    public ActiveElement(Pos pos, char symbol, String name){

        this.pos = pos;
        this.symbol = symbol;
        this.name = name;
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
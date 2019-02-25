package inf112.skeleton.app.base.board.boardElement;

public abstract class ActiveElement implements  IActiveElement {
    protected int x;
    protected int y;
    protected char symbol;
    protected String name;


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    public Integer[] getXY() {
        return new Integer[2];
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
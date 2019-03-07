package inf112.skeleton.app.roborally.board.boardElement;

public abstract class ActiveElement implements IActiveElement {
    protected int x, y;
    protected char symbol;
    protected String name;

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public Integer[] getXY() {
        return new Integer[2];
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

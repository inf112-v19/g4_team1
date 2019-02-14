package inf112.skeleton.app.board.boardElement;

import inf112.skeleton.app.board.Board;
import inf112.skeleton.app.board.ITileObject;

public class Pit implements ITileObject {

    private int x;
    private int y;
    private char symbol;
    private String name;
    private Board board;

    public Pit(int x, int y, char symbol, Board board) {
        this.x = x;
        this.y = y;
        this.symbol = symbol;
        this.name = name;
        this.board = board;
        name="pit";
    }

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
        this.symbol=symbol;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name=name;
    }
}

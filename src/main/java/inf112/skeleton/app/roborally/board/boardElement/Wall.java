package inf112.skeleton.app.roborally.board.boardElement;

import inf112.skeleton.app.roborally.board.Board;
import inf112.skeleton.app.roborally.board.ITileObject;
import inf112.skeleton.app.roborally.utils.Direction;

public class Wall implements ITileObject {

    private Direction wallDir;
    private int x, y;
    private char symbol;
    private String name;
    private Board board;

    public Wall(Direction dir, int x, int y, char symbol, Board board) {
        this.wallDir = dir;
        this.x = x;
        this.y = y;
        this.symbol = symbol;
        this.board = board;
        this.name = "Wall";
    }

    public Direction getWallDir(){
        return wallDir;
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

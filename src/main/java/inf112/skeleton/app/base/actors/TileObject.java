package inf112.skeleton.app.base.actors;

import inf112.skeleton.app.base.board.IBoardElement;
import inf112.skeleton.app.base.utils.Pos;

public abstract class TileObject implements IBoardElement {
    protected String name = "Tile Object";
    protected char symbol = '#';
    protected Pos pos;

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

package inf112.skeleton.app.roborally.board;

import java.util.ArrayList;
import java.util.List;

public class Tile implements ITile {
    private List<ITileObject> content;
    private int x, y;

    public Tile() {
        content = new ArrayList<ITileObject>();
        x = -1;
        y = -1;
    }

    public Tile(int x, int y) {
        content = new ArrayList<ITileObject>();
        this.x = x;
        this.y = y;
    }

    @Override
    public void addObject(ITileObject object) {
        content.add(object);
    }

    @Override
    public Boolean contains(ITileObject object) {
        return content.contains(object);
    }

    @Override
    public List<ITileObject> getContent() {
        return content;
    }

    @Override
    public void removeContent(ITileObject object) {
        if (this.contains(object)) content.remove(object);
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }
}

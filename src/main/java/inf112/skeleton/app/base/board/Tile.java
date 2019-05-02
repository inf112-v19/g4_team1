package inf112.skeleton.app.base.board;

import java.util.ArrayList;
import java.util.List;

public class Tile implements ITile {
    private List<IBoardElement> content;

    public Tile() {
        content = new ArrayList<IBoardElement>();
    }

    @Override
    public void addObject(IBoardElement object) {
        content.add(object);
    }

    @Override
    public Boolean contains(IBoardElement object) {
        return content.contains(object);
    }

    @Override
    public List<IBoardElement> getContent() {
        return content;
    }


    @Override
    public void removeContent(IBoardElement object) {
        if (this.contains(object))
            content.remove(object);
    }

}

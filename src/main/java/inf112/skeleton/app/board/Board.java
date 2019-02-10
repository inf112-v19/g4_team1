package inf112.skeleton.app.board;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.skeleton.app.actors.IRobot;
import inf112.skeleton.app.actors.ITileObject;

import java.util.ArrayList;
import java.util.List;

public class Board implements IBoard {
    private List<ITile> board;
    private int height, width;

    public Board(int height, int width) {
        board = new ArrayList<ITile>(height * width);
        this.height = height;
        this.width = width;

        for (int i = 0; i < this.getHeight(); i++)
            for (int j = 0; j < this.getWidth(); j++)
                board.add(new Tile());
    }

    public void setTile(int x, int y, ITile tile) {
        board.set(indexFromCor(x, y), tile);
    }

    public void addTileObject(int x, int y, ITileObject obj) {
        get(x, y).addObject(obj);
    }

    public ITile get(int x, int y) {
        return board.get(indexFromCor(x, y));
    }


    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public List<ITile> getBoard() {
        return board;
    }

    @Override
    public boolean isValidPos(int x, int y) {
        return (x >= 0 && x < getWidth() && y >= 0 & y < getHeight());
    }

    @Override
    public int getSize() {
        return getHeight() * getWidth();
    }

    public boolean containsRobot(int x, int y){
        List<ITileObject> tileObjects =  board.get(indexFromCor(x, y)).getContent();
        for (ITileObject tileObject : tileObjects) {
            if (tileObject instanceof IRobot) {
                return true;
            }
        }
        return false;
    }

    public IRobot getRobot(int x, int y){
        List<ITileObject> tileObjects =  board.get(indexFromCor(x, y)).getContent();
        for (ITileObject tileObject : tileObjects) {
            if (tileObject instanceof IRobot) {
                return (IRobot) tileObject;
            }
        }
        throw new IllegalStateException(x+","+y+" does not ccontain robot");
    }

    private int indexFromCor(int x, int y){
        return x + (getWidth() * y);
    }

    public void draw(SpriteBatch batch) {

    }
}
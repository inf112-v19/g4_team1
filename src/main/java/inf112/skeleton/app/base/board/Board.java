package inf112.skeleton.app.base.board;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.skeleton.app.base.actors.IRobot;
import inf112.skeleton.app.base.board.boardElement.Conveyor;
import inf112.skeleton.app.base.board.boardElement.Pit;
import inf112.skeleton.app.base.board.boardElement.Wall;
import inf112.skeleton.app.base.utils.Direction;
import inf112.skeleton.app.base.utils.Pos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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

    public Board(String textFile) throws IOException {
        FileReader fileReader = new FileReader(textFile);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = null;
        String firstLine = bufferedReader.readLine();
        String[] arr = firstLine.split(",");
        if (arr.length !=2){
            throw new IllegalArgumentException("first line of file is not 2 chars");
        }
        this.height = Integer.parseInt(arr[1]);
        this.width = Integer.parseInt(arr[0]);
        board = new ArrayList<>(height * width);
        int y = height;
        while((line = bufferedReader.readLine()) != null) {
            y--;
            for (int x = 0; x < line.length(); x++) {
                if(line.length()!=width)
                    throw new IllegalArgumentException("wrong length at board textfile at line "+(height-y));
                Tile tile = new Tile();
                Character symbol= line.charAt(x);
                System.out.println("adder "+symbol+" på "+x +" "+y);
                switch(symbol){
                    case '-': break;
                    case 'r': tile.addObject(new Conveyor(Direction.EAST, x, y, 'r', this));
                    case 'd': tile.addObject(new Conveyor(Direction.SOUTH, x, y, 'r', this));
                    case 'p': tile.addObject(new Pit(x, y, 'p', this));

                }
                board.add(tile);
            }
        }
        bufferedReader.close();
    }

    public void setTile(Pos pos, ITile tile) {
        board.set(indexFromCor(pos), tile);
    }

    public void addTileObject(ITileObject obj) {
        Pos pos = obj.getPos();
        get(pos).addObject(obj);
    }


    public ITile get(Pos pos) {
        return board.get(indexFromCor(pos));
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
    public boolean outOfBounds(Pos pos) {
        return (pos.x() < 0 || pos.x() >= getWidth() || pos.y() < 0 || pos.y() >= getHeight());
    }

    @Override
    public int getSize() {
        return getHeight() * getWidth();
    }

    public boolean containsRobot(Pos pos){
        List<ITileObject> tileObjects =  board.get(indexFromCor(pos)).getContent();
        for (ITileObject tileObject : tileObjects) {
            if (tileObject instanceof IRobot) {
                return true;
            }
        }
        return false;
    }

    public IRobot getRobot(Pos pos){
        List<ITileObject> tileObjects =  board.get(indexFromCor(pos)).getContent();
        for (ITileObject tileObject : tileObjects) {
            if (tileObject instanceof IRobot) {
                return (IRobot) tileObject;
            }
        }
        throw new IllegalStateException(pos.x()+","+pos.y()+" does not ccontain robot");
    }

    @Override
    public Direction getWallDir(Pos pos) {
        List<ITileObject> tileObjects =  board.get(indexFromCor(pos)).getContent();
        for (ITileObject tileObject : tileObjects) {
            if (tileObject instanceof Wall) {
                return ((Wall) tileObject).getWallDir();
            }
        }
        return null;
    }


    public boolean containsPit(Pos pos) {
        List<ITileObject> tileObjects =  board.get(indexFromCor(pos)).getContent();
        for (ITileObject tileObject : tileObjects) {
            if (tileObject instanceof Pit) {
                return true;
            }
        }
        return false;
    }


    private int indexFromCor(Pos pos){
        return pos.x() + (getWidth() * pos.y());
    }

    public void draw(SpriteBatch batch) {

    }
}
package inf112.skeleton.app.board;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.skeleton.app.actors.IRobot;
import inf112.skeleton.app.board.boardElement.Conveyor;
import inf112.skeleton.app.board.boardElement.Pit;
import inf112.skeleton.app.board.boardElement.Wall;
import inf112.skeleton.app.utils.Direction;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
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
        int width = Integer.parseInt(arr[0]);
        int height = Integer.parseInt(arr[1]);
        this.height = height;
        this.width = width;
        board = new ArrayList<>(height * width);
        int y = height;
        while((line = bufferedReader.readLine()) != null) {
            y--;
            for (int x = 0; x < line.length(); x++) {
                if(line.length()!=width)
                    throw new IllegalArgumentException("wrong length at board textfile");
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

    public void setTile(int x, int y, ITile tile) {
        board.set(indexFromCor(x, y), tile);
    }

    public void addTileObject(ITileObject obj) {
        int x = obj.getX();
        int y = obj.getY();
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
    public boolean outOfBounds(int x, int y) {
        return (x < 0 || x >= getWidth() || y < 0 || y >= getHeight());
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

    @Override
    public Direction getWallDir(int x, int y) {
        List<ITileObject> tileObjects =  board.get(indexFromCor(x, y)).getContent();
        for (ITileObject tileObject : tileObjects) {
            if (tileObject instanceof Wall) {
                return ((Wall) tileObject).getWallDir();
            }
        }
        return null;
    }


    public boolean containsPit(int x, int y) {
        List<ITileObject> tileObjects =  board.get(indexFromCor(x, y)).getContent();
        for (ITileObject tileObject : tileObjects) {
            if (tileObject instanceof Pit) {
                return true;
            }
        }
        return false;
    }


    private int indexFromCor(int x, int y){
        return x + (getWidth() * y);
    }

    public void draw(SpriteBatch batch) {

    }
}
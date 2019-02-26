package inf112.skeleton.app.base.board;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.skeleton.app.base.actors.IRobot;
import inf112.skeleton.app.base.actors.Player;
import inf112.skeleton.app.base.actors.Robot;
import inf112.skeleton.app.base.board.boardElement.Conveyor;
import inf112.skeleton.app.base.board.boardElement.Pit;
import inf112.skeleton.app.base.board.boardElement.Wall;
import inf112.skeleton.app.base.utils.Direction;

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
                char symbol= line.charAt(x);
                System.out.println("adder " + symbol +" på " + x +" "+y);
                switch (symbol) {
                    case '-': break;
                    case 'r': tile.addObject(new Conveyor(Direction.EAST, x, y, 'r', this));
                    case 'd': tile.addObject(new Conveyor(Direction.SOUTH, x, y, 'd', this));
                    case 'p': tile.addObject(new Pit(x, y, 'p', this));
                    case 'N': tile.addObject(new Wall(Direction.NORTH, x, y, 'N', this));
                    case 'E': tile.addObject(new Wall(Direction.EAST, x, y, 'E', this));
                    case 'S': tile.addObject(new Wall(Direction.SOUTH, x, y, 'S', this));
                    case 'W': tile.addObject(new Wall(Direction.WEST, x, y, 'W', this));
                    case 'R': tile.addObject(new Robot(x, y, Direction.SOUTH,
                            new Player("Player 1"), this));
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
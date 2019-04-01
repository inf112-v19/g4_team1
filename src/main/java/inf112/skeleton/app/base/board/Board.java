package inf112.skeleton.app.base.board;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import inf112.skeleton.app.base.actors.IRobot;
import inf112.skeleton.app.base.actors.Player;
import inf112.skeleton.app.base.actors.Robot;
import inf112.skeleton.app.base.board.boardelement.*;
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
        board = new ArrayList<>(height * width);
        this.height = height;
        this.width = width;

        for (int i = 0; i < this.getHeight(); i++)
            for (int j = 0; j < this.getWidth(); j++)
                board.add(new Tile());
    }

    public Board(String textFile) throws IOException {
        FileReader fileReader = new FileReader(textFile);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line, firstLine = bufferedReader.readLine();
        String[] arr = firstLine.split(",");
        if (arr.length != 2)
            throw new IllegalArgumentException("First line of file is not 2 chars");

        this.height = Integer.parseInt(arr[1]);
        this.width = Integer.parseInt(arr[0]);
        board = new ArrayList<>(height * width);
        int y = height;

        while ((line = bufferedReader.readLine()) != null) {
            y--;

            for (int x = 0; x < line.length(); x++) {
                if (line.length() != width)
                    throw new IllegalArgumentException(
                            "Wrong length in board text file at line " + (height - y));

                Tile tile = new Tile();
                char symbol = line.charAt(x);
                Pos pos = new Pos( x,  y);

                switch (symbol) {
                    case '-': break;

                    case 'r': tile.addObject(
                            new Conveyor(Direction.EAST, pos,  this));
                    case 'd': tile.addObject(
                            new Conveyor(Direction.SOUTH, pos,  this));
                    case 'p': tile.addObject(
                            new Pit(pos,  this));
                    case 'N': tile.addObject(
                            new Wall(Direction.NORTH, pos,  this));
                    case 'E': tile.addObject(
                            new Wall(Direction.EAST, pos,  this));
                    case 'S': tile.addObject(
                            new Wall(Direction.SOUTH, pos,  this));
                    case 'W': tile.addObject(
                            new Wall(Direction.WEST, pos,  this));
                    case 'R': tile.addObject(
                            new Robot(pos, Direction.SOUTH, new Player("Player 1"), this));
                    case 'w': tile.addObject(
                            new WrenchTile(pos,  this));
                    case 's': tile.addObject(
                            new Pusher(Direction.EAST, pos,  this));
                    case 'Q': tile.addObject(
                            new Spawn(pos, 'Q', this));
                }
                board.add(tile);
            }
        }
        bufferedReader.close();
    }

    public Board(TiledMap board) {
        /*
          constructor that adds all elements according to the tiles in the tmx object
         */
        int mapWidth = board.getProperties().get("width", Integer.class);
        int mapHeight = board.getProperties().get("height", Integer.class);
        this.board = new ArrayList<>(height * width);
        this.height = mapHeight;
        this.width = mapWidth;

        for (int i = 0; i < this.getHeight(); i++)
            for (int j = 0; j < this.getWidth(); j++)
                this.board.add(new Tile());

        for (int x = 0; x < mapWidth;   x++) {
            for (int y = 0; y < mapHeight; y++) {
                int id = ((TiledMapTileLayer) board.getLayers().get(0)).getCell(x, y).getTile().getId();
                if(getBoardElemFromTmx(id, new Pos(x, y))!= null)
                    addTileObject(getBoardElemFromTmx(id, new Pos(x, y)));
            }
        }
    }

    private IBoardElement getBoardElemFromTmx(int id, Pos pos) {
        /*
          id is the number of the tile used in the tmx file
         */
        switch(id){
            case 1: return new Pusher(Direction.SOUTH, pos , this);
            case 2: return new Pusher(Direction.WEST, pos , this);
            case 3: return new Pusher(Direction.NORTH, pos , this);
            case 4: return new Pusher(Direction.EAST, pos , this);
            case 5: return null; //this is the empty tile
            case 6: return new Pit(pos, this);
            case 12: return new Conveyor();
            case 14: return new WrenchTile(pos, this);
            case 15: return new Conveyor();
            case 16: return new Conveyor();
            case 17: return new Conveyor();
            case 18: return new Conveyor();
            case 19: return new Conveyor();
            case 20: return new Conveyor();
            case 21: return new Wall(Direction.EAST, pos, this);
            case 26: return new Wall(Direction.SOUTH, pos, this);
            case 27: return new Wall(Direction.WEST, pos, this);
            case 28: return new Wall(Direction.NORTH, pos ,this);
            case 47: return new Gear(Direction.WEST, pos ,this);
            case 48: return new Gear(Direction.EAST, pos ,this);
            case 49: return new Flag(pos, this);
            case 50: return new Spawn(pos, board);

        }
        return new Spawn(pos, 'a', this);
        //throw new IllegalArgumentException("not a valid id");
    }

    @Override
    public void setTile(Pos pos, ITile tile) {
        board.set(indexFromCor(pos), tile);
    }

    @Override
    public void addTileObject(IBoardElement obj) {
        Pos pos = obj.getPos();
        get(pos).addObject(obj);
    }

    @Override
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

    @Override
    public boolean containsRobot(Pos pos) {
        if(outOfBounds(pos))
            throw new IllegalArgumentException(pos+" out of bounds");
        List<IBoardElement> tileObjects =  get(pos).getContent();
        for (IBoardElement tileObject : tileObjects)
            if (tileObject instanceof IRobot) return true;

        return false;
    }

    @Override
    public IRobot getRobot(Pos pos) {
        List<IBoardElement> tileObjects =  board.get(indexFromCor(pos)).getContent();
        for (IBoardElement tileObject : tileObjects)
            if (tileObject instanceof IRobot) return (IRobot) tileObject;

        throw new IllegalStateException(pos.x() + "," + pos.y() + " does not contain robot");
    }

    @Override
    public Direction getWallDir(Pos pos) {
        List<IBoardElement> tileObjects =  board.get(indexFromCor(pos)).getContent();
        for (IBoardElement tileObject : tileObjects)
            if (tileObject instanceof Wall)
                return ((Wall) tileObject).getWallDir();

        return null;
    }

    @Override
    public boolean containsPit(Pos pos) {
        List<IBoardElement> tileObjects =  board.get(indexFromCor(pos)).getContent();
        for (IBoardElement tileObject : tileObjects)
            if (tileObject instanceof Pit) return true;

        return false;
    }

    private int indexFromCor(Pos pos) {
        return pos.x() + (getWidth() * pos.y());
    }

    @Override
    public ArrayList<IActiveElement> getActiveElements() {
        ArrayList<IActiveElement> elems = new ArrayList<>();
        for (ITile tile : board) {
            for (IBoardElement obj : tile.getContent()) {
                if (obj instanceof IActiveElement)
                    elems.add((IActiveElement) obj);
            }
        }

        return elems;
    }

    @Override
    public ArrayList<Flag> getFlags() {
        ArrayList<Flag> elems = new ArrayList<>();
        for (ITile tile : board) {
            for (IBoardElement obj : tile.getContent()) {
                if (obj instanceof Flag)
                    elems.add((Flag) obj);
            }
        }

        return elems;
    }

    @Override
    public ArrayList<WrenchTile> getWrenches() {
        ArrayList<WrenchTile> elems = new ArrayList<>();
        for (ITile tile : board) {
            for (IBoardElement obj : tile.getContent()) {
                if (obj instanceof WrenchTile)
                    elems.add((WrenchTile) obj);
            }
        }

        return elems;
    }

    @Override
    public Pos getSpawn() {
        for (ITile tile  : board) {
            for (IBoardElement obj : tile.getContent()) {
                if (obj instanceof Spawn) {
                    if (!containsRobot(obj.getPos())) {
                        return obj.getPos();
                    }
                }
            }
        }
        throw  new IllegalStateException("No available spawns");
    }

}

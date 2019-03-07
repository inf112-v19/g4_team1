package inf112.skeleton.app.base.board;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.skeleton.app.base.actors.IRobot;
import inf112.skeleton.app.base.actors.Player;
import inf112.skeleton.app.base.actors.Robot;
import inf112.skeleton.app.base.board.boardElement.*;
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
        final int tileWidth = 96;
        final int tileHeight = 96;
        FileReader fileReader = new FileReader(textFile);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line, firstLine = bufferedReader.readLine();
        String[] arr = firstLine.split(",");
        if (arr.length != 2)
            throw new IllegalArgumentException("First line of file is not 2 chars.");

        this.height = Integer.parseInt(arr[1]);
        this.width = Integer.parseInt(arr[0]);
        board = new ArrayList<>(height * width);
        int y = height;
        while((line = bufferedReader.readLine()) != null) {
            y--;
            for (int x = 0; x < line.length(); x++) {
                if (line.length() != width)
                    throw new IllegalArgumentException(
                            "Wrong length in board text file at line " + (height - y));

                Tile tile = new Tile();
                char symbol = line.charAt(x);
                System.out.println("Added " + symbol + " at " + x + " " + y);
                Pos pos = new Pos(tileWidth * x, tileHeight * y);
                switch (symbol) {
                    case '-': break;
                    case 'r': tile.addObject(
                            new Conveyor(Direction.EAST, pos, 'r', this));

                    case 'd': tile.addObject(
                            new Conveyor(Direction.SOUTH, pos, 'd', this));

                    case 'p': tile.addObject(
                            new Pit(pos, 'p', this));

                    case 'N': tile.addObject(
                            new Wall(Direction.NORTH, pos, 'N', this));

                    case 'E': tile.addObject(
                            new Wall(Direction.EAST, pos, 'E', this));

                    case 'S': tile.addObject(
                            new Wall(Direction.SOUTH, pos, 'S', this));

                    case 'W': tile.addObject(
                            new Wall(Direction.WEST, pos, 'W', this));

                    case 'R': tile.addObject(
                            new Robot(pos, Direction.SOUTH, new Player("Player 1"), this));

                    case 'w': tile.addObject(
                            new WrenchTile(pos, 'w', this));

                    case 's': tile.addObject(
                            new Pusher(Direction.EAST, pos, 's', this));
                }

                board.add(tile);
            }
        }
        bufferedReader.close();
    }

    @Override
    public void setTile(Pos pos, ITile tile) {
        board.set(indexFromCor(pos), tile);
    }

    @Override
    public void addTileObject(ITileObject obj) {
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
        List<ITileObject> tileObjects =  board.get(indexFromCor(pos)).getContent();
        for (ITileObject tileObject : tileObjects)
            if (tileObject instanceof IRobot) return true;

        return false;
    }

    @Override
    public IRobot getRobot(Pos pos) {
        List<ITileObject> tileObjects =  board.get(indexFromCor(pos)).getContent();
        for (ITileObject tileObject : tileObjects)
            if (tileObject instanceof IRobot) return (IRobot) tileObject;

        throw new IllegalStateException(pos.x() + "," + pos.y()+ " does not contain robot.");
    }

    @Override
    public Direction getWallDir(Pos pos) {
        List<ITileObject> tileObjects =  board.get(indexFromCor(pos)).getContent();
        for (ITileObject tileObject : tileObjects)
            if (tileObject instanceof Wall) return ((Wall) tileObject).getWallDir();

        return null;
    }

    @Override
    public boolean containsPit(Pos pos) {
        List<ITileObject> tileObjects =  board.get(indexFromCor(pos)).getContent();
        for (ITileObject tileObject : tileObjects)
            if (tileObject instanceof Pit) return true;

        return false;
    }

    private int indexFromCor(Pos pos) {
        return pos.x() + (getWidth() * pos.y());
    }

    public ArrayList<IActiveElement> getActiveElements(){
        ArrayList<IActiveElement> elems = new ArrayList<>();
        for(ITile tile : board){
            for(ITileObject obj : tile.getContent()){
                if(obj instanceof IActiveElement){
                    elems.add((IActiveElement) obj);
                }
            }
        }
        return elems;
    }
    public ArrayList<Flag> getFlags(){
        ArrayList<Flag> elems = new ArrayList<>();
        for(ITile tile : board){
            for(ITileObject obj : tile.getContent()){
                if(obj instanceof Flag){
                    elems.add((Flag) obj);
                }
            }
        }
        return elems;
    }

    public void draw(SpriteBatch batch) {

    }

    public ArrayList<WrenchTile> getWrenches() {
        ArrayList<WrenchTile> elems = new ArrayList<>();
        for(ITile tile : board){
            for(ITileObject obj : tile.getContent()){
                if(obj instanceof WrenchTile){
                    elems.add((WrenchTile) obj);
                }
            }
        }
        return elems;
    }
}

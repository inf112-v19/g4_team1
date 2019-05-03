package inf112.skeleton.app.base.utils;

import java.util.ArrayList;

/**
 * Class that represents (x, y) coordinates
 */
public class Pos {
    private final int x, y;

    public Pos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * get x
     *
     * @return x
     */
    public int x() {
        return x;
    }

    /**
     * get y
     *
     * @return y
     */
    public int y() {
        return y;
    }

    /**
     * get the adjacent position based on the direction
     *
     * @param dir direction to the adjacent position
     * @return adjacent position
     */
    public Pos getAdjacent(Direction dir) {
        int newX = x;
        int newY = y;
        switch (dir) {
            case EAST: newX++; break;
            case WEST: newX--; break;
            case NORTH: newY++; break;
            case SOUTH: newY--; break;
        }

        return new Pos(newX, newY);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pos pos = (Pos) o;
        return x == pos.x && y == pos.y;
    }

    @Override
    public String toString(){
        return "(" + x + ", " + y + ")";
    }

    /**
     * all 4 Positions connected to this. can be out of bounds
     * @return list with 4 positions
     */
    public ArrayList<Pos> getAllAdjacent() {
        ArrayList<Pos> pos = new ArrayList<>();
        for(Direction dir : Direction.values()){
            pos.add(this.getAdjacent(dir));
        }
        return pos;
    }
}

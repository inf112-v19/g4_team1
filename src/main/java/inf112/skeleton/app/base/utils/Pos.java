package inf112.skeleton.app.base.utils;

import java.util.Objects;

public class Pos {
    private final int x, y;

    public Pos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }
    public Pos getAdjacent(Direction dir){
        int newX = x;
        int newY = y;
        switch (dir){
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
        return x == pos.x &&
                y == pos.y;
    }

}

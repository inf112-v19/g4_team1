package inf112.skeleton.app.base.utils;

/**
 * Enumerator that contains four directions
 */
public enum Direction {
    NORTH,
    EAST,
    SOUTH,
    WEST,
    ;

    /**
     * change the direction to relative left direction
     */
    public Direction left() {
        switch (this) {
            case NORTH: return Direction.WEST;
            case EAST: return Direction.NORTH;
            case WEST: return Direction.SOUTH;
            case SOUTH: return Direction.EAST;
        }

        throw new IllegalStateException("no direction");
    }

    /**
     * change the direction to relative right direction
     */
    public Direction right() {
        switch (this) {
            case NORTH: return Direction.EAST;
            case EAST: return Direction.SOUTH;
            case WEST: return Direction.NORTH;
            case SOUTH: return Direction.WEST;
        }

        throw new IllegalStateException("no direction");
    }

    /**
     * change the direction to the relative opposite direction
     *
     * @return opposite direction
     */
    public Direction opposite(){
        return this.left().left();
    }

    public int getRotationDegrees(){
        switch(this){
            case NORTH: return 0;
            case EAST: return 270;
            case SOUTH: return 180;
            case WEST: return 90;
        }
        throw new IllegalArgumentException();
    }

}

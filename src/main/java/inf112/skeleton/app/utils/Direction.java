package inf112.skeleton.app.utils;

public enum Direction {
    NORTH,
    EAST,
    SOUTH,
    WEST,
    ;

    /**
     * methods for returning another relative direction
     */
    public Direction left(){
        switch (this){
            case NORTH: return Direction.WEST;
            case EAST: return Direction.NORTH;
            case WEST: return Direction.SOUTH;
            case SOUTH: return Direction.EAST;
        }
        throw new IllegalStateException("no direction");

    }

    public Direction right(){
        switch (this){
            case NORTH: return Direction.EAST;
            case EAST: return Direction.SOUTH;
            case WEST: return Direction.NORTH;
            case SOUTH: return Direction.WEST;
        }
        throw new IllegalStateException("no direction");

    }
    public Direction opposite(){
        return this.left().left();
    }
}

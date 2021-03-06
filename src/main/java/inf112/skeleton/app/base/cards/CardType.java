package inf112.skeleton.app.base.cards;

/**
 * Enumerator that contains different types of cards
 */
public enum CardType {
    TURN_RIGHT,
    TURN_LEFT,
    TURN_HALF,
    MOVE_1_TILE,
    MOVE_2_TILE,
    MOVE_3_TILE,
    MOVE_BACK,
    POWERDOWN;

    @Override
    public String toString() {
        switch (this) {
            case TURN_HALF: return "Turn 180 degrees";
            case TURN_LEFT: return "Quarter Turn Left";
            case TURN_RIGHT: return "Quarter Turn Right";
            case MOVE_1_TILE: return "Move 1 Tile forward";
            case MOVE_2_TILE: return "Move 2 Tile forward";
            case MOVE_3_TILE: return "Move 3 Tile forward";
            case MOVE_BACK: return "Move 1 Tile backwards";
            case POWERDOWN: return "powerdown";
        }

        throw new IllegalStateException("no card type");
    }

}

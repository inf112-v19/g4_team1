package inf112.skeleton.app.base.board;

import inf112.skeleton.app.base.utils.Pos;

/**
 * Generic interface for the object that is located on the board
 */
public interface IBoardElement {

    /**
     * Get the position of the object
     *
     * @return position
     */
    Pos getPos();

}

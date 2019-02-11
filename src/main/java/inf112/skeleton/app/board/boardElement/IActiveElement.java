package inf112.skeleton.app.board.boardElement;

import inf112.skeleton.app.board.ITileObject;

/**
 * all elements on the board that needs to be activated after the robots have moved. does not include "always on"
 * elements like pit and wall
 */
public interface IActiveElement extends ITileObject {
    /**
     * activates the element and makes it push/attack/...
     */
    void activate();
}

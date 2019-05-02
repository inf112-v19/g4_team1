package inf112.skeleton.app.base.board.boardelement;

import inf112.skeleton.app.base.actors.IRobot;
import inf112.skeleton.app.base.board.IBoardElement;

/**
 * all elements on the board that needs to be activated after the robots have moved. does not include "always on"
 * elements like pit and wall
 */
public interface IActiveElement extends IBoardElement {

    /**
     * activates the element and makes it push/attack/...
     */
    IRobot activate();

}

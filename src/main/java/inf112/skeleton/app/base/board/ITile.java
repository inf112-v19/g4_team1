package inf112.skeleton.app.base.board;

import java.util.List;

/**
 * Generic interface for the board tile
 */
public interface ITile {

    /**
     * Place the object in the tile
     *
     * @param object element to be placed
     */
    void addObject(IBoardElement object);

    /**
     * Check if the tile contains the element
     *
     * @param object the element to check
     * @return true if the tile contains the element, false otherwise
     */
    Boolean contains(IBoardElement object);

    /**
     * Return the content of the tile
     *
     * @return content of the tile
     */
    List<IBoardElement> getContent();

    /**
     * Delete an element from the tile
     *
     * @param object element that will be removed.
     */
    void removeContent(IBoardElement object);

    /**
     *
     * @return true if tile contains instance of Robot-class
     */
    boolean containsRobot();
}

package com.mygdx.frick;

import java.util.List;

/**
 * Generic interface for the board tile.
 *
 */
public interface ITile {

    /**
     * Place the object in the tile.
     *
     * @param object element to be placed
     */
    void addObject(ITileObject object);

    /**
     * Check if the tile contains the element
     *
     * @param object the element to check
     * @return true if the tile contains the element, false otherwise
     */
    Boolean contains(ITileObject object);

    /**
     * Return the content of the tile
     *
     * @return content of the tile
     */
    List<ITileObject> getContent();

    /**
     * Delete an element from the tile
     *
     * @param object element that will be removed.
     */
    void removeContent(ITileObject object);

    /**
     * Get the x coordinate of the tile
     *
     * @return x
     */
    int getX();

    /**
     * Get the y coordinate of the tile
     *
     * @return y
     */
    int getY();

    /**
     * Get the array containing x and y coordinates
     *
     * @return [x,y]
     */
    Integer[] getXY();
}

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
    void addObject(TileObject object);

    /**
     * Check if the tile contains the element
     *
     * @param object the element to check
     * @return true if the tile contains the element, false otherwise
     */
    Boolean contains(TileObject object);

    /**
     * Return the content of the tile
     *
     * @return content of the tile
     */
    List<TileObject> getContent();

    /**
     * Delete an element from the tile
     *
     * @param object element that will be removed.
     */
    void removeContent(TileObject object);
}

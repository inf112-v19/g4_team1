package inf112.skeleton.app.roborally.screens;

/**
 * a set of constants to have a constant scaling of the game
 * regardless of the resolution of the screen
 */
public class Constants {
    public static final float PPM = 4;
    public static final float MPP = 1 / PPM;

    public static final int WORLD_PIXEL_WIDTH = 96 * 18;
    public static final int WORLD_PIXEL_HEIGHT = 96 * 15;
    public static final float WORLD_WIDTH = WORLD_PIXEL_WIDTH / PPM;
    public static final float WORLD_HEIGHT = WORLD_PIXEL_HEIGHT / PPM;
}

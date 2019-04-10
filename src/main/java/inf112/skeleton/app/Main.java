package inf112.skeleton.app;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import inf112.skeleton.app.roborally.RoboRally;
import inf112.skeleton.app.roborally.screens.RoboRallyGame;

public class Main {
    public static void main(String[] args) {
        LwjglApplicationConfiguration roboRallyCfg = new LwjglApplicationConfiguration();

        roboRallyCfg.title = "Robo Rally v0.004";
        roboRallyCfg.width = 1920;
        roboRallyCfg.height = 1080;
        //roboRallyCfg.fullscreen = true;
        roboRallyCfg.useHDPI =true;

        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();


        new LwjglApplication(new RoboRally(), roboRallyCfg);
    }
}

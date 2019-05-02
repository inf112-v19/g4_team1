package inf112.skeleton.app;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import inf112.skeleton.app.roborally.RoboRally;

public class Main {
    public static void main(String[] args) {
        LwjglApplicationConfiguration roboRallyCfg = new LwjglApplicationConfiguration();

        roboRallyCfg.title = "Robo Rally v0.005";
        //roboRallyCfg.fullscreen = true;
        roboRallyCfg.width = 1920;
        roboRallyCfg.height = 1080;
        roboRallyCfg.useHDPI = true;

        new LwjglApplication(new RoboRally(), roboRallyCfg);
    }
}

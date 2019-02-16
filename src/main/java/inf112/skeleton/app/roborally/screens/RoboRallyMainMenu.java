package inf112.skeleton.app.roborally.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import inf112.skeleton.app.roborally.RoboRally;

public class RoboRallyMainMenu implements Screen {
    private final RoboRally roboRally;
    private OrthographicCamera camera;

    public RoboRallyMainMenu(RoboRally roboRally) {
        this.roboRally = roboRally;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1024, 768);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        roboRally.batch.setProjectionMatrix(camera.combined);

        roboRally.batch.begin();
        //
        // TODO: add some buttons + background and then switch the screen to the game board
        //
        roboRally.batch.end();

    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}

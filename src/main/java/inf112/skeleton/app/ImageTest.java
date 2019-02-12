package inf112.skeleton.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class ImageTest implements ApplicationListener {
    private Stage stage;

    @Override
    public void create() {
        stage = new Stage(new ScreenViewport());
        Texture texture = new Texture("assets/image.jpg");

        Image image1 = new Image(texture);
        image1.setSize(texture.getWidth()/1.5f, texture.getHeight()/1.5f);
        image1.setPosition(Gdx.graphics.getWidth()/3f - image1.getWidth()/2f,
                Gdx.graphics.getHeight()*2f/3f - image1.getHeight()/2f);
        stage.addActor(image1);

        Image image2 = new Image(texture);
        image2.setSize(texture.getWidth()/1.5f, texture.getHeight()/1.5f);
        image2.setPosition(Gdx.graphics.getWidth()*2f/3f - image2.getWidth()/2f,
                Gdx.graphics.getHeight()*2f/3f - image2.getHeight()/2f);
        image2.setOrigin(image2.getWidth()/2f, image2.getHeight()/2f);
        image2.rotateBy(45);
        stage.addActor(image2);

        Image image3 = new Image(texture);
        image3.setSize(texture.getWidth()/2f, texture.getHeight()/2f);
        image3.setPosition(Gdx.graphics.getWidth()/3f - image3.getWidth()/2f,
                Gdx.graphics.getHeight()/3f - image3.getHeight()/2f);
        stage.addActor(image3);

        texture.setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
        TextureRegion textureRegion = new TextureRegion(texture);
        textureRegion.setRegion(2, 2, texture.getWidth()*2f, texture.getHeight()*2f);
        Image image4 = new Image(textureRegion);
        image4.setSize(300, 200);
        image4.setPosition(Gdx.graphics.getWidth()*2f/3f - image4.getWidth()/2f,
                Gdx.graphics.getHeight()/3f - image4.getHeight()/2f);
        stage.addActor(image4);
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}

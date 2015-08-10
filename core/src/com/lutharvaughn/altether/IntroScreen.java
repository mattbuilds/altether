package com.lutharvaughn.altether;

/**
 * Created by Matt on 12/7/14.
 */
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class IntroScreen implements Screen {

    private BoxPuzzle game; // Note it's "MyGame" not "Game"
    private SpriteBatch batch;
    private Sprite title, play, more, about, by;
    private OrthographicCamera camera;
    private BitmapFont font, big_font;

    // constructor to keep a reference to the main Game class
    public IntroScreen(BoxPuzzle game, int game_width, int game_height){
        this.game = game;
        batch = new SpriteBatch();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();
        title = new Sprite(new Texture(this.game.resolution + "/title.png"));
        title.setPosition(camera.viewportWidth / 2f - title.getWidth() / 2f, camera.viewportHeight - title.getHeight() * 1.4f);

        play = new Sprite(new Texture(this.game.resolution + "/play.png"));
        play.setPosition(camera.viewportWidth/2f - play.getWidth()/2f, camera.viewportHeight/2f + play.getHeight());
        more = new Sprite(new Texture(this.game.resolution + "/more.png"));
        more.setPosition(camera.viewportWidth/2f - more.getWidth()/2f, camera.viewportHeight/2f - .76f * more.getHeight());
        about = new Sprite(new Texture(this.game.resolution + "/about.png"));
        about.setPosition(camera.viewportWidth/2f - about.getWidth()/2f, camera.viewportHeight/2f - 2.5f *about.getHeight());
        by = new Sprite(new Texture(this.game.resolution + "/by.png"));
        by.setPosition(camera.viewportWidth - by.getWidth(), 0);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(71 / 255f, 81 / 255f, 93/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        title.draw(batch);
        play.draw(batch);
        more.draw(batch);
        about.draw(batch);
        by.draw(batch);
        batch.end();
    }

    public boolean spriteTouched(Sprite sprite, int x, int y){
        if(x >=sprite.getX() && x <= sprite.getX() + sprite.getWidth() &&
                y >= sprite.getY() && y <= sprite.getY() + sprite.getHeight() ){
            return true;
        }
        return false;
    }

    public void touch(int x, int y){
        if(spriteTouched(play, x, y) )
            this.game.setMenu();
        if(spriteTouched(more, x, y))
            this.game.setScreen(this.game.moreLevels);
        if(spriteTouched(about, x, y))
            this.game.setScreen(this.game.aboutScreen);
    }


    @Override
    public void resize(int width, int height) {

    }


    @Override
    public void show() {
        // called when this screen is set as the screen with altether.setScreen();
    }


    @Override
    public void hide() {
        // called when current screen changes from this to a different screen
    }


    @Override
    public void pause() {
    }


    @Override
    public void resume() {
    }


    @Override
    public void dispose() {
        // never called automatically
    }

}

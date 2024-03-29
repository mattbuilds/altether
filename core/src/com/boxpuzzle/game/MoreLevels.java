package com.boxpuzzle.game;

/**
 * Created by Matt on 12/7/14.
 */
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;
import java.util.List;

public class MoreLevels implements Screen {

    private BoxPuzzle game; // Note it's "MyGame" not "Game"
    private SpriteBatch batch;
    private Sprite title, more_text, back, by;
    private OrthographicCamera camera;
    private BitmapFont font, big_font;

    // constructor to keep a reference to the main Game class
    public MoreLevels(BoxPuzzle game, int game_width, int game_height){
        this.game = game;
        batch = new SpriteBatch();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();
        title = new Sprite(new Texture(this.game.resolution + "/title.png"));
        title.setPosition(camera.viewportWidth / 2f - title.getWidth() / 2f, camera.viewportHeight - title.getHeight() * 1.4f);
        more_text = new Sprite(new Texture(this.game.resolution + "/more_text.png"));
        more_text.setPosition(camera.viewportWidth / 2f - more_text.getWidth() / 2f, camera.viewportHeight / 2f - more_text.getHeight() / 2f);

        by = new Sprite(new Texture(this.game.resolution + "/by.png"));
        by.setPosition(camera.viewportWidth - by.getWidth(), 0);

        back = new Sprite(new Texture(this.game.resolution + "/back_text.png"));
        back.setPosition(camera.viewportWidth / 2f - back.getWidth() / 2f, (more_text.getY() + by.getHeight())/2f - back.getHeight()/2f);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(71 / 255f, 81 / 255f, 93 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        title.draw(batch);
        more_text.draw(batch);
        back.draw(batch);
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
        if(spriteTouched(back, x, y) )
            this.game.setScreen(this.game.introScreen);
    }

    @Override
    public void resize(int width, int height) {

    }


    @Override
    public void show() {
        // called when this screen is set as the screen with game.setScreen();
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

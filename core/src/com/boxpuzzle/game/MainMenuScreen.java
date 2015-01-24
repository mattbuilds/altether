package com.boxpuzzle.game;

/**
 * Created by Matt on 12/7/14.
 */
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;
import java.util.List;

public class MainMenuScreen implements Screen {

    private BoxPuzzle game; // Note it's "MyGame" not "Game"
    private OrthographicCamera camera;
    private FitViewport viewport;
    private SpriteBatch batch;
    private Texture check, texture, one_texture, two_texture;
    private List<Sprite> sprites, checks;
    private float sprite_x_offset, sprite_y_offset, scale_x, scale_y;
    private BitmapFont font;

    // constructor to keep a reference to the main Game class
    public MainMenuScreen(BoxPuzzle game, int game_width, int game_height){
        this.game = game;

        sprites = new ArrayList<Sprite>();
        checks = new ArrayList<Sprite>();

        camera = new OrthographicCamera(game_width, game_height);
        camera.position.set(camera.viewportWidth/2f, camera.viewportHeight/2f, 0);
        camera.update();
        viewport = new FitViewport(game_width,game_height,camera);
        viewport.apply();

        font = new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false);
        //font.setColor(1, 1, 1, 1);
        //font.setScale(.35f);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        check = new Texture("check.png");
        check.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        batch = new SpriteBatch();
        one_texture = new Texture("two.png");
        one_texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        two_texture = new Texture("two.png");
        two_texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        for (int i = 1; i<= 35; i++){
            String number_name = "box.png";
            texture = new Texture(number_name);
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            Sprite adding = new Sprite(texture);

            float width = getWidth(i-1);
            float height = getHeight(i-1);

            adding.setOrigin(0,0);
            adding.setPosition(camera.viewportHeight/2f + adding.getWidth() *width, camera.viewportHeight/2f + adding.getHeight()*height);

            sprites.add(adding);
        }
    }

    public float getHeight(int i){
        i = (i/5)%9 + 1;

        if (i == 1){
            return 5f;
        } else if (i == 2){
            return 3.5f;
        } else if (i == 3){
            return 2f;
        } else if (i == 4){
            return .5f;
        } else if (i == 5){
            return -1f;
        } else if (i == 6){
            return -2.5f;
        } else if (i == 7){
            return -4f;
        } else if (i == 8){
            return -5.5f;
        } else if (i == 9){
            return -7f;
        } else {
            return 0f;
        }
    }

    public float getWidth(int i){
        i = i%5+1;

        if (i == 1){
          return -6f;
        } else if (i == 2){
            return -4.5f;
        } else if (i == 3){
            return -3f;
        } else if (i == 4){
            return -1.5f;
        } else if (i == 5){
            return 0f;
        } else{
            return 0f;
        }
    }

    public void updateCompletedLevel(int lvl_num){
        float sprite_x = sprites.get(lvl_num).getX();
        float sprite_y = sprites.get(lvl_num).getY();
        checks.add(new Sprite(check));
        checks.get(checks.size()-1).setPosition(sprite_x, sprite_y);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(71/255f, 81/255f, 93/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();


        for (int i = 0; i<sprites.size(); i++){
            sprites.get(i).draw(batch);
        }
        for (int i = 0; i<checks.size(); i++){
            checks.get(i).draw(batch);
        }
        for (int i = 0; i<sprites.size(); i++){
            if (i < 9){
                font.draw(batch, Integer.toString(i+1), sprites.get(i).getX()+21, sprites.get(i).getY()+48);
            } else{
                font.draw(batch, Integer.toString(i+1), sprites.get(i).getX()+8, sprites.get(i).getY()+48);
            }
        }
        batch.end();
    }

    public void touch(int x, int y){
        Boolean clicked = false;
        sprite_x_offset = (Gdx.graphics.getWidth() - viewport.getLeftGutterWidth() - viewport.getRightGutterWidth())/camera.viewportWidth;
        sprite_y_offset = (Gdx.graphics.getHeight() - viewport.getTopGutterHeight() - viewport.getBottomGutterHeight())/camera.viewportHeight;
        for (int i = 0; i< sprites.size(); i++){
            clicked = number_click(x,y, sprites.get(i));
            if (clicked == true){
                game.setGameScreen(i+1);
            }
        }
    }

    private boolean number_click(int x, int y, Sprite sprite){
        if(x >= (sprite.getX() * sprite_x_offset + viewport.getLeftGutterWidth()) &&
                x <= ((sprite.getX() + sprite.getWidth()) * sprite_x_offset + viewport.getLeftGutterWidth()) &&
                y >= (sprite.getY() * sprite_y_offset + viewport.getBottomGutterHeight()) &&
                y <= ((sprite.getY() + sprite.getHeight()) * sprite_y_offset + viewport.getBottomGutterHeight()) ){
           return true;
        } else{
            return false;
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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

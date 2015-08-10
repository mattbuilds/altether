package com.lutharvaughn.altether;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.util.List;

public class GameScreen implements Screen{
    SpriteBatch batch, fixed_batch;
    //Arrow arrow;
    FPSLogger fpsLogger;
    Texture back_text, refresh_text, previous_text, next_text, popup_text, popup_box_text;
    Sprite top_bar, menu, back, refresh, previous, next, popup, lvl_comp, menu_popup, next_popup, grey;
    BitmapFont font, popup_font;

    Texture red_text, blue_text, red_goal, blue_goal, green_text, green_goal, yellow_text, yellow_goal,
            purple_text, purple_goal, wall_img;
    Sprite background_grid;

    Player player;
    List<Box> boxes;
    Grid grid;
    Level level;
    JsonValue levels;
    private int width, height, lvl_num = 1;
    private int offset = 64;
    private float time;
    private boolean won = false;
    private OrthographicCamera camera;

    BoxPuzzle game; // Note it's "my altether" not "your altether"

    // constructor to keep a reference to the main Game class
    public GameScreen(BoxPuzzle game, int cam_width, int cam_height){
        this.game = game;

        //Load levels
        FileHandle file = Gdx.files.internal("levels.txt");
        String text = file.readString();
        levels = new JsonReader().parse(text);

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();

        fixed_batch = new SpriteBatch();
        grey = new Sprite(new Texture("grey.png"));
        grey.setPosition(0, 0);
        grey.setSize(camera.viewportWidth, camera.viewportHeight);

        top_bar = new Sprite(new Texture("top_bar.png"));
        top_bar.setSize(camera.viewportWidth, camera.viewportWidth / 10f * 1.5f);
        top_bar.setPosition(0,Gdx.graphics.getHeight() - top_bar.getHeight());

        menu = new Sprite(new Texture(this.game.resolution + "/menu.png"));
        menu.setPosition(camera.viewportWidth - menu.getWidth(), camera.viewportHeight - top_bar.getHeight() / 2f - menu.getHeight() / 2f);

        background_grid = new Sprite(new Texture(game.resolution + "/tile.png"));
        red_text = new Texture(Gdx.files.internal(game.resolution + "/red_tile.png"), true);
        red_goal = new Texture(game.resolution + "/red_goal.png");
        blue_text = new Texture(game.resolution + "/blue_tile.png");
        blue_goal = new Texture(game.resolution +"/blue_goal.png");
        green_text = new Texture(game.resolution +"/green_tile.png");
        green_goal = new Texture(game.resolution +"/green_goal.png");
        yellow_text = new Texture(game.resolution + "/yellow_tile.png");
        yellow_goal = new Texture(game.resolution + "/yellow_goal.png");

        batch = new SpriteBatch();
        level = new Level(this.game, background_grid, red_text, red_goal, blue_text, blue_goal, green_text, green_goal, yellow_text, yellow_goal);
        level.load(lvl_num, levels);

        font = new BitmapFont(Gdx.files.internal(this.game.resolution +"/font.fnt"), Gdx.files.internal(this.game.resolution +"/font.png"), false);
        //font.setColor(221/255f, 181/255f, 85/255f, 1);
        //font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        popup_font = new BitmapFont(Gdx.files.internal(this.game.resolution +"/font.fnt"), Gdx.files.internal(this.game.resolution +"/font.png"), false);
        popup_font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        //popup_font.setColor(35/255f, 55/255f, 55/255f, 1);
        popup_font.setScale(.8f);

        back_text = new Texture(this.game.resolution +"/back.png");
        back = new Sprite(back_text);
        back.setOrigin(0,0);
        back.setPosition(back.getWidth(),camera.viewportHeight - ((camera.viewportHeight -camera.viewportWidth)/2f) - back.getHeight()/4f) ;

        refresh_text = new Texture(this.game.resolution +"/refresh.png");
        refresh = new Sprite(refresh_text);
        refresh.setOrigin(0, 0);
        refresh.setPosition(camera.viewportWidth/2 - (refresh.getWidth()/2),0);

        previous = new Sprite(new Texture(this.game.resolution +"/back.png"));
        previous.setOrigin(0, 0);
        previous.setPosition(camera.viewportWidth/2 - 2f *previous.getWidth(),refresh.getHeight()/2f - previous.getHeight()/2f);


        next = new Sprite(new Texture(this.game.resolution +"/next.png"));
        next.setOrigin(0, 0);
        next.setPosition(camera.viewportWidth/2 + next.getWidth(),refresh.getHeight()/2f - next.getHeight()/2f);

        popup_text = new Texture(this.game.resolution +"/popup.png");
        popup = new Sprite(popup_text);
        popup.setOrigin(0,0);
        popup.setPosition(camera.viewportWidth/2 - popup.getWidth()/2,camera.viewportHeight/2 - popup.getHeight()/2);

        lvl_comp = new Sprite(new Texture(this.game.resolution +"/lvl_comp.png"));
        lvl_comp.setOrigin(0,0);
        lvl_comp.setPosition(camera.viewportWidth/2 - lvl_comp.getWidth()/2,camera.viewportHeight/2);

        menu_popup = new Sprite(new Texture(this.game.resolution +"/menu_popup.png"));
        menu_popup.setOrigin(0,0);
        menu_popup.setPosition(camera.viewportWidth/2 - 1.5f*menu_popup.getWidth(),camera.viewportHeight/2 - 1.25f* menu_popup.getHeight());

        next_popup = new Sprite(new Texture(this.game.resolution +"/next_popup.png"));
        next_popup.setOrigin(0,0);
        next_popup.setPosition(camera.viewportWidth/2 + .5f*next_popup.getWidth(),camera.viewportHeight/2 - 1.25f* next_popup.getHeight());

        fpsLogger = new FPSLogger();
    }

    public void load_level(int id){
        System.out.println("In Level");
        lvl_num = id;
        level = new Level(this.game, background_grid, red_text, red_goal, blue_text, blue_goal, green_text, green_goal, yellow_text, yellow_goal);
        level.load(lvl_num, levels);
    }

    @Override
    public void render (float something) {
        time = Gdx.graphics.getDeltaTime();
        level.boxesSpeed(700, 700, offset, time);
        level.doCollisions();
        //level.stuff();
        //player.getSpeed(width, height, offset, time);
        Gdx.gl.glClearColor(71/255f, 81/255f, 93/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        //arrow.drawArrow(batch);
        level.draw(batch);
        top_bar.draw(batch);
        menu.draw(batch);
        font.draw(batch, "Level " +lvl_num, camera.viewportWidth/2f - font.getBounds("Level " + lvl_num).width/2f, camera.viewportHeight - top_bar.getHeight()/2f +  font.getBounds("Level " + lvl_num).height/2f );
        //font.draw(batch, "Level " +lvl_num, camera.viewportWidth/2f - font.getBounds("Level " + lvl_num).width/2f, camera.viewportHeight - camera.viewportWidth/10f *1.5f + (1.5f * font.getBounds("Level  " + lvl_num).height) );
        //back.draw(batch);
        if (lvl_num != 1){
            previous.draw(batch);
        }
        refresh.draw(batch);
        if (lvl_num !=  this.game.mainMenuScreen.levels_completed.size){
            next.draw(batch);
        }
        if (won == false){
            won = level.checkCompleted(this.game, lvl_num);
        } else{
            grey.draw(batch);
            popup.draw(batch);
            lvl_comp.draw(batch);
            menu_popup.draw(batch);
            if (lvl_num !=  this.game.mainMenuScreen.levels_completed.size){
                next_popup.draw(batch);
            }
        }
        batch.end();
        fpsLogger.log();
    }

    public void move (int keycode) {
        if (keycode == 46){
            game.analytics.writeEvent("Refresh lvl "+lvl_num);
            level = new Level(this.game, background_grid, red_text, red_goal, blue_text, blue_goal, green_text, green_goal, yellow_text, yellow_goal);
            level.load(lvl_num, levels);
        }

        if (keycode == 66 && won == true){
            game.analytics.writeEvent("Continuted from lvl "+lvl_num);
            won = false;
            level = new Level(this.game, background_grid, red_text, red_goal, blue_text, blue_goal, green_text, green_goal, yellow_text, yellow_goal);
            lvl_num += 1;
            level.load(lvl_num, levels);
        }
        level.handleInput(keycode);
    }

    public void touch (int x, int y) {
        if (won == true){
            if (spriteTouch(x,y,next_popup)){
                game.analytics.writeEvent("Continuted from lvl "+lvl_num);
                won = false;
                level = new Level(this.game, background_grid, red_text, red_goal, blue_text, blue_goal, green_text, green_goal, yellow_text, yellow_goal);
                lvl_num += 1;
                level.load(lvl_num, levels);
            }

            if (spriteTouch(x,y,menu_popup)){
                game.analytics.writeEvent("Main menu from lvl "+lvl_num);
                won = false;
                game.setMenu();
            }
        } else{
            if (spriteTouch(x,y, menu)){
                game.analytics.writeEvent("Main menu from lvl "+lvl_num);
                game.setMenu();
            }

            if (spriteTouch(x,y,refresh)){
                game.analytics.writeEvent("Refresh lvl "+lvl_num);
                level = new Level(this.game, background_grid, red_text, red_goal, blue_text, blue_goal, green_text, green_goal, yellow_text, yellow_goal);
                level.load(lvl_num, levels);
            }

            if (spriteTouch(x,y,previous) && lvl_num != 1){
                game.analytics.writeEvent("Previous from lvl "+lvl_num);
                level = new Level(this.game, background_grid, red_text, red_goal, blue_text, blue_goal, green_text, green_goal, yellow_text, yellow_goal);
                lvl_num -=1;
                level.load(lvl_num, levels);
            }

            if (spriteTouch(x,y, next) && lvl_num != this.game.mainMenuScreen.levels_completed.size){
                game.analytics.writeEvent("Next from lvl "+lvl_num);
                level = new Level(this.game, background_grid, red_text, red_goal, blue_text, blue_goal, green_text, green_goal, yellow_text, yellow_goal);
                lvl_num += 1;
                level.load(lvl_num, levels);
            }
        }
    }

    private boolean spriteTouch(int x, int y, Sprite sprite){
        if(x >=sprite.getX() && x <= sprite.getX() + sprite.getWidth() &&
                y >= sprite.getY() && y <= sprite.getY() + sprite.getHeight() ){
            return true;
        }
        return false;
    }

    @Override
    public void hide(){
        //
    }

    @Override
    public void dispose(){
        //
    }

    @Override
    public void show(){
        //
    }

    @Override
    public void resume(){
        //
    }

    @Override
    public void pause(){
        //
    }

    @Override
    public void resize(int width, int height){

    }
}

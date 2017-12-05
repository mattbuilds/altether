package com.lutharvaughn.altether;

/**
 * Created by Matt on 12/7/14.
 */
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;
import java.util.List;

public class MainMenuScreen implements Screen {

    private BoxPuzzle game; // Note it's "MyGame" not "Game"
    private OrthographicCamera camera, fixed_cam;
    private FitViewport viewport;
    private SpriteBatch batch,  fixed_batch;
    private Texture check, texture, off;
    private Sprite  on, top_bar, back;
    private List<Sprite> sprites, checks, circles;
    private float page, time, camera_x, middle, on_x_off;// sprite_x_offset, sprite_y_offset, scale_x, scale_y;
    private int pages;
    private BitmapFont big_font, font;
    public JsonValue levels_completed;
    FileHandle level_file;
    FPSLogger fpsLogger;
    private Boolean moving = false;

    // constructor to keep a reference to the main Game class
    public MainMenuScreen(BoxPuzzle game, int game_width, int game_height){
        this.game = game;
        page = 1;
        //pages = 3;

        sprites = new ArrayList<Sprite>();
        checks = new ArrayList<Sprite>();
        circles = new ArrayList<Sprite>();

        switch (Gdx.app.getType()) {
            case WebGL:
                level_file = Gdx.files.internal("level_status.txt");
                break;
            default:
                level_file = Gdx.files.local("level_status.txt");
                break;
        }

        try{
            levels_completed = new JsonReader().parse(level_file.readString());
        } catch (Exception e){
            String text = "{1:{status:0},2:{status:0},3:{status:0},4:{status:0},5:{status:0},6:{status:0},7:{status:0},8:{status:0},9:{status:0},10:{status:0},11:{status:0},12:{status:0},13:{status:0},14:{status:0},15:{status:0},16:{status:0},17:{status:0},18:{status:0},19:{status:0},20:{status:0},21:{status:0},22:{status:0},23:{status:0},24:{status:0},25:{status:0},26:{status:0},27:{status:0},28:{status:0},29:{status:0},30:{status:0},31:{status:0},32:{status:0},33:{status:0},34:{status:0},35:{status:0},36:{status:0},37:{status:0},38:{status:0},39:{status:0},40:{status:0},41:{status:0},42:{status:0},43:{status:0},44:{status:0},45:{status:0},46:{status:0},47:{status:0},48:{status:0},49:{status:0},50:{status:0},51:{status:0},52:{status:0},53:{status:0},54:{status:0},55:{status:0},56:{status:0},57:{status:0},58:{status:0},59:{status:0},60:{status:0}}";
            switch (Gdx.app.getType()) {
                case WebGL:
                    level_file = Gdx.files.internal("level_status.txt");
                    break;
                default:
                    level_file.writeString(text, false);
                    levels_completed = new JsonReader().parse(level_file.readString());
                    break;
            }
            levels_completed = new JsonReader().parse(level_file.readString());
        }
        System.out.println(levels_completed.size);
        pages = ((levels_completed.size-1)/30)+1;

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(camera.viewportWidth/2f, camera.viewportHeight/2f, 0);
        camera.update();
        camera_x = camera.viewportWidth/2f;

        fixed_cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        fixed_cam.position.set(fixed_cam.viewportWidth / 2f, fixed_cam.viewportHeight / 2f, 0);
        fixed_cam.update();

        big_font = new BitmapFont(Gdx.files.internal(this.game.resolution + "/font.fnt"), Gdx.files.internal(this.game.resolution + "/font.png"), false);
        font = new BitmapFont(Gdx.files.internal(this.game.resolution + "/num_font.fnt"), Gdx.files.internal(this.game.resolution + "/num_font.png"), false);

        top_bar = new Sprite(new Texture("top_bar.png"));
        top_bar.setSize(camera.viewportWidth, camera.viewportWidth/10f *1.5f);
        top_bar.setPosition(0, Gdx.graphics.getHeight() - top_bar.getHeight());

        back = new Sprite(new Texture(this.game.resolution + "/back_text.png"));
        back.setPosition( back.getWidth() *.1f,camera.viewportHeight - top_bar.getHeight()/2f - back.getHeight()/2f);

        check = new Texture(this.game.resolution + "/check.png");
        check.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        batch = new SpriteBatch();
        fixed_batch = new SpriteBatch();
        texture = new Texture(this.game.resolution + "/box.png");
        for (int i = 1; i<= levels_completed.size; i++){
            Sprite adding = new Sprite(texture);
            setPos(i, adding);
        }

        off = new Texture(this.game.resolution + "/off_circle.png");
        on = new Sprite(new Texture(this.game.resolution + "/on_circle.png"));
        for (int i = 0; i<pages; i++){
            circles.add(new Sprite(off));
        }

        setCircles(circles);
        setOn();

        fpsLogger = new FPSLogger();

/*
        for (int i = 1; i<= 30; i++){
            String number_name = "box.png";
            //texture = new Texture(number_name);
            //texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            Sprite adding = new Sprite(texture);

            float width = getWidth(i-1);
            float height = getHeight(i-1);

            adding.setOrigin(0,0);
            adding.setPosition(camera.viewportHeight/2f + adding.getWidth() *width, camera.viewportHeight/2f + adding.getHeight()*height);

            sprites.add(adding);
        }
*/
        for (int i = 1; i <= levels_completed.size; i++) {
            if (levels_completed.get(Integer.toString(i)).getString("status").toString().equals("1")) {
                float sprite_x = sprites.get(i - 1).getX();
                float sprite_y = sprites.get(i - 1).getY();
                checks.add(new Sprite(check));
                checks.get(checks.size() - 1).setPosition(sprite_x, sprite_y);
            }
        }
    }

    private void setCircles(List<Sprite> sprite){
        float middle = sprite.size()/2f +1;

        for (int i=0; i<sprite.size(); i++){
            float x_off = -1f* ((middle - i - 1.5f)*2 +.5f);
            sprite.get(i).setPosition(camera.viewportWidth/2 + x_off*sprite.get(i).getWidth(), 3f * sprite.get(i).getHeight());
        }
    }

    private void setOn(){
        middle =(pages)/2f +1;
        page = (camera_x+camera.viewportWidth/2f)/camera.viewportWidth;
        on_x_off = -1f* ((middle - page - .5f)*2 +.5f);
        on.setPosition(camera.viewportWidth/2 + on_x_off*on.getWidth(), 3f * on.getHeight() );
        //on.setPosition(camera.viewportWidth/2f + );
    }

    private void setPos(int num, Sprite adding){
        float width_mult;
        float height_mult;
        int x_num, y_num, offset;

        x_num = num%5;
        if (x_num == 1){
            width_mult = adding.getWidth()*-3.5f;
        } else if (x_num == 2){
            width_mult = adding.getWidth()*-1.75f;
        } else if (x_num == 3){
            width_mult = 0f;
        } else if (x_num == 4){
            width_mult = adding.getWidth()*1.75f;
        } else {
            width_mult = adding.getWidth()*3.5f;
        }

        offset = (num-1)/30;
        width_mult = offset*camera.viewportWidth + width_mult;

        y_num = ((num-1)%30)/5;
        if (y_num == 0){
            height_mult = adding.getHeight()*4.5f;
        } else if (y_num == 1){
            height_mult = adding.getHeight()*2.75f;
        } else if (y_num == 2){
            height_mult = adding.getHeight()* 1f ;
        } else if (y_num == 3){
            height_mult = adding.getHeight() *-.75f;
        } else if (y_num == 4){
            height_mult = adding.getHeight()*-2.5f;
        } else{
            height_mult = adding.getHeight()*-4.25f;
        }


        adding.setPosition(camera.viewportWidth / 2f - adding.getWidth() / 2 + width_mult, camera.viewportHeight / 2f - adding.getHeight() / 2 + height_mult);
        sprites.add(adding);
    }

    public void updateCompletedLevel(int lvl_num){
        if (levels_completed.get(Integer.toString(lvl_num+1)).getString("status").toString().equals("0")){
                levels_completed.get(Integer.toString(lvl_num+1)).get("status").set(1);
                switch (Gdx.app.getType()) {
                    case WebGL:
                        break;
                    default:
                        level_file.writeString(levels_completed.toString(), false);
                        break;
                }
            float sprite_x = sprites.get(lvl_num).getX();
            float sprite_y = sprites.get(lvl_num).getY();
            checks.add(new Sprite(check));
            checks.get(checks.size()-1).setPosition(sprite_x, sprite_y);
        }
    }

    @Override
    public void render(float delta) {
        time = Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(71 / 255f, 81 / 255f, 93 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cameraMove();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        //box.draw(batch);
        for (int i = 0; i<sprites.size(); i++){
            sprites.get(i).draw(batch);
        }
        for (int i = 0; i<checks.size(); i++){
            checks.get(i).draw(batch);
        }
        for (int i = 0; i<sprites.size(); i++){
            font.draw(batch, Integer.toString(i+1),
                      sprites.get(i).getX()+ sprites.get(i).getWidth()/2f - font.getBounds(Integer.toString(i+1)).width/2f,
                      sprites.get(i).getY()+ sprites.get(i).getHeight()/2f + font.getBounds(Integer.toString(i+1)).height/2f);
        }

        batch.end();
        //fpsLogger.log();
        //camera.position.set(camera.position.x +1f, camera.position.y, 0);
        //camera.update();
        fixed_batch.setProjectionMatrix(fixed_cam.combined);
        fixed_batch.begin();
        for (int i=0; i<circles.size(); i++){
            circles.get(i).draw(fixed_batch);
        }
        on.draw(fixed_batch);
        top_bar.draw(fixed_batch);
        back.draw(fixed_batch);
        setOn();
        page = (camera_x+camera.viewportWidth/2f)/camera.viewportWidth;
        fixed_batch.end();
    }

    public void cameraMove(){
        if (camera_x == camera.position.x){
            moving =false;
        } else if ((int)camera_x > camera.position.x){
            camera.position.x += 1500f*time;
            if (camera.position.x > camera_x){
                forceCameraLocation();
            }
            camera.update();
        } else if ((int)camera_x < camera.position.x){
            camera.position.x -= 1500f*time;
            if (camera.position.x < camera_x){
                forceCameraLocation();
            }
            camera.update();
        }
    }

    public void forceCameraLocation(){
        camera.position.x = camera_x;
        camera.update();
        setOn();
    }

    public void move(int keycode){
         if (keycode == 21 && page < pages && moving == false){
             moving = true;
             camera_x = camera.position.x + camera.viewportWidth;
         } else if (keycode == 22 && page > 1 && moving == false){
             moving = true;
             camera_x = camera.position.x - camera.viewportWidth;

         }
    }

    public void touch(int x, int y){
        Boolean clicked = false;
        if (number_click(x - Math.round((page-1)*Gdx.graphics.getWidth()),y, back))
            game.setScreen(this.game.introScreen);

        for (int i = 0; i< sprites.size(); i++){
            clicked = number_click(x,y, sprites.get(i));
            if (clicked == true){
                //this.altether.analytics.writeEvent("Level " + i+i + " selected");
                //System.out.print(i+1);
                i = i + 1;
                game.analytics.writeEvent("Selected lvl "+ i);
                game.setGameScreen(i);
            }
        }
    }

    private boolean number_click(int x, int y, Sprite sprite){
        float update_x = x + (page-1)*camera.viewportWidth;

        if(update_x >=sprite.getX() && update_x <= sprite.getX() + sprite.getWidth() &&
           y >= sprite.getY() && y <= sprite.getY() + sprite.getHeight() ){
           return true;
        } else{
            return false;
        }
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth =  width;
        camera.viewportHeight = height;
        camera.position.set(camera.viewportWidth/2f, camera.viewportHeight/2f, 0);
        camera.update();

        fixed_cam.viewportWidth =  width;
        fixed_cam.viewportHeight = height;
        fixed_cam.position.set(fixed_cam.viewportWidth/2f, fixed_cam.viewportHeight/2f, 0);
        fixed_cam.update();
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
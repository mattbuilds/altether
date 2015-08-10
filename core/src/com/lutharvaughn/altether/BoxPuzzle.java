package com.lutharvaughn.altether;

        import com.badlogic.gdx.*;
        import com.badlogic.gdx.input.GestureDetector;
        import com.badlogic.gdx.math.Vector2;

public class BoxPuzzle extends Game implements InputProcessor, GestureDetector.GestureListener {

    public static final String TITLE = "Gravity Game";
    public static final int WIDTH = 640, HEIGHT =960;
    private int init_x=0, init_y=0;
    public String resolution;

    MainMenuScreen mainMenuScreen;
    GameScreen gameScreen;
    IntroScreen introScreen;
    MoreLevels moreLevels;
    AboutScreen aboutScreen;

    Analytics analytics;


    //@Override
    public void create () {
        analytics = new Analytics();
        analytics.writeEvent("Game Started");
        analytics.createEvent();
        resolution = getResolution(Gdx.graphics.getWidth());
        InputMultiplexer multiplexer  = new InputMultiplexer();
        multiplexer.addProcessor(new GestureDetector(this));
        multiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(multiplexer);
        Gdx.input.setCatchBackKey(true);
        //mainMenuScreen = new MainMenuScreen(this, WIDTH, HEIGHT);
        introScreen = new IntroScreen(this, WIDTH, HEIGHT);
        gameScreen = new GameScreen(this, WIDTH, HEIGHT);
        mainMenuScreen = new MainMenuScreen(this, WIDTH, HEIGHT);
        moreLevels = new MoreLevels(this, WIDTH, HEIGHT);
        aboutScreen = new AboutScreen(this, WIDTH, HEIGHT);
        setScreen(introScreen);
    }

    public String getResolution(int res){
        if (res >=  1440){
            res = 1440;
        }else if (res >= 1280){
            res = 1280;
        }else if (res >= 960){
            res = 960;
        }else if (res >=720){
            res = 720;
        }else if (res >= 640){
            res = 640;
        }else{
            res = 480;
        }
        return Integer.toString(res);
    }

    public void setGameScreen(int lvl){
        gameScreen.load_level(lvl);
        setScreen(gameScreen);
    }

    public void setMenu(){
        setScreen(mainMenuScreen);
        mainMenuScreen.forceCameraLocation();
    }

    public void completedLevel(int lvl_num){
        int i = lvl_num + 1;
        analytics.writeEvent("Completed Lvl " + i);
        mainMenuScreen.updateCompletedLevel(lvl_num);
    }

    // Input Processor Functions
    @Override
    public boolean keyDown (int keycode) {
        if (keycode == Input.Keys.BACK){
            if (getScreen() == gameScreen){
                setScreen(mainMenuScreen);
                return false;
            }

            if (getScreen() == mainMenuScreen || getScreen() == moreLevels || getScreen() == aboutScreen){
                setScreen(introScreen);
                return  false;
            }

            if (getScreen() == introScreen){
                Gdx.app.exit();
            }
        }

        if (getScreen() == gameScreen){
            gameScreen.move(keycode);
        }

        if (getScreen() == mainMenuScreen){
            if(keycode == 21){
                keycode = 22;
            } else if(keycode == 22){
                keycode = 21;
            }
            mainMenuScreen.move(keycode);
        }
        return false;
    }

    @Override
    public boolean keyUp (int keycode) { return false; }

    @Override
    public boolean keyTyped (char character) {
        return false;
    }

    @Override
    public boolean touchDown (int x, int y, int pointer, int button) {
        init_x = x;
        init_y = y;
        return false;
    }

    @Override
    public boolean touchUp (int x, int y, int pointer, int button) {
        if ((Math.abs(init_x - x) > 30) || (Math.abs(init_y - y) > 30)){
            return false;
        }

        if (mainMenuScreen == getScreen()){
            y = Gdx.graphics.getHeight() - y;
            mainMenuScreen.touch(x,y);
            return false;
        }

        if (gameScreen == getScreen()){
            y = Gdx.graphics.getHeight() - y;
            gameScreen.touch(x,y);
            return false;
        }

        if (introScreen == getScreen()){
            y = Gdx.graphics.getHeight() - y;
            introScreen.touch(x,y);
            return false;
        }

        if (moreLevels == getScreen()){
            y = Gdx.graphics.getHeight() - y;
            moreLevels.touch(x,y);
            return false;
        }

        if (aboutScreen == getScreen()){
            y = Gdx.graphics.getHeight() - y;
            aboutScreen.touch(x,y);
            return false;
        }

        return false;
    }

    @Override
    public boolean touchDragged (int x, int y, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved (int x, int y) {
        return false;
    }

    @Override
    public boolean scrolled (int amount) {
        return false;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {

        return false;
    }

    @Override
    public boolean longPress(float x, float y) {

        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
            int keycode;
            if (Math.abs(velocityX)-100f > Math.abs(velocityY)){
                if (velocityX > 0){
                    keycode = 22;
                } else{
                    keycode = 21;
                }
            } else{
                if (velocityY > 0){
                    keycode = 20;
                } else{
                    keycode = 19;
                }
            }

        if (getScreen() == gameScreen){
            gameScreen.move(keycode);
        } else if (getScreen() == mainMenuScreen){
            mainMenuScreen.move(keycode);
        }
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {

        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {

        return false;
    }

    @Override
    public boolean zoom (float originalDistance, float currentDistance){

        return false;
    }

    @Override
    public boolean pinch (Vector2 initialFirstPointer, Vector2 initialSecondPointer, Vector2 firstPointer, Vector2 secondPointer){
        return false;
    }
}

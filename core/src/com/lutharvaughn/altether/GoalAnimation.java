package com.lutharvaughn.altether;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Matt on 8/21/15.
 */
public class GoalAnimation {
    private Texture red_animation_text, blue_animation_text, green_animation_text, yellow_animation_text;
    private Animation red_animation, blue_animation, green_animation, yellow_animation;
    private TextureRegion[] red_animation_text_reg, blue_animation_text_reg, green_animation_text_reg, yellow_animation_text_reg;
    private TextureRegion currentRedFrame, currentBlueFrame, currentGreenFrame, currentYellowFrame;
    private float redStartTime, blueStartTime, greenStartTime, yellowStartTime, img_width;
    private SpriteBatch batch;
    public boolean redDraw, blueDraw, yellowDraw, greenDraw;
    int red_x, red_y, blue_x, blue_y, yellow_x, yellow_y, green_x, green_y;
    private BoxPuzzle game;

    public GoalAnimation(BoxPuzzle game, SpriteBatch batch){
        this.game = game;
        this.batch = batch;
        redDraw = false;
        blueDraw = false;
        yellowDraw = false;
        greenDraw = false;

        red_animation_text = new Texture(game.resolution + "/red_animation.png");
        TextureRegion[][] tmp = TextureRegion.split(red_animation_text, red_animation_text.getWidth()/4, red_animation_text.getHeight()/2);
        red_animation_text_reg = new TextureRegion[8];
        int index = 0;
        for (int i=0; i < 2; i++){
            for (int j=0; j < 4; j++){
                red_animation_text_reg[index++] = tmp[i][j];
            }
        }
        red_animation = new Animation(.06f, red_animation_text_reg);

        blue_animation_text = new Texture(game.resolution + "/blue_animation.png");
        TextureRegion[][] tmp2 = TextureRegion.split(blue_animation_text, blue_animation_text.getWidth()/4, blue_animation_text.getHeight()/2);
        blue_animation_text_reg = new TextureRegion[8];
        int index2 = 0;
        for (int i=0; i < 2; i++){
            for (int j=0; j < 4; j++){
                blue_animation_text_reg[index2++] = tmp2[i][j];
            }
        }
        blue_animation = new Animation(.06f, blue_animation_text_reg);

        green_animation_text = new Texture(game.resolution + "/green_animation.png");
        TextureRegion[][] tmp3 = TextureRegion.split(green_animation_text, green_animation_text.getWidth()/4, green_animation_text.getHeight()/2);
        green_animation_text_reg = new TextureRegion[8];
        int index3 = 0;
        for (int i=0; i < 2; i++){
            for (int j=0; j < 4; j++){
                green_animation_text_reg[index3++] = tmp3[i][j];
            }
        }
        green_animation = new Animation(.06f, green_animation_text_reg);

        yellow_animation_text = new Texture(game.resolution + "/yellow_animation.png");
        TextureRegion[][] tmp4 = TextureRegion.split(yellow_animation_text, yellow_animation_text.getWidth()/4, yellow_animation_text.getHeight()/2);
        yellow_animation_text_reg = new TextureRegion[8];
        int index4 = 0;
        for (int i=0; i < 2; i++){
            for (int j=0; j < 4; j++){
                yellow_animation_text_reg[index4++] = tmp4[i][j];
            }
        }
        yellow_animation = new Animation(.06f, yellow_animation_text_reg);

        redStartTime = 0f;
        blueStartTime = 0f;
        yellowStartTime = 0f;
        greenStartTime = 0f;
        red_x = 0;
        red_y = 0;
        blue_x = 0;
        blue_y = 0;
        yellow_x = 0;
        yellow_y = 0;
        green_x = 0;
        green_y = 0;
    }

    public void setDraw(String color, float width, int x, int y) {
        this.img_width = width;
        if (color.equalsIgnoreCase("red")){
            redDraw = true;
            red_x = x;
            red_y = y;
        }

        if (color.equalsIgnoreCase("blue")){
            blueDraw = true;
            blue_x = x;
            blue_y = y;
        }

        if (color.equalsIgnoreCase("yellow")){
            yellowDraw = true;
            yellow_x = x;
            yellow_y = y;
        }

        if (color.equalsIgnoreCase("green")){
            greenDraw = true;
            green_x = x;
            green_y = y;
        }
    }

    public void animate(float x_offset, float y_offset){
        if (redDraw == true){
            redStartTime += Gdx.graphics.getDeltaTime();
            if(red_animation.isAnimationFinished(redStartTime) == false)
               currentRedFrame = red_animation.getKeyFrame(redStartTime, true);
            batch.draw(currentRedFrame, red_x*(img_width/64f)+x_offset, red_y*(img_width/64f)+y_offset);
        }

        if (blueDraw == true){
            blueStartTime += Gdx.graphics.getDeltaTime();
            if(blue_animation.isAnimationFinished(blueStartTime) == false)
                currentBlueFrame = blue_animation.getKeyFrame(blueStartTime, true);
            batch.draw(currentBlueFrame, blue_x*(img_width/64f)+x_offset, blue_y*(img_width/64f)+y_offset);
        }

        if (greenDraw == true){
            greenStartTime += Gdx.graphics.getDeltaTime();
            if(green_animation.isAnimationFinished(greenStartTime) == false)
                currentGreenFrame = green_animation.getKeyFrame(greenStartTime, true);
            batch.draw(currentGreenFrame, green_x*(img_width/64f)+x_offset, green_y*(img_width/64f)+y_offset);
        }

        if (yellowDraw == true){
            yellowStartTime += Gdx.graphics.getDeltaTime();
            if(yellow_animation.isAnimationFinished(yellowStartTime) == false)
                currentYellowFrame = yellow_animation.getKeyFrame(yellowStartTime, true);
            batch.draw(currentYellowFrame, yellow_x*(img_width/64f)+x_offset, yellow_y*(img_width/64f)+y_offset);
        }
    }

    public void clearAnimation(){
        redDraw = false;
        redStartTime = 0f;
        blueDraw = false;
        blueStartTime = 0f;
        yellowDraw = false;
        yellowStartTime = 0f;
        greenDraw = false;
        greenStartTime = 0f;
    }
}

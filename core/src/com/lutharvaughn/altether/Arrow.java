package com.lutharvaughn.altether;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Matt on 10/4/14.
 */
public class Arrow {
    private int rotation;
    private Texture arrow_img;

    public Arrow(){
        rotation = 180;
        arrow_img = new Texture("arrow.jpg");
    }

    public void drawArrow(SpriteBatch batch){
        batch.draw(arrow_img, 256, 256, 128, 128, 256, 256, 1, 1, rotation, 0, 0, 256, 256, false, false);
    }

    public void setDirection(int keycode){
        if (keycode == 19){
            rotation = 0;
        }
        if (keycode == 20){
            rotation = 180;
        }
        if (keycode == 21){
            rotation = 90;
        }
        if (keycode == 22){
            rotation = 270;
        }
    }
}

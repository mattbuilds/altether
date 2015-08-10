package com.lutharvaughn.altether;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * Created by Matt on 10/3/14.
 */
public class Wall {

    public int x, y;

    public Wall(int set_x, int set_y){
        x = set_x;
        y = set_y;
    }

    public void draw(Batch batch, Texture img){
        batch.draw(img, x, y);
    }
}

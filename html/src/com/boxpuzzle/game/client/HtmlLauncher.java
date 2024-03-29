package com.boxpuzzle.game.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.boxpuzzle.game.BoxPuzzle;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(480, 640);
        }

        @Override
        public ApplicationListener getApplicationListener () {
                return new BoxPuzzle();
        }
}
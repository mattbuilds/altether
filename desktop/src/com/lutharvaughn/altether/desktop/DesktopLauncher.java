package com.lutharvaughn.altether.desktop;

        import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
        import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
        import com.lutharvaughn.altether.BoxPuzzle;

public class DesktopLauncher {
    public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 480;
        config.height = 700;
        new LwjglApplication(new BoxPuzzle(), config);
    }

}
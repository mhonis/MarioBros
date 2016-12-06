package com.mhonis.mariobros;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mhonis.mariobros.screens.PlayScreen;

/**
 * known issues:
 * friction decreases Mario's vertical velocity
 */

public class MarioBros extends Game {

    public static final int V_WIDTH = 400;
    public static final int V_HEIGHT = 208;
    public static final float PPM = 100; //pixels per meter

    public static final short GROUND_BIT = 1;
    public static final short MARIO_BIT = 2;
    public static final short BRICK_BIT = 4;
    public static final short COIN_BIT = 8;
    public static final short DESTROYED_BIT = 16;
    public static final short OBJECT_BIT = 32;
    public static final short ENEMY_BIT = 64;
    public static final short ENEMY_HEAD_BIT = 128;
    public static final short MARIO_HEAD_BIT = 256;

    public SpriteBatch batch;

    //TODO do not use static AssetManager, it can cause problems, especially on android. Pass it around to the classes that need it instead.
    public static AssetManager manager;

    @Override
    public void create() {
        batch = new SpriteBatch();
        manager = new AssetManager();
        manager.load("audio/music/mario_music.ogg", Music.class);
        manager.load("audio/sounds/bump.wav", Sound.class);
        manager.load("audio/sounds/coin.wav", Sound.class);
        manager.load("audio/sounds/breakblock.wav", Sound.class);
        manager.finishLoading(); //synchronous loading
        setScreen(new PlayScreen(this));
    }

    @Override
    public void render() {
        super.render();
        if (manager.update()) { //returns a boolean that tells whether all assets are loaded
            //this would be used for asynchronous loading
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        manager.dispose();
    }
}

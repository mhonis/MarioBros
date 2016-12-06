package com.mhonis.mariobros.sprites.tile_objects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;
import com.mhonis.mariobros.MarioBros;
import com.mhonis.mariobros.scenes.Hud;
import com.mhonis.mariobros.screens.PlayScreen;

/**
 * Created by mhonis on 4.11.2016.
 */

public class Brick extends InteractiveTileObject {

    private final Sound breakSound;

    public Brick(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds);
        fixture.setUserData(this);
        setCathegoryFilter(MarioBros.BRICK_BIT);
        breakSound = MarioBros.manager.get("audio/sounds/breakblock.wav");
    }

    @Override
    public void onHeadHit() {
        setCathegoryFilter(MarioBros.DESTROYED_BIT);
        getCell().setTile(null);
        Hud.addScore(200);
        breakSound.play();
    }
}

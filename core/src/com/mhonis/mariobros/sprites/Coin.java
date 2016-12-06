package com.mhonis.mariobros.sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.mhonis.mariobros.MarioBros;
import com.mhonis.mariobros.scenes.Hud;

/**
 * Created by mhonis on 4.11.2016.
 */

public class Coin extends InteractiveTileObject {

    private static TiledMapTileSet tileSet;
    private final int BLANK_COIN = 28;
    private final Sound coinSound;
    private final Sound bumpSound;

    public Coin(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        tileSet = map.getTileSets().getTileSet("tileset_gutter");
        fixture.setUserData(this);
        setCathegoryFilter(MarioBros.COIN_BIT);
        coinSound = MarioBros.manager.get("audio/sounds/coin.wav");
        bumpSound = MarioBros.manager.get("audio/sounds/bump.wav");
    }

    @Override
    public void onHeadHit() {
        if (getCell().getTile().getId() != BLANK_COIN) {
            Hud.addScore(100);
            coinSound.play();
        } else {
            bumpSound.play();
        }
        getCell().setTile(tileSet.getTile(BLANK_COIN));
    }
}

package com.mhonis.mariobros.sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.mhonis.mariobros.MarioBros;

/**
 * Created by mhonis on 4.11.2016.
 */

public class Coin extends InteractiveTileObject {

    public Coin(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        fixture.setUserData(this);
        setCathegoryFilter(MarioBros.COIN_BIT);
    }

    @Override
    public void onHeadHit() {
        System.out.println("head hit a coin");
    }
}

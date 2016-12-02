package com.mhonis.mariobros.sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.mhonis.mariobros.MarioBros;
import com.mhonis.mariobros.scenes.Hud;

/**
 * Created by mhonis on 4.11.2016.
 */

public class Brick extends InteractiveTileObject {

    public Brick(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        fixture.setUserData(this);
        setCathegoryFilter(MarioBros.BRICK_BIT);
    }

    @Override
    public void onHeadHit() {
        System.out.println("head hit a brick");
        setCathegoryFilter(MarioBros.DESTROYED_BIT);
        getCell().setTile(null);
        Hud.addScore(200);
    }
}

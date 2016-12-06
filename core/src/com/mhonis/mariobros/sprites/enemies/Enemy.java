package com.mhonis.mariobros.sprites.enemies;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mhonis.mariobros.screens.PlayScreen;

/**
 * Created by mhonis on 6.12.2016.
 */

public abstract class Enemy extends Sprite {

    protected final World world;
    protected final PlayScreen screen;
    public Body b2body;

    public Enemy(PlayScreen screen, float x, float y) {
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x, y);
        defineEnemy();
    }

    protected abstract void defineEnemy();

    public abstract void hitOnHead();

    public abstract void reverseVelocity(boolean x, boolean y);

    public abstract void update(float dt);
}

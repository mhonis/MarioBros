package com.mhonis.mariobros.tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mhonis.mariobros.sprites.InteractiveTileObject;

/**
 * Created by mhonis on 30.11.2016.
 */

public class WorldContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();
        if (fixA.getUserData() != null && fixB.getUserData() != null)
            if (fixA.getUserData().equals("head") || fixB.getUserData().equals("head")) {
                Fixture head = fixA.getUserData().equals("head") ? fixA : fixB;
                Fixture object = head == fixA ? fixB : fixA;

                if (object.getUserData() instanceof InteractiveTileObject) {
                    ((InteractiveTileObject) object.getUserData()).onHeadHit();
                }
            }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}

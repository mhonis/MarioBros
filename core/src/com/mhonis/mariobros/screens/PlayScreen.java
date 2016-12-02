package com.mhonis.mariobros.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mhonis.mariobros.MarioBros;
import com.mhonis.mariobros.scenes.Hud;
import com.mhonis.mariobros.sprites.Mario;
import com.mhonis.mariobros.tools.B2WorldCreator;
import com.mhonis.mariobros.tools.WorldContactListener;

/**
 * Created by mhonis on 4.11.2016.
 */

public class PlayScreen implements Screen {

    private MarioBros game;
    private OrthographicCamera gameCam;
    private Hud hud;
    private Viewport gamePort;

    private float camBoundRight;

    //Tiled map variables
    TmxMapLoader mapLoader;
    TiledMap map;
    OrthogonalTiledMapRenderer renderer;

    //Box2D variables
    private World world;
    private Box2DDebugRenderer b2dr;

    Mario player;
    private TextureAtlas textureAtlas;


    public PlayScreen(MarioBros game) {
        textureAtlas = new TextureAtlas("MarioAndEnemies.pack");
        this.game = game;
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(MarioBros.V_WIDTH / MarioBros.PPM, MarioBros.V_HEIGHT / MarioBros.PPM, gameCam);
        hud = new Hud(game.batch);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("level_1_map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / MarioBros.PPM);
        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, -10), true); //parameters: gravity; sleep (do you want to sleep objects that are at rest)
        b2dr = new Box2DDebugRenderer();

        new B2WorldCreator(world, map);

        player = new Mario(world, this);
        camBoundRight = (16 * map.getProperties().get("width", Integer.class) / MarioBros.PPM);

        world.setContactListener(new WorldContactListener());
    }

    public TextureAtlas getTextureAtlas() {
        return textureAtlas;
    }

    @Override
    public void show() {
    }

    public void handleInput(float dt) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && player.b2body.getLinearVelocity().y == 0) {
            //2 ways to jump - impulse (immediate change) & force (gradual change) - player jumps impulsively
            player.b2body.applyLinearImpulse(new Vector2(0, 4F), player.b2body.getWorldCenter(), true); //4 works best
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2) {
            player.b2body.applyLinearImpulse(new Vector2(0.1F, 0), player.b2body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2) {
            player.b2body.applyLinearImpulse(new Vector2(-0.1F, 0), player.b2body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
        }
    }

    public void update(float dt) {
        //handle user input first
        handleInput(dt);

        //tick the world
        world.step(1 / 60F, 6, 2);

        player.update(dt);
        hud.update(dt);

        //track Mario with camera (only within the range of the map)
        if ((player.b2body.getPosition().x > gameCam.viewportWidth / 2) && (player.b2body.getPosition().x < (camBoundRight - (gameCam.viewportWidth / 2))))
            gameCam.position.x = player.b2body.getPosition().x;

        //update the camera and view
        gameCam.update();
        renderer.setView(gameCam);
    }

    @Override
    public void render(float delta) {
        //separate the game logic from renderer
        update(delta);

        //clear the screen with black color
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //render the game map
        renderer.render();

        //render the Box2d debug lines
        b2dr.render(world, gameCam.combined);

        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();
        player.draw(game.batch);
        game.batch.end();

        //set the batch to render what the camera sees & HUD
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        renderer.dispose();
        map.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }
}

/*
 * Copyright (c) 2012, 2013 Hemanta Sapkota.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Hemanta Sapkota (laex.pearl@gmail.com)
 * http://hsapkota.com.au
 */
package com.laex;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

/**
 * The Class MyGdxGame.
 */
public class MyGdxGame extends ApplicationAdapter {

    /** The debug draw. */
    private Box2DDebugRenderer debugDraw;

    /** The world. */
    private World world;

    /** The cam. */
    private OrthographicCamera cam;

    /** The ground. */
    private Body ground;

    /** The joint. */
    private Joint joint;

    /** The script manager. */
    private LuaScriptManager scriptManager;

    /** The body. */
    private Body body;

    /*
     * (non-Javadoc)
     * 
     * @see com.badlogic.gdx.ApplicationAdapter#create()
     */
    @Override
    public void create() {
        // Set up world and debug draw
        Vector2 gravity = new Vector2(0, -9);
        world = new World(gravity, true);
        debugDraw = new Box2DDebugRenderer();

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        // set up orthographic camera
        cam = new OrthographicCamera(w / 10, h / 10);
        cam.position.set(cam.viewportWidth / 2, cam.viewportHeight / 2, 0);

        // Our LuaScriptManager. We'll talk about this in a while.
        scriptManager = new LuaScriptManager(
                world,
                cam,
                "scripts/tumbler.lua");

        // Create Box2D physics objcets
        createBodies();

        // Call script manager init(). This method is only called once for each script.
        scriptManager.executeInit();
    }

    /**
     * Creates the bodies.
     */
    private void createBodies() {
        // this part of box2d code is taken from Tumbler test from Box2D Source
        {
            BodyDef bodyDef = new BodyDef();
            ground = world.createBody(bodyDef);
        }

        {
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyType.DynamicBody;
            bodyDef.allowSleep = true;
            bodyDef.position.set(0.0f, 0.0f);
            body = world.createBody(bodyDef);

            PolygonShape shape = new PolygonShape();

            shape.setAsBox(0.5f, 10.0f, new Vector2(5.0f, 0.0f), 0.0f);
            body.createFixture(shape, 5.0f);

            shape.setAsBox(0.5f, 10.0f, new Vector2(-5.0f, 0.0f), 0.0f);
            body.createFixture(shape, 5.0f);

            shape.setAsBox(10.0f, 0.5f, new Vector2(0.0f, 5.0f), 0.0f);
            body.createFixture(shape, 5.0f);

            shape.setAsBox(10.0f, 0.5f, new Vector2(0.0f, -5.0f), 0.0f);
            body.createFixture(shape, 5.0f);

            RevoluteJointDef rjd = new RevoluteJointDef();
            rjd.bodyA = ground;
            rjd.bodyB = body;
            rjd.localAnchorA.set(0.0f, 10.0f);
            rjd.localAnchorB.set(0.0f, 0.0f);
            rjd.referenceAngle = 0.0f;
            rjd.motorSpeed = 0.05f * MathUtils.PI;
            rjd.maxMotorTorque = 1e8f;
            rjd.enableMotor = true;
            joint = world.createJoint(rjd);

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.badlogic.gdx.ApplicationAdapter#render()
     */
    @Override
    public void render() {
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        cam.update();
        cam.apply(Gdx.graphics.getGL10());

        debugDraw.render(world, cam.combined);
        world.step(1 / 60f, 100, 100);

        if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            scriptManager.executeKeyPressed("Right", body, joint);
        } else if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            scriptManager.executeKeyPressed("Left", body, joint);
        } else if (Gdx.input.isKeyPressed(Keys.A)) {
            scriptManager.executeKeyPressed("A", body, joint);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.badlogic.gdx.ApplicationAdapter#dispose()
     */
    @Override
    public void dispose() {
        super.dispose();
    }

}

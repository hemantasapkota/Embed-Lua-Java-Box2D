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

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;

/**
 * The Class LuaScriptManager.
 */
public class LuaScriptManager implements IScript {

    /** The globals. */
    private Globals globals = JsePlatform.standardGlobals();
    
    /** The chunk. */
    private LuaValue chunk;
    
    /** The script file exists. */
    private boolean scriptFileExists;
    
    /** The world. */
    private World world;
    
    /** The cam. */
    private Camera cam;

    /**
     * Instantiates a new lua script manager.
     *
     * @param world the world
     * @param cam the cam
     * @param scriptFileName the script file name
     */
    public LuaScriptManager(World world, Camera cam, String scriptFileName) {
        this.world = world;
        this.cam = cam;

        if (!Gdx.files.internal(scriptFileName).exists()) {
            scriptFileExists = false;
            return;
        } else {
            scriptFileExists = true;
        }

        chunk = globals.loadFile(scriptFileName);

        // very important step. subsequent calls to script method do not work if the
        // chunk
        // is not called here
        chunk.call();

    }

    /* (non-Javadoc)
     * @see com.laex.IScript#canExecute()
     */
    @Override
    public boolean canExecute() {
        return scriptFileExists;
    }

    /* (non-Javadoc)
     * @see com.laex.IScript#executeInit()
     */
    @Override
    public void executeInit() {
        if (!canExecute()) {
            return;
        }
        
        globals.get("init").invoke(
                new LuaValue[] { CoerceJavaToLua.coerce(world),
                        CoerceJavaToLua.coerce(cam) });
    }

    /* (non-Javadoc)
     * @see com.laex.IScript#executeKeyPressed(java.lang.String, com.badlogic.gdx.physics.box2d.Body, com.badlogic.gdx.physics.box2d.Joint)
     */
    @Override
    public void executeKeyPressed(String key, Body body, Joint joint) {
        if (!canExecute()) {
            return;
        }
        
        globals.get("keyPressed").invoke(
                new LuaValue[] { CoerceJavaToLua.coerce(world),
                        CoerceJavaToLua.coerce(cam),
                        CoerceJavaToLua.coerce(body),
                        CoerceJavaToLua.coerce(joint), LuaValue.valueOf(key) });
    }

}

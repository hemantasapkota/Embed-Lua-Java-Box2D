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

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.utils.GdxNativesLoader;

/**
 * The Class MyGdxGameDesktop.
 */
public class MyGdxGameDesktop {

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        LwjglApplicationConfiguration lwapp = new LwjglApplicationConfiguration();
        lwapp.width = 480;
        lwapp.height = 320;
        lwapp.title = "Embed-Lua-Java-Tutorial";
        
        GdxNativesLoader.load();
        
        new LwjglApplication(new MyGdxGame(), lwapp);
    }

}

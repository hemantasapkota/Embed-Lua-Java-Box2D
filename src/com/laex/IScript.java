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

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;


/**
 * The Interface IScript.
 */
public interface IScript {
	
	  /**
  	 * Can execute.
  	 *
  	 * @return true, if successful
  	 */
  	boolean canExecute();
	  
	  /**
  	 * Execute init.
  	 */
  	void executeInit();
	  
	  /**
  	 * Execute key pressed.
  	 *
  	 * @param key the key
  	 * @param body the body
  	 * @param joint the joint
  	 */
  	void executeKeyPressed(String key, Body body, Joint joint);

}

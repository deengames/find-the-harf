/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.deengames.findtheharf;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.deengames.findtheharf.model.Constants;
import com.deengames.findtheharf.screens.LoadingScreen;
import com.deengames.radiantwrench.controller.Game;
import com.deengames.radiantwrench.utils.PersistentStorage;
import com.deengames.radiantwrench.view.Text;


public class FindTheHarfGame extends Game implements ApplicationListener {
	
	public void create() {
		PersistentStorage.setPreferenceFile(Constants.PREF_FILE_NAME);
		Text.setDefaultColour("black");
		Game.showScreen(new LoadingScreen());
		super.create();
	}
}

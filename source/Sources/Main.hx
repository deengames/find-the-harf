package;

import kha.System;
import scenes.SplashScene;

class Main {
	
	public static inline var GAME_WIDTH = 576;
	public static inline var GAME_HEIGHT = 1024;

	public static function main() {
		// index.html doesn't use these properties. You can set them by hand
		// (Kha won't overwrite them when you rebuild the html5 target).
		System.init({ title: "Find the Harf", width: GAME_WIDTH, height: GAME_HEIGHT }, function () {
			new SplashScene();
		});
	}
}

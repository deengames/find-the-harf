// Adapted from http://gamepopper.co.uk/2014/08/26/haxeflixel-making-a-custom-preloader/
// Pre-loads assets. Used for Android and Flash.
package quasar.states;

import flash.Lib;
import flash.display.Bitmap;
import flash.display.BitmapData;
import flixel.system.FlxPreloader;
import openfl.display.Sprite;

@:bitmap("assets/images/preloader-logo.png") class LogoImage extends BitmapData { }

class Preloader extends FlxPreloader
{
	var logo:Sprite = new Sprite();
	private static inline var FADE_FROM_PERCENT = 0.75;

	public function new(minDisplayTime:Float = 0, ?allowedUrls:Array<String>)
	{
		super(minDisplayTime, allowedUrls);
	}

	override private function create():Void
	{
		this._width = Lib.current.stage.stageWidth;
		this._height = Lib.current.stage.stageHeight;

		var bitmap = new Bitmap(new LogoImage(0,0));
		// Rotate around origin
		bitmap.x = -bitmap.width / 2;
		bitmap.y = -bitmap.height / 2;

		//Sets the graphic of the sprite to a Bitmap object,
		// which uses our embedded BitmapData class.
		logo.addChild(bitmap);
		logo.x = (this._width - logo.width) / 2;
		logo.y = (this._height - logo.height) / 2;

		// account for negative-coordinates origin
		logo.x += -bitmap.x;
		logo.y += -bitmap.y;
		addChild(logo); //Adds the graphic to the preloader's buffer.
	}

	override public function update(percent:Float):Void
	{
        trace("pre");
        
		// 60 FPS, two rotations per second
		logo.rotation += 12;
		// 0-90% = 1.0 alpha
		// 91-100% = 1.0 => 0
		if (percent >= FADE_FROM_PERCENT) {
			logo.alpha = 1 - (percent - FADE_FROM_PERCENT) * (1 / (1 - FADE_FROM_PERCENT));
		}
	}
}

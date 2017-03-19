// Adapted from http://gamepopper.co.uk/2014/08/26/haxeflixel-making-a-custom-preloader/
// Pre-loads assets. Used for Android and Flash.
package deengames;

import flash.Lib;
import flash.display.Bitmap;
import flash.display.BitmapData;
import flixel.system.FlxPreloader;
import openfl.display.Sprite;

@:bitmap("assets/images/preloader-logo.png")
class GraphicLogo extends BitmapData { }

class Preloader extends FlxPreloader
{
	private static inline var LOGO_WIDTH = 411;
	private static inline var LOGO_HEIGHT = 512;
	private static inline var FADE_FROM_PERCENT = 0.75;
	private static inline var ROTATIONS_PER_SECOND = 2;
	
	private var degreesToRotatePerUpdate:Int;

	var logo:Sprite = new Sprite();

	public function new(minDisplayTime:Float = 0.25, ?allowedUrls:Array<String>)
	{
		super(minDisplayTime, allowedUrls);
		degreesToRotatePerUpdate = Std.int(360 * ROTATIONS_PER_SECOND / 60); // 60FPS
		trace(degreesToRotatePerUpdate);
	}

	override private function create():Void
	{
		this._width = Lib.current.stage.stageWidth;
		this._height = Lib.current.stage.stageHeight;

		var bitmap = new Bitmap(new GraphicLogo(LOGO_WIDTH, LOGO_HEIGHT));

		// Rotate around origin
		bitmap.x = -LOGO_WIDTH / 2;
		bitmap.y = -LOGO_HEIGHT / 2;

		//Sets the graphic of the sprite to a Bitmap object,
		// which uses our embedded BitmapData class.
		logo.addChild(bitmap);
		logo.x = (this._width - LOGO_WIDTH) / 2;
		logo.y = (this._height - LOGO_HEIGHT) / 2;

		// account for negative-coordinates origin
		logo.x += -bitmap.x;
		logo.y += -bitmap.y;
		addChild(logo); //Adds the graphic to the preloader's buffer.
	}

	override public function update(percent:Float):Void
	{
		// 60 FPS, two rotations per second. This is degrees.
		logo.rotation += degreesToRotatePerUpdate;
		// 0-90% = 1.0 alpha
		// 91-100% = 1.0 => 0
		if (percent >= FADE_FROM_PERCENT)
		{
			logo.alpha = 1 - (percent - FADE_FROM_PERCENT) * (1 / (1 - FADE_FROM_PERCENT));
		}
	}
}

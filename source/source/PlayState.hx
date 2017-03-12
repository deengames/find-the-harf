package;

import flixel.FlxG;
import flixel.FlxSprite;
import flixel.text.FlxText;
import flixel.ui.FlxButton;
import flixel.math.FlxMath;
import quasar.core.QuasarState;

class PlayState extends QuasarState
{
	private var letters:Array<String> = ["alif", "ba", "ta", "tha", "jeem", "7a", "kha",
		"daal", "thaal", "ra", "za", "seen", "sheen", "saad",
		"daad", "taw", "thaw", "ayn", "ghayn", "fa",
		"qaaf", "kaaf", "laam", "meem", "noon", "ha", "waw", "ya"];

	private static inline var LETTERS_ACROSS = 4;
	private static inline var LETTERS_DOWN = 7;
	private static inline var LETTER_SIZE:Int = 128;

	override public function create():Void
	{
		super.create();

		var groupXOffset = (this.width - (LETTERS_ACROSS * LETTER_SIZE)) / 2;
		var groupYOffset = (this.height - (LETTERS_DOWN * LETTER_SIZE)) / 2;
		trace('${groupXOffset} = ${this.width} - (4*${LETTER_SIZE}) / 2');

		for (i in 0...letters.length) {
			var letter = letters[i];
			var sprite = this.addSprite('assets/images/letters/${letter}.png');
			sprite.setGraphicSize(LETTER_SIZE, LETTER_SIZE);
			sprite.updateHitbox();
			sprite.x = ((LETTERS_ACROSS - (i % LETTERS_ACROSS)) * sprite.width) - sprite.width + groupXOffset;
			sprite.y = (Math.floor(i / LETTERS_ACROSS) * sprite.height) + groupYOffset;	
		}
	}

	override public function update(elapsed:Float):Void
	{
		super.update(elapsed);
	}
}

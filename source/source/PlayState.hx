package;

import flixel.FlxG;
import flixel.text.FlxText;
import flixel.ui.FlxButton;
import flixel.math.FlxMath;
import flixel.math.FlxRandom;
import quasar.core.QuasarState;
import quasar.core.QuasarSprite;

class PlayState extends QuasarState
{
	private static inline var LETTERS_ACROSS = 4;
	private static inline var LETTERS_DOWN = 7;
	private static inline var LETTER_SIZE:Int = 128;
	private static inline var JUMBO_LETTER_SIZE = 512;
	private static inline var WHITEOUT_ALPHA:Float = 0.75;

	private var letters:Array<String> = ["alif", "ba", "ta", "tha", "jeem", "7a", "kha",
		"daal", "thaal", "ra", "za", "seen", "sheen", "saad",
		"daad", "taw", "thaw", "ayn", "ghayn", "fa",
		"qaaf", "kaaf", "laam", "meem", "noon", "ha", "waw", "ya"];

	private var random:FlxRandom = new FlxRandom();
	private var currentTarget:String;
	private var whiteout:QuasarSprite;
	private var jumboLetter:QuasarSprite;

	override public function create():Void
	{
		super.create();

		this.addSprite("assets/images/background.jpg");

		var groupXOffset = (this.width - (LETTERS_ACROSS * LETTER_SIZE)) / 2;
		var groupYOffset = (this.height - (LETTERS_DOWN * LETTER_SIZE)) / 2;

		for (i in 0...letters.length)
		{
			var letter = letters[i];
			var sprite = this.addSprite('assets/images/letters/${letter}.png');
			sprite.onMouseClick(function() { 
				if (letter == currentTarget)
				{
					selectAndDisplayNewTarget();
				}
			}, false); // Use bounding-box for clicks
			sprite.scaleTo(LETTER_SIZE, LETTER_SIZE);
			sprite.x = ((i % LETTERS_ACROSS) * sprite.width) + groupXOffset;
			sprite.y = (Math.floor(i / LETTERS_ACROSS) * sprite.height) + groupYOffset;
		}

		this.whiteout = this.addSprite("assets/images/whiteout.jpg");

		this.selectAndDisplayNewTarget();
	}

	override public function update(elapsed:Float):Void
	{
		super.update(elapsed);

		this.whiteout.update(elapsed);
		this.jumboLetter.update(elapsed);

		if (this.whiteout.alpha > WHITEOUT_ALPHA) {
			this.whiteout.alpha = WHITEOUT_ALPHA;
			this.whiteout.alphaVelocity = 0;			
			this.jumboLetter.alpha = 1;
			this.jumboLetter.alphaVelocity = 0;
		}
	}

	private function selectAndDisplayNewTarget():Void
	{
		var next = this.currentTarget;
		while (next == this.currentTarget)
		{
			next = this.random.getObject(this.letters);
		}

		jumboLetter = this.addSprite('assets/images/letters/${next}.png');
		jumboLetter.scaleTo(JUMBO_LETTER_SIZE, JUMBO_LETTER_SIZE);
		jumboLetter.x = (this.width - jumboLetter.width) / 2;
		jumboLetter.y = (this.height - jumboLetter.height) / 2;

		this.whiteout.alpha = 0;
		this.whiteout.alphaVelocity = 1;
		this.jumboLetter.alpha = 0;
		this.jumboLetter.alphaVelocity = 1;
		
		this.currentTarget = next;

		this.after(3, function() {
			this.whiteout.alphaVelocity = -1;
			this.jumboLetter.alphaVelocity = -1;
		});
	}
}

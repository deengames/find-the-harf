package deengames.findtheharf;

import flixel.FlxG;
import flixel.text.FlxText;
import flixel.ui.FlxButton;
import flixel.math.FlxMath;
import flixel.math.FlxRandom;
import quasar.core.QuasarState;
import quasar.core.QuasarSprite;
import quasar.AudioPlayer;

class PlayState extends QuasarState
{
	private static inline var LETTERS_ACROSS = 4;
	private static inline var LETTERS_DOWN = 7;
	private static inline var LETTER_SIZE:Int = 128;
	private static inline var JUMBO_LETTER_SIZE = 512;
	private static inline var WHITEOUT_ALPHA:Float = 0.75;
	private static inline var BUTTON_PADDING:Int = 16;
	private static inline var FOOTER_HEIGHT:Int = 128;

	private static var LETTERS = ["alif", "ba", "ta", "tha", "jeem", "7a", "kha",
		"daal", "thaal", "ra", "za", "seen", "sheen", "saad",
		"daad", "taw", "thaw", "ayn", "ghayn", "fa",
		"qaaf", "kaaf", "laam", "meem", "noon", "ha", "waw", "ya"];

	private static var COLOURS = ["red", "orange", "yellow", "green", "blue", "purple", "pink"];

	private var letterMap = new Map<String, String>();	

	private var praises:Array<String> = ["awesome", "great-job", "hurray"];

	private var random:FlxRandom = new FlxRandom();
	private var currentTarget:String;
	private var whiteout:QuasarSprite;
	private var jumboLetter:QuasarSprite;
	private var wrongPicks = new Array<String>();
	private var letterXs = new Array<QuasarSprite>();

	private var fadeOutStartTime:Float = 0;

	override public function create():Void
	{
		super.create();		

		for (i in 0 ... LETTERS.length)
		{
			var letter = LETTERS[i];
			var colour = COLOURS[i % COLOURS.length];
			letterMap.set(letter, colour);
		}

		this.addSprite("assets/images/background.png");

		var help = this.addSprite("assets/images/help.png");
		help.move(BUTTON_PADDING, this.height - help.height - BUTTON_PADDING).onClick(function() {
			AudioPlayer.stopAndEmptyQueue();
			this.playFindCurrentLetterAudio();
		}, false);

		var x = this.addSprite("assets/images/x.png");
		x.move(this.width - x.width - BUTTON_PADDING, this.height - x.height - BUTTON_PADDING).onClick(function() {
			AudioPlayer.stopAndEmptyQueue();
			this.fadeOutInstantly(); // move to top
			this.fadeOutStartTime  = this.totalStateTime;
		}, false);

		var groupXOffset = (this.width - (LETTERS_ACROSS * LETTER_SIZE)) / 2;
		var groupYOffset = (this.height - FOOTER_HEIGHT - (LETTERS_DOWN * LETTER_SIZE)) / 2;

		for (i in 0...LETTERS.length)
		{
			var letter = LETTERS[i];
			var sprite = this.addSprite('assets/images/letters/${letter}.png');
			sprite.onClick(function() {
				// If jumbo is visible, fade out real fast.
				if (jumboLetter.alpha > 0)
				{
					jumboLetter.alphaVelocity = -2;
					whiteout.alphaVelocity = -2;
				}				
				else if (letter == this.currentTarget)
				{
					this.pickedCorrectLetter();
				}
				// Never picked this wrong letter before
				else if (wrongPicks.indexOf(letter) == -1)
				{
					this.pickedWrongLetter(letter);
				}
			}, false); // Use bounding-box for clicks
			sprite.scaleTo(LETTER_SIZE, LETTER_SIZE);
			sprite.x = ((i % LETTERS_ACROSS) * sprite.width) + groupXOffset;
			sprite.y = (Math.floor(i / LETTERS_ACROSS) * sprite.height) + groupYOffset;

			var spriteCrossOut = this.addSprite("assets/images/letterX.png");
			spriteCrossOut.x = sprite.x;
			spriteCrossOut.y = sprite.y;
			spriteCrossOut.alpha = 0;
			this.letterXs.push(spriteCrossOut);
		}

		this.whiteout = this.addSprite("assets/images/whiteout.jpg");

		this.selectAndDisplayNewTarget();
	}

	override public function update(elapsed:Float):Void
	{
		super.update(elapsed);

		this.whiteout.update(elapsed);
		this.jumboLetter.update(elapsed);

		if (this.whiteout.alpha > WHITEOUT_ALPHA)
		{
			this.whiteout.alpha = WHITEOUT_ALPHA;
			this.whiteout.alphaVelocity = 0;			
			this.jumboLetter.alpha = 1;
			this.jumboLetter.alphaVelocity = 0;
		}

		if (this.fadeOutStartTime > 0)
		{
			var delta = this.totalStateTime - this.fadeOutStartTime;
			this.blackout.alpha = delta / 0.5; // 0.5s

			if (delta >= 0.5)
			{
				FlxG.switchState(new TitleState());
			}
		}
	}

	private function selectAndDisplayNewTarget():Void
	{
		// Without this, if you click really rapidly on the correct letter, and
		// dismiss the jumbo letter, the current jumbo letter might disappear
		// really fast because of a callback queued a few letters ago.
		// this.afterCallbacks.clear();
		for (key in this.afterCallbacks.keys())
		{
			this.afterCallbacks.remove(key);
		}

		for (x in this.letterXs)
		{
			x.alpha = 0;
		}

		wrongPicks = new Array<String>();

		var next = this.currentTarget;
		while (next == this.currentTarget)
		{
			next = this.random.getObject(PlayState.LETTERS);
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

		this.playFindCurrentLetterAudio();
	}

	private function playFindCurrentLetterAudio():Void
	{
		AudioPlayer.queueAndPlaySerially(["assets/sounds/find-the-letter", 'assets/sounds/letters/${this.currentTarget}']);		
	}

	private function pickedWrongLetter(letter:String):Void
	{
		AudioPlayer.stopAndEmptyQueue();					
		this.wrongPicks.push(letter);
		var letterIndex = LETTERS.indexOf(letter);
		this.letterXs[letterIndex].alpha = 1;

		if (wrongPicks.length <= 2)
		{
			// That's not <x>. That's, <y>. The harf <x> ...
			AudioPlayer.queueAndPlaySerially(['assets/sounds/wrong/thats-not', 'assets/sounds/letters/${this.currentTarget}', "assets/sounds/wrong/thats", 'assets/sounds/letters/${letter}',
				"assets/sounds/wrong/the-letter", 'assets/sounds/letters/${this.currentTarget}']);

			if (wrongPicks.length == 1)
			{
				// ... comes after/before <y>!
				var afterOrBefore = LETTERS.indexOf(this.currentTarget) < LETTERS.indexOf(letter) ? "before" : "after";
				AudioPlayer.queueAndPlaySerially(["assets/sounds/wrong/comes", 'assets/sounds/wrong/${afterOrBefore}', 'assets/sounds/letters/${letter}']);
			}
			else if (wrongPicks.length == 2)
			{
				// ... is <colour>!
				var colour = this.letterMap[this.currentTarget];
				AudioPlayer.queueAndPlaySerially(["assets/sounds/wrong/is", 'assets/sounds/colours/${colour}']);
			}
		}
		else
		{
			AudioPlayer.stopLastSound();
			AudioPlayer.play("assets/sounds/wrong/sorry-try-again");
			this.selectAndDisplayNewTarget();
		}
	}

	private function pickedCorrectLetter():Void
	{
		AudioPlayer.stopAndEmptyQueue();
		var praise = this.random.getObject(this.praises);
		AudioPlayer.queueAndPlaySerially(['assets/sounds/correct', 'assets/sounds/praise/${praise}', "assets/sounds/praise/mashaAllah", "assets/sounds/now"]);
		selectAndDisplayNewTarget();
	}
}

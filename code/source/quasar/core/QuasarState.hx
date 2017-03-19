package quasar.core;

import flixel.FlxG;
import flixel.FlxState;
import flixel.util.FlxColor;
import quasar.core.QuasarSprite;
import quasar.AudioPlayer;

class QuasarState extends FlxState
{
    public var width(get, null):Int = 0;
    public var height(get, null):Int = 0;

    // Granularity on Neko and some platforms is second-by-second only.
    // To get around this, track the total amount of time we see (elapsed)
    // in calls to update().
    public var totalStateTime(default, null):Float = 0;

    private var afterCallbacks = new Map<Date, Void->Void>();
    private var blackout:QuasarSprite;

    private static inline var FADE_DURATION_SECONDS:Float = 0.5;

    override public function create():Void
    {
        super.create();
        this.blackout = this.addSprite("assets/images/blackout.jpg");                
    }    

    public function get_width():Int
    {
        return FlxG.stage.stageWidth;
    }

    public function get_height():Int
    {
        return FlxG.stage.stageHeight;
    }

    public function addSprite(filename:String):QuasarSprite
    {
        if (filename.indexOf('.') == -1)
        {
            filename = '${filename}.png';
        }

        var sprite = new QuasarSprite(filename);
        this.add(sprite);
        return sprite;
    }

    public function after(numSeconds:Float, callback:Void->Void):QuasarState
    {
        var now = Date.now();
        var then = Date.fromTime(now.getTime() + (1000 * numSeconds));
        if (this.afterCallbacks.exists(then))
        {
            throw 'Can\'t have multiple callbacks for the same time; duplicate was ${numSeconds}';
        }
        this.afterCallbacks[then] =  callback;
        return this;
    }

    public function fadeOutInstantly():Void
    {
        this.remove(this.blackout);
        this.insert(this.members.length, this.blackout); // add to the top
        this.blackout.alpha = 1;
    }

    override public function update(elapsedSeconds:Float):Void
    {
        super.update(elapsedSeconds);
        this.totalStateTime += elapsedSeconds;
        
        for (date in afterCallbacks.keys())
        {
            if (Date.now().getTime() >= date.getTime())
            {
                var callback = afterCallbacks[date];
                callback();
                afterCallbacks.remove(date);
            }
        }

        AudioPlayer.update();
    }
}
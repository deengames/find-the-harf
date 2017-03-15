package quasar.core;

import flixel.FlxG;
import flixel.FlxState;
import quasar.core.QuasarSprite;

class QuasarState extends FlxState
{

    public var width(get, null):Int = 0;
    public var height(get, null):Int = 0;
    private var afterCallbacks = new Map<Date, Void->Void>();

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
        if (this.afterCallbacks.exists(then)) {
            throw 'Can\'t have multiple callbacks for the same time; duplicate was ${numSeconds}';
        }
        this.afterCallbacks[then] =  callback;
        return this;
    }

    override public function update(elapsedSeconds:Float):Void
    {
        super.update(elapsedSeconds);
        for (date in afterCallbacks.keys())
        {
            if (Date.now().getTime() >= date.getTime())
            {
                var callback = afterCallbacks[date];
                callback();
                afterCallbacks.remove(date);
            }
        }
    }
}
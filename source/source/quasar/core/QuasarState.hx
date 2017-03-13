package quasar.core;

import flixel.FlxG;
import flixel.FlxState;
import quasar.core.QuasarSprite;

class QuasarState extends FlxState {

    public var width(get, null):Int = 0;
    public var height(get, null):Int = 0;

    public function get_width():Int
    {
        return FlxG.stage.stageWidth;
    }

    public function get_height():Int
    {
        return FlxG.stage.stageHeight;
    }

    function addSprite(filename:String):QuasarSprite
    {
        if (filename.indexOf('.') == -1)
        {
            filename = '${filename}.png';
        }

        var sprite = new QuasarSprite(filename);
        this.add(sprite);
        return sprite;
    }
}
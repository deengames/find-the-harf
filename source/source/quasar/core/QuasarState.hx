package quasar.core;

import flixel.FlxG;
import flixel.FlxSprite;
import flixel.FlxState;

class QuasarState extends FlxState {

    public var width(get, null):Int = 0;
    public var height(get, null):Int = 0;

    public function get_width():Int {
        return FlxG.stage.stageWidth;
    }

    public function get_height():Int {
        return FlxG.stage.stageHeight;
    }

    public function addSprite(filename:String):FlxSprite
    {
        if (filename.indexOf('.') == -1) {
            filename = '${filename}.png';
        }

        var sprite = new FlxSprite();
        sprite.loadGraphic(filename);
        this.add(sprite);
        return sprite;
    }
}
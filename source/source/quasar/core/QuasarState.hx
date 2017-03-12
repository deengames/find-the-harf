package quasar.core;

import flixel.FlxG;
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
}
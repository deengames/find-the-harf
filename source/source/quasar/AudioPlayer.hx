package quasar;

import flixel.FlxG;

import openfl.Assets;

class AudioPlayer
{
    private function new() { }

    private static inline var SOUND_EXT = "ogg";    

    public static function loadAndPlay(audioFileName:String):Void
    {
        // FlxSound doesn't work on HTML5. This does.
        var audio:openfl.media.Sound = Assets.getSound('${audioFileName}.${SOUND_EXT}');
        audio.play();
    }
}
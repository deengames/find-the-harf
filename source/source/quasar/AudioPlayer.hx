package quasar;

import flixel.FlxG;
import flixel.system.FlxSound;

class AudioPlayer
{
    private function new() { }

    private static inline var SOUND_EXT = "ogg";    

    public static function loadAndPlay(audioFilename:String):Void
    {
        var audio:FlxSound = FlxG.sound.load('${audioFilename}.${SOUND_EXT}');
        audio.play();
        trace('Playing ${audioFilename}.${SOUND_EXT}');
    }
}
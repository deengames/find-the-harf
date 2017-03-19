package quasar;

import flixel.FlxG;

class AudioPlayer
{
    private function new() { }

    private static inline var SOUND_EXT = "ogg";    

    public static function loadAndPlay(audioFileName:String):Void
    {
        FlxG.sound.load('${audioFileName}.${SOUND_EXT}').play();
    }
}
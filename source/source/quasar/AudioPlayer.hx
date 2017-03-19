package quasar;

import flixel.FlxG;
import flixel.system.FlxSound;

class AudioPlayer
{
    private function new() { }

    private static inline var SOUND_EXT = "ogg";
    private static var lastSound:FlxSound;
    private static var audioFilesQueue = new Array<String>();

    public static function loadAndPlay(audioFileName:String):FlxSound
    {
        var sound = FlxG.sound.load('${audioFileName}.${SOUND_EXT}');
        sound.play();
        return sound;
    }

    public static function playSerially(audioFiles:Array<String>):Void
    {
        for (i in 0 ... audioFiles.length)
        {
            AudioPlayer.audioFilesQueue.push(audioFiles[i]);
        }
    }

    public static function update():Void
    {
        if (audioFilesQueue.length > 0 && (lastSound == null || !lastSound.playing))
        {
            var fileName = audioFilesQueue.shift(); // take first element
            AudioPlayer.lastSound = loadAndPlay(fileName);
        }
    }
}
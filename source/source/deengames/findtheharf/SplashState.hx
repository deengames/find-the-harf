package deengames.findtheharf;

import flash.display.Sprite;
import flash.display.StageAlign;
import flash.display.StageScaleMode;
import flash.events.Event;
import flash.Lib;

import flixel.FlxGame;
import flixel.FlxG;
import flixel.util.FlxColor;

import quasar.core.QuasarState;
import quasar.core.QuasarSprite;
import quasar.AudioPlayer;

// Loads from startup screen.
class SplashState extends QuasarState
{
    private var startTime:Float = 0;

    /**
    * Function that is called up when to state is created to set it up.
    */
    override public function create():Void
    {
        super.create();        
        var title:QuasarSprite = this.addSprite('assets/images/logo-vertical');
        title.move((this.width - title.width) / 2, (this.height - title.height) / 2);

        AudioPlayer.play('assets/sounds/giggle');        

        startTime = this.totalStateTime;
        this.fadeOutInstantly();
    }

    override public function update(elapsed:Float):Void
    {
        super.update(elapsed);            
        var timeDelta = this.totalStateTime - startTime;

        // 500ms => half-a-second to fade in
        if (timeDelta < 0.5)
        {
            this.blackout.alpha = (0.5 - timeDelta) / 0.5;
        }
        else if (timeDelta >= 0.5 && timeDelta < 3.5)
        {
            this.blackout.alpha = 0;
        }
        // 3.5s total (3s after fade-in) to show the logo
        // After 3.5s, start fading out.
        else if (timeDelta >= 3.5 && timeDelta < 4)
        {
            this.blackout.alpha = (timeDelta - 3.5) / 0.5;
        // After 4s, move to the next screen
        }
        else if (timeDelta >= 4)
        {
            FlxG.switchState(new TitleState());
        }
    }
}

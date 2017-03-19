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

class TitleState extends QuasarState
{
    private var startTime:Float = 0;
    private var fade:String = "in";
    
    override public function create():Void
    {
        super.create();
        var title:QuasarSprite = this.addSprite('assets/images/titlescreen.png');
        title.onMouseClick(function()
        {
            fade = "out";
            startTime = this.totalStateTime;
        });

        AudioPlayer.play('assets/sounds/titlescreen');        
        startTime = this.totalStateTime;
        this.fadeOutInstantly();
    }

    override public function update(elapsed:Float):Void
    {
        super.update(elapsed);            
        var timeDelta = this.totalStateTime - startTime;

        // 500ms => half-a-second to fade in
        if (timeDelta < 0.5 && fade == "in")
        {
            this.blackout.alpha = (0.5 - timeDelta) / 0.5;
        }
        else if (fade == "out")
        {
            if (timeDelta < 0.5)
            {
                this.blackout.alpha = timeDelta / 0.5; // 0.5s
            }
            else
            {
                FlxG.switchState(new PlayState());
            }
        }
    }
}

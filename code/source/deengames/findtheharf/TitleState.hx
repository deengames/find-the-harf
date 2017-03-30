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
    private static inline var BADGE_PADDING:Int = 16;

    private var startTime:Float = 0;
    private var fade:String = "in";
    
    override public function create():Void
    {
        super.create();

        var title:QuasarSprite = this.addSprite('assets/images/titlescreen.png');
        title.onClick(startGame);

        AudioPlayer.play('assets/sounds/titlescreen');        
        startTime = this.totalStateTime;

        var facebook = this.addSprite("assets/images/facebook.png");
        facebook.move(BADGE_PADDING, this.height - facebook.height - BADGE_PADDING);
        facebook.onClick(function()
        {
            FlxG.openURL("http://facebook.com/deengames");
        }, false);

        var patreon = this.addSprite("assets/images/patreon.png");
        patreon.move(this.width - patreon.width - BADGE_PADDING, this.height - patreon.height - BADGE_PADDING);
        patreon.onClick(function()
        {
            FlxG.openURL("http://patreon.com/deengames");
        }, false);

        var begin:QuasarSprite = this.addSprite("assets/images/begin.png");
        begin.move((this.width - begin.width) / 2, (this.height - begin.height) / 2);         
        begin.y += begin.height; 

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

    private function startGame():Void
{
        // prevent multiple clicks restarting fade
        if (fade != "out")
        {
            fade = "out";
            startTime = this.totalStateTime;
        }
    }
}

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
import quasar.web.Browser;

class TitleState extends QuasarState
{
    private static inline var BADGE_PADDING:Int = 16;

    private var startTime:Float = 0;
    private var fade:String = "in";
    
    override public function create():Void
    {
        super.create();

        var title:QuasarSprite = this.addSprite('assets/images/titlescreen.png');
        title.onClick(function()
        {
            // prevent multiple clicks restarting fade
            if (fade != "out")
            {
                fade = "out";
                startTime = this.totalStateTime;
            }
        });

        AudioPlayer.play('assets/sounds/titlescreen');        
        startTime = this.totalStateTime;

        var facebook = this.addSprite("assets/images/facebook.png");
        facebook.move(BADGE_PADDING, this.height - facebook.height - BADGE_PADDING);
        facebook.onClick(function()
        {
            Browser.openUrl("http://facebook.com/deengames");
        }, false);

        var patreon = this.addSprite("assets/images/patreon.png");
        patreon.move(this.width - patreon.width - BADGE_PADDING, this.height - patreon.height - BADGE_PADDING);
        patreon.onClick(function()
        {
            Browser.openUrl("http://patreon.com/deengames");
        }, false);

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

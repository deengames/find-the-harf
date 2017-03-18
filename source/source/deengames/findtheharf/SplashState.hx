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

    //this.loadAndPlay('assets/sounds/giggle');

    startTime = Date.now().getTime();
    this.fadeOutInstantly();
  }

  override public function update(elapsed:Float):Void
  {
      super.update(elapsed);      
      var timeDelta = Date.now().getTime() - startTime;
      trace(timeDelta);

      // 500ms => half-a-second to fade in
      if (timeDelta <= 500)
      {
          this.blackout.alpha = (500 - timeDelta) / 500;
      }
      // 3.5s total (3s after fade-in) to show the logo
      // After 3.5s, start fading out.
      else if (timeDelta >= 3500 && timeDelta < 4000)
      {
          this.blackout.alpha = (timeDelta - 3500) / 500;
      // After 4s, move to the next screen
      } else if (timeDelta >= 4000) {
          FlxG.switchState(new PlayState());
      }

  }
}

package scenes;

import kha.Assets;
import kha.audio2.Audio1;
import kha.Font;
import kha.graphics2.Graphics;
import kha.Image;
import kha.Scheduler;

import scenes.TitleScene;

class SplashScene extends BaseScene
{
    private var logo:Image;
    private var blackout:Image;

    private var state:String = "fade in"; // fade in, giggle, fade out
    private var stateStartTime:Float;

    public function new() {
        super();
        
        this.loadAssets(function() {
            logo = Assets.images.dg_logo;
            blackout = Assets.images.blackout;

            stateStartTime = Scheduler.time();

            this.after(0.5, function() {
                Audio1.play(Assets.sounds.giggle);                
            }).after(1, function() {
                this.state = "giggle";
                this.stateStartTime = Scheduler.time();
            }).after(4, function() {
                this.state = "fade out";
                this.stateStartTime = Scheduler.time();
            }).after(5, function() {
                new TitleScene();
                this.destroy();
            });
        });
    }

    override function onRender(g:Graphics):Void
    {    
        if (this.initialized) {

            this.drawImage(logo, 0, 0);

            if (this.state == "fade in") {
                var blackoutAlpha = 1 - (Scheduler.time() - this.stateStartTime);
                this.drawImage(blackout, 0, 0, blackoutAlpha);
            } else if (this.state == "fade out") {
                var blackoutAlpha = (Scheduler.time() - stateStartTime);
                this.drawImage(blackout, 0, 0, blackoutAlpha);
            }
        }
    }
}
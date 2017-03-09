package scenes;

import kha.Assets;
import kha.Font;
import kha.graphics2.Graphics;
import kha.Image;
import kha.Scheduler;

class SplashScene extends BaseScene
{
    private var logo:Image;
    private var blackout:Image;
    private var logoAlpha:Float = 0;
    private var blackoutAlpha:Float = 1;

    private var state:String = "fade in"; // fade in, giggle, fade out
    private var stateStartTime:Float;

    public function new() {
        super();
        
        this.loadAssets(function() {
            logo = Assets.images.dg_logo;
            blackout = Assets.images.blackout;

            stateStartTime = Scheduler.time();
            trace("Start");

            this.after(1, function() {
                this.state = "giggle";
                this.stateStartTime = Scheduler.time();
                trace("Giggling");
            }).after(4, function() {
                this.state = "fade out";
                this.stateStartTime = Scheduler.time();
                trace("Fading out");
            }).after(5, function() {
                trace("DONE!!!!!!");
            });
        });
    }

    override function onRender(g:Graphics):Void
    {        
        if (this.initialized) {

            this.drawImage(logo, 0, 0);

            if (this.state == "fade in") {
                blackoutAlpha = 1 - (Scheduler.time() - this.stateStartTime);
                this.drawImage(blackout, 0, 0, blackoutAlpha);
            } else if (this.state == "fade out") {
                blackoutAlpha = (Scheduler.time() - stateStartTime);
                this.drawImage(blackout, 0, 0, blackoutAlpha);
            }
            
        }
    }
}
package scenes;

import kha.Assets;
import kha.audio2.Audio1;
import kha.Font;
import kha.graphics2.Graphics;
import kha.Image;
import kha.Scheduler;

class TitleScene extends BaseScene
{
    private var image:Image;
    private var blackout:Image;

    private var state:String = "fade in"; // fade in, waiting, fade out
    private var stateStartTime:Float;

    public function new() {
        super();
        
        this.loadAssets(function() {
            image = Assets.images.titlescreen;            
            blackout = Assets.images.blackout;
            this.stateStartTime = Scheduler.time();
            this.after(1, function() { this.state = "waiting"; });
        });
    }

    override function onRender(g:Graphics):Void
    {        
        trace("title");

        if (this.initialized) {
            this.drawImage(image, 0, 0);
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
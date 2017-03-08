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

    public function new() {
        super();
        this.loadAssets(function() {
            logo = Assets.images.dg_logo;
            blackout = Assets.images.blackout;
        });
    }

    override function onRender(g:Graphics):Void
    {        
        if (this.initialized) {
            var alpha:Float = Math.cos(Scheduler.time());
            // map alpha from [-1..1] to [0..1]
            alpha = (1 + alpha) / 2;
            this.drawImage(this.logo, 0, 0, alpha);
        }
    }
}
package scenes;

import kha.Assets;
import kha.Color;
import kha.Font;
import kha.Framebuffer;
import kha.graphics2.Graphics;
import kha.Image;
import kha.Scaler;
import kha.Scheduler;
import kha.System;

/** Don't use this class directly. It has boilerplate code. Subclass it. */
class BaseScene
{
	private var font:Font;
    private var backbuffer:Image;
    private var initialized(default, null):Bool = false;
    private var lastUpdateTime:Float = 0;

    // Float (time) to callback. Since we can't keep a flat as a key,
    // we instead substitute an int (float * 1000)
    private var delayFunctions = new Map<Int, Void->Void>();

    public function new()
    {
        System.notifyOnRender(render);
		Scheduler.addTimeTask(update, 0, 1 / 60);

		backbuffer = Image.createRenderTarget(Main.GAME_WIDTH, Main.GAME_HEIGHT);
    }

    ///// Start protected/internal functions \\\\\

    private function loadAssets(callback:Void->Void):Void
    {
        Assets.loadEverything(function()
		{
            initialized = true;
            callback();
        });
    }

    // Virtual functions to override
    private function onUpdate():Void { } 

    private function onRender(g:Graphics):Void { }

    private function update():Void
    {
        for (time in this.delayFunctions.keys()) {
            if (Scheduler.time() * 1000 >= time) {
                this.delayFunctions[time]();
                this.delayFunctions.remove(time);
            }
        }
        this.onUpdate();
    }

    private function drawImage(image:Image, x:Int, y:Int, alpha:Float = 1.0):Void
    {
        if (!initialized)
        {
            trace("Warning: can't call drawImage before calling loadAssets(...) to initialize the game.");
            return;
        }

        var g = backbuffer.g2;
        g.opacity = alpha; // draw at alpha
        g.drawImage(image, x, y);
        g.opacity = 1.0; // reset alpha
    }

    

    private function drawText(text:String, x:Int, y:Int, fontSize:Int = 36):Void
    {
        if (!initialized)
        {
            trace("Warning: can't call drawImage before calling loadAssets(...) to initialize the game.");
            return;
        }

        var g = backbuffer.g2;    
        g.fontSize = fontSize;    
        g.drawString(text, x, y);
    }

    ///// End protected/internal functions \\\\\

    ///// Absolutely ridiculous fluent functions /////
    private function after(numSeconds:Int, callback:Void->Void):BaseScene
    {
        // Possible but extremely likely to get two functions with the
        // same value of relativeTime. We can't ignore that possibility.
        // So, offset by a small fractional amount of time if this happens.
        var relativeTime:Int = Std.int(1000 * (Scheduler.time() + numSeconds));
        while (this.delayFunctions.exists(relativeTime)) {
            relativeTime += 1;
        }
        this.delayFunctions[relativeTime] = callback;
        return this;
    }
    ///// End Absolutely ridiculous fluent functions /////

    private function render(frameBuffer:Framebuffer):Void
    {
        if (initialized)
        {
            var g = backbuffer.g2;

            if (this.font != null) {
                g.font = this.font;
            }
            
            // clear our backbuffer using graphics2
            g.begin(true, Color.Black);

            this.onRender(g);
            
            g.end();
            // draw our backbuffer onto the active framebuffer
            frameBuffer.g2.begin();
            Scaler.scale(backbuffer, frameBuffer, System.screenRotation);
            frameBuffer.g2.end();
        }
    }
}
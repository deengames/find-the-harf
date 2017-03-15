package quasar.core;

import flixel.FlxObject;
import flixel.FlxSprite;
import flixel.input.mouse.FlxMouseEventManager;

class QuasarSprite extends FlxSprite 
{
    private var isMouseDown:Bool = false;
    private var mouseClickCallback:Void->Void = null;
    public var alphaVelocity(default, default):Float = 0; // Change in alpha per second

    public function new(filename:String)
    {
        super();
        this.loadGraphic(filename);
    }

    // If false, uses the bounding-box as the clickable area
    public function onMouseClick(mouseClickCallback:Void->Void, usePixelPerfectCollisions:Bool = true)
    {
        this.mouseClickCallback = mouseClickCallback;
        FlxMouseEventManager.add(this, this.onMouseDown, this.onMouseUp, null, null, false, true, usePixelPerfectCollisions);
    }

    public function scaleTo(width:Int, height:Int):Void
    {
        this.setGraphicSize(width, height);
        this.updateHitbox();
    }

    override public function update(elapsedSeconds:Float):Void
    {
        super.update(elapsedSeconds);
        this.alpha += (this.alphaVelocity * elapsedSeconds);
        
        if (this.alpha < 0)
        {
            this.alpha = 0;
        }
        else if (this.alpha > 1)
        {
            this.alpha = 1;
        }
    }

    private function onMouseDown(obj:FlxObject):Void
    {
        this.isMouseDown = true;
    }

    private function onMouseUp(obj:FlxObject):Void
    {
        if (this.isMouseDown)
        {
            if (this.mouseClickCallback != null)
            {
                this.mouseClickCallback();
            }

            this.isMouseDown = false;
        }
    }
}
package quasar.core;

import flixel.FlxObject;
import flixel.FlxSprite;
import flixel.input.mouse.FlxMouseEventManager;

class QuasarSprite extends FlxSprite 
{
    private var isMouseDown:Bool = false;
    private var onClickCallback:Void->Void = null;
    public var alphaVelocity(default, default):Float = 0; // Change in alpha per second

    public function new(filename:String)
    {
        super();
        this.loadGraphic(filename);
    }

    // If false, uses the bounding-box as the clickable area
    public function onClick(onClickCallback:Void->Void, usePixelPerfectCollisions:Bool = true):QuasarSprite
    {
        this.onClickCallback = onClickCallback;
        FlxMouseEventManager.add(this, this.onMouseDown, this.onMouseUp, null, null, false, true, usePixelPerfectCollisions);
        return this;
    }

    public function scaleTo(width:Int, height:Int):QuasarSprite
    {
        this.setGraphicSize(width, height);
        this.updateHitbox();
        return this;
    }

    public function move(x:Float, y:Float):QuasarSprite
    {
        this.x = x;
        this.y = y;
        return this;
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
            if (this.onClickCallback != null)
            {
                this.onClickCallback();
            }

            this.isMouseDown = false;
        }
    }
}
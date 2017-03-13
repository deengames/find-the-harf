package quasar.core;

import flixel.FlxObject;
import flixel.FlxSprite;
import flixel.input.mouse.FlxMouseEventManager;

class QuasarSprite extends FlxSprite 
{
    private var isMouseDown:Bool = false;
    private var mouseClickCallback:Void->Void = null;

    public function new(filename:String, mouseClickCallback:Void->Void = null, usePixelPerfectCollisions:Bool = true)
    {
        super();
        this.loadGraphic(filename);

        if (mouseClickCallback != null)
        {
            this.mouseClickCallback = mouseClickCallback;
            FlxMouseEventManager.add(this, this.onMouseDown, this.onMouseUp, null, null, false, true, usePixelPerfectCollisions);
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
package quasar.web;

import openfl.net.URLRequest;
import openfl.Lib;

class Browser
{
    /**
    Opens a URL in a new browser window. Works on JS and Android.
    Assumes no query parameters (for now).
    Not sure about other platforms at the moment.
    */
    public static function openUrl(url:String):Void
    {
        #if js
        js.Browser.location.assign(url);
        #else
        Lib.getURL(new URLRequest(url));
        #end
    }
}
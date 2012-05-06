package com.deengames.radiantwrench.thirdparty;

import java.util.HashMap;
import java.util.Map;

import com.flurry.android.FlurryAgent;

public class FlurryHelper {
	
	private FlurryHelper() { } // static class
	
	private static boolean _disabled = false;
	
	public static void logEvent(String eventId) {
		if (!_disabled) {
			FlurryAgent.logEvent(eventId);
		} else {
			System.out.println("FlurryHelper.logEvent " + eventId);
		}
	}
	
	public static void logEvent(String eventId, String key, String value) {
		if (!_disabled) {
			Map map = new HashMap();
			map.put(key, value);
			FlurryAgent.logEvent(eventId, map);
		} else {
			System.out.println("FlurryHelper.logEvent " + eventId + " (" + key + "=" + value + ")");
		}
	}
	
	public static void logEvent(String eventId, String key1, String value1, String key2, String value2) {
		if (!_disabled) {
			Map map = new HashMap();
			map.put(key1, value1);
			map.put(key2, value2);
			FlurryAgent.logEvent(eventId, map);
		} else {
			System.out.println("FlurryHelper.logEvent " + eventId + " (" + key1 + "=" + value1 + " " + key2 + "=" + value2 + ")");
		}
	}

	public static void SetIsDesktopApp() {
		_disabled = true;
	}
}

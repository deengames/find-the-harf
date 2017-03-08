package;

import kha.System;

class Main {
	public static function main() {
		// index.html doesn't use these properties. You can set them by hand
		// (Kha won't overwrite them when you rebuild the html5 target).
		System.init({ title: "Unknown", width: 576, height: 1024 }, function () {
			new Unknown();
		});
	}
}

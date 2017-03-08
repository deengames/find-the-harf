package;

import kha.System;

class Main {
	public static function main() {
		System.init("Unknown", 576, 1024, function () {
			new Unknown();
		});
	}
}

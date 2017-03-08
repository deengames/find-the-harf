var project = new Project('Blocks');
project.addAssets('Assets/**');
project.addSources('Sources');
// These don't work in Kha 16.1.2 (from haxelib)
project.windowOptions.width = 576;
project.windowOptions.height = 1024;
resolve(project);

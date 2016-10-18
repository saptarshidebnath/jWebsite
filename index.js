var exitCode = require('exit-code');
var sass = require('node-sass');
var compass = require('compass-importer');
var fs = require('fs');
/*

process.argv.forEach(function (val, index, array) {
  console.log(index + ': ' + val);
});

*/

valid_arg_length=4;

if (process.argv.length == valid_arg_length) {
    //
    // scss/sass file name
    //
    scss_file_name = process.argv[3];


    //
    // Default include path. As of now not going to mess around with them
    //
    include_path = ['lib/', 'mod/'];

    fs.access(scss_file_name, fs.F_OK, function(err) {
        if (!err) {            
            sass.render({
                file: scss_file_name,
                importer: compass,
                includePaths: include_path,
                outputStyle: 'compressed',
                sourceMapEmbed: true,
                sourceMapContents: false
            }, function(error, result) { // node-style callback from v3.0.0 onwards 
                if (error) {
                    console.log(JSON.stringify(error));
                    process.exitCode = 201
                } else {
                    console.log(result.css.toString());
                }
            });
        } else {
            console.log("File \"" + scss_file_name + "\" is not accessible. ");
            console.log(JSON.stringify(err));
            usage();
            process.exitCode = 102
        }
    });

} else {
    console.log("Illegal number of parameters")
    usage();
    process.exitCode = 101
}


function usage() {
    console.log("Usage : npm run start -s -- <path/to/scss/sass/file>")
    console.log("1XX : Syntax errors or input files not found");
    console.log("2XX : Error compiling the sass file");
}
module.exports = function (grunt) {

    require('google-closure-compiler').grunt(grunt);
    grunt.initConfig({
        varaibles: {
            name: "jwebsite"
        },
        pkg: grunt.file.readJSON('package.json'),
        watch: {
            sass: {
                files: ['src/main/scss/*.{scss,sass}'],
                tasks: ['sass:dist']
            },
            js: {
                files: ['src/main/js/**/*.js'],
                tasks: ['closure-compiler:dev']
            }
        },
        sass: {
            options: {
                importer: require('compass-importer'),
                sourceMap: true,
                sourceComments: true,
                outputStyle: 'expanded'
            },
            dist: {
                files: {
                    'target/<%= varaibles.name %>/css/compiled.css': 'src/main/scss/compiled.scss'
                }
            }
        },
        'closure-compiler': {
            dev: {
                files: {
                    'target/<%= varaibles.name %>/js/compiled.min.js': ['src/main/js/**/*.js']
                },
                options: {
                    compilation_level: 'SIMPLE',
                    language_in: 'ECMASCRIPT5_STRICT',
                    create_source_map: 'target/<%= varaibles.name %>/js/compiled.min.js.map',
                    output_wrapper: '(function(){\n%output%\n}).call(this)\n//# sourceMappingURL=compiled.min.js.map'
                }
            }
        }
    });

    grunt.registerTask('default', ['sass:dist', 'closure-compiler:dev']);
    grunt.registerTask('compile', ['default']);
    grunt.registerTask('cjs', ['closure-compiler:dev']);
    grunt.registerTask('gwatch', ['default', 'watch']);

    grunt.loadNpmTasks('grunt-sass');
    grunt.loadNpmTasks('grunt-contrib-watch');
};

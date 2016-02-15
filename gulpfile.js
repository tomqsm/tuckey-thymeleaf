/* File: gulpfile.js */
//https://scotch.io/tutorials/automate-your-tasks-easily-with-gulp-js
// grab our gulp packages
var gulp = require('gulp'),
        gutil = require('gulp-util'),
        jshint = require('gulp-jshint'),
        sass = require('gulp-sass'),
        sourcemaps = require('gulp-sourcemaps'),
        uglify = require('gulp-uglify'),
        concat = require('gulp-concat'),
        rename = require('gulp-rename'),
        connect = require('gulp-connect'),
        gulpif = require('gulp-if'),
        /*variables*/
        javascriptsSrc = './src/main/webapp/js/src/**/*.js',
        javascriptsDist = './src/main/webapp/js/dist',
        htmlsSrc = './src/main/webapp/html/*.html',
        sasssheetsSrc = './src/main/webapp/css/src/*.scss',
        sasssheetsDist = './src/main/webapp/css/dist';

// define the default task and add the watch task to it
gulp.task('default', ['watch', 'connect']);

gulp.task('connect', function () {
    connect.server({
        root: ['./src/main/webapp/html', './src/main/webapp'],
        port: 8001,
        livereload: true
    });
});

gulp.task('build-css', function () {
    return gulp.src(sasssheetsSrc)
            .pipe(sourcemaps.init())
            .pipe(sass())
            .pipe(sourcemaps.write())
            .pipe(gulp.dest(sasssheetsDist))
            .pipe(connect.reload());
});

gulp.task('build-js', function () {
    return gulp.src(javascriptsSrc)
            .pipe(sourcemaps.init())
            .pipe(concat('app-bundle.js'))
            .pipe(gulpif(gutil.env.type === 'production', uglify()))
            .pipe(gulpif(gutil.env.type === 'production', rename({
                suffix: '.min'
            })))
            .pipe(sourcemaps.write())
            .pipe(gulp.dest(javascriptsDist))
});

gulp.task('htmlLocation', function () {
    // copy any html files in source/ to public/
    gutil.log('Gulp is copying html!');
    gulp.src(htmlsSrc)
            .pipe(connect.reload());
});

gulp.task('jshint', function () {
    return gulp.src(javascriptsSrc)
            .pipe(jshint())
            .pipe(jshint.reporter('jshint-stylish'));
})

gulp.task('watch', function () {
    gulp.watch(javascriptsSrc, ['jshint']);
    gulp.watch(javascriptsSrc, ['build-js']);
    gulp.watch(sasssheetsSrc, ['build-css']);
    gulp.watch(htmlsSrc, ['htmlLocation']);
});
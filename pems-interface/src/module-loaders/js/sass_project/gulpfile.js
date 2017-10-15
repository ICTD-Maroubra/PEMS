var gulp = require('gulp');
var sass = require('gulp-sass');
var watch = require('gulp-watch');
var debug = require('gulp-debug');
var cleanCSS = require('gulp-clean-css');
//var pump = require('pump');
var rename = require('gulp-rename');
var config = {
    bootstrapDir: './bower_components/bootstrap-sass',
    publicDir: 'assets/styles',
};
var autoprefixer = require('gulp-autoprefixer');

function onError(err) {
    console.log(err);
    // console.log(sourcemaps);
}



//gulp.task('default', function() {
// return gutil.log('Gulp is running!')
//});



//Auto Prefixer
//1. sass, prefixer, minify

gulp.task('default', ['watch-theme']);

gulp.task('watch', function() {
    gulp.watch('sass/**/*.scss', function() {
        console.log('Gulping...');
        gulp.start('build');
        console.log('Done!');
    });
});
gulp.task('watch-theme', function() {
    gulp.start('theme');
    gulp.watch('sass/**/*.scss', function() {
        console.log('Gulping...');
        gulp.start('theme');
        console.log('Done!');
    });
});

gulp.task('theme', function() {
    //gulp.start('watch');
    return gulp.src('./sass/theme.scss')
        .pipe(debug())
        .pipe(sass())
        .pipe(gulp.dest('css/')).pipe(debug());
    // .pipe(livereload({ start: true }));
});

gulp.task('prefixer', ['sass'], function() {
    return gulp.src('/css/main.css')
        .pipe(autoprefixer({
            browsers: ['last 2 versions'],
            cascade: false
        }))
        .pipe(gulp.dest('/css/'));
});


gulp.task('minify', ['prefixer'], function() {
    return gulp.src('/css/main.css')
        .pipe(cleanCSS({
            compatibility: 'ie8'
        }))
        .pipe(rename({
            suffix: '.min'
        }))
        .pipe(gulp.dest('/css/'));
});;

var gulp = require('gulp');
var jshint = require('gulp-jshint');
//var gif = require('gulp-if');
var uglify = require('gulp-uglify');
var less = require('gulp-less');
var size = require('gulp-size');
var cache = require('gulp-cache');
var imagemin = require('gulp-imagemin');
var concat = require('gulp-concat');
var rename = require('gulp-rename');
var header = require('gulp-header');  
var minifyCss = require('gulp-minify-css');
var plumber = require('gulp-plumber');  
var minifyhtml = require('gulp-minify-html');
var useref = require('gulp-useref');

var del = require('del');
var runSequence = require('run-sequence');
var browserSync = require('browser-sync');
var pagespeed = require('psi');
var reload = browserSync.reload;

var path = require('path');
var pkg = require('./package.json');

/* Prepare banner text */
var banner = ['/**',  
	' * <%= pkg.name %> v<%= pkg.version %>',
	' * <%= pkg.description %>',
	' * ',
	' * author <%= pkg.author.name %> <<%= pkg.author.email %>>',
	' */',
	''].join('\n');


// Lint JavaScript
gulp.task('jshint', function () {
    return gulp.src('assets/javascripts/**/*.js')
        .pipe(reload({stream: true, once: true}))
        .pipe(jshint())
        .pipe(jshint.reporter('jshint-stylish'))
        //.pipe(gif(!browserSync.active, jshint.reporter('fail')));
});

// Optimize images
gulp.task('images', function () {
    return gulp.src('images/**/*')
        .pipe(cache(imagemin({
            progressive: true,
            interlaced: true
        })))
        .pipe(gulp.dest('dist/images'))
        .pipe(size({title: 'images'}));
});

// Copy all files at the root level (app)
gulp.task('copy', function () {
    return gulp.src([
            'pages/*',
            '!pages/*.html',
            'node_modules/apache-server-configs/dist/.htaccess'
        ], {
        dot: true
    }).pipe(gulp.dest('dist'))
    .pipe(size({title: 'copy'}));
});

// Copy web fonts to dist
gulp.task('fonts', function () {
    return gulp.src(['assets/fonts/**'])
        .pipe(gulp.dest('dist/fonts'))
        .pipe(size({title: 'fonts'}));
});


// crio uma tarefa para gerar o less e o css
gulp.task('less', function () {
	return gulp.src("assets/stylesheets/**/*.less")
	//return gulp.src("assets/stylesheets/components/main.less")
		.pipe(plumber())
		.pipe(less({
			paths: [ path.join(__dirname, 'less', 'includes') ]
		}))
		.pipe(header(banner, {pkg: pkg}))
		.pipe(gulp.dest('app/styles/'));
        //.pipe(gulp.dest('dist/styles'))
        //.pipe($.size({title: 'styles'}));
});

// comprime o css
gulp.task('minify-css', function() {
	gulp.src(['assets/stylesheets/**/*.css'])
	//gulp.src(['assets/stylesheets/landerapp.min.css'])
		.pipe(minifyCss())
		//.pipe(gulp.dest('app/styles'))
        .pipe(gulp.dest('dist/styles/'))
        .pipe(size({title: 'styles'}));
});

// task
gulp.task('minify-js', function () {
    gulp.src('assets/javascripts/**/*.js')
        .pipe(uglify())
        .pipe(rename({ extname: '.min.js' }))
        //.pipe(gulp.dest('app/scripts/'))
        .pipe(gulp.dest('dist/scripts/'))
        .pipe(size({title: 'js'}));
});

// watch files
gulp.task('watch', function() {
	gulp.watch('assets/stylesheets/**/*.less', ['less']);
	gulp.watch('assets/stylesheets/**/*.css', ['minify-css']);
    gulp.watch('assets/javascripts/**/*.js', ['minify-js']);
});

gulp.task('concat', function() {
    gulp.src([// trocar esses arquivos pelos arquivos da sua pasta js menos o main.js
        'libs/jquery.min.js',
        'libs/jquery.bxslider.min.js',
        'libs/jquery.reel-min.js',
        'libs/modernizr.js',
        'libs/html5shiv.min.js',
        'libs/slideshow.js'
    ])
    .pipe(concat('all.js'))
    .pipe(uglify())
    //.pipe(gulp.dest('app/scripts/'))
    .pipe(gulp.dest('dist/scripts/'))
    .pipe(size({title: 'js-libs'}));
});

gulp.task('js', function() {
    return gulp.src(['assets/javascripts/**/*.js'])
    .pipe(gulp.dest('dist/scripts/'))
    .pipe(size({title: 'js'}));
});

// Clean output directory
gulp.task('clean', del.bind(null, ['.tmp', 'dist/*', '!dist/.git'], {dot: true}));

// Watch files for changes & reload
gulp.task('serve', ['less'], function () {
    browserSync({
        notify: false,
        // Customize the BrowserSync console logging prefix
        logPrefix: 'WSK',
        server: ['.tmp', '.']
    });

    gulp.watch(['pages/**/*.html'], reload);
    gulp.watch(['template/**/*.html'], reload);
    //gulp.watch(['assets/stylesheets/**/*.{less,css}'], ['less', reload]);
    //gulp.watch(['assets/javascripts/**/*.js'], ['jshint']);
    gulp.watch(['*.js'], ['jshint']);
    gulp.watch(['images/**/*'], reload);
});

// Build and serve the output from the dist build
gulp.task('serve:dist', ['default'], function () {
    browserSync({
        notify: false,
        logPrefix: 'WSK',
        server: 'dist'
    });
});


gulp.task('move', function() {
	var bower = './bower_components/';
	
    gulp.src([bower + 'jquery/dist/jquery.min.js',
		    bower + 'html5shiv/dist/html5shiv.min.js',
		    bower + 'bxslider-4/dist/jquery.bxslider.min.js',
		    bower + 'Reel/jquery.reel-min.js',
		    bower + 'modernizr/modernizr.js'])
        .pipe(gulp.dest('app/scripts/libs'));// mude a pasta para onde os arquivos vão
});

// Scan your HTML for assets & optimize them
gulp.task('html', function () {
    //var assets = useref.assets({searchPath: '{.tmp,app}'});
    
    //return gulp.src('app/**/*.html')
    //.pipe(assets)
    // Concatenate and minify JavaScript
    //.pipe(gif('*.js', uglify({preserveComments: 'some'})))
    //.pipe(gif('*.html', minifyHtml()))
    //.pipe(gulp.dest('dist'))
    //.pipe(size({title: 'html'}));
    
    return gulp.src('pages/**/*.html')
        .pipe(minifyhtml())
        .pipe(gulp.dest('./dist'))
        
        //.pipe(gulp.dest('./dist'))
        .pipe(size({title: 'html'}));
});

// Build production files, the default task
gulp.task('default', ['clean'], function (cb) {
    runSequence('less', ['html', 'concat', 'minify-js', 'minify-css', 'images', 'fonts', 'copy'], cb);
});

// Run PageSpeed Insights
gulp.task('pagespeed', function (cb) {
    // Update the below URL to the public URL of your site
    pagespeed.output('www.altamira.com.br', {
        strategy: 'mobile',
    }, cb);
});


var debug = process.env.NODE_ENV !== "production";
var webpack = require('webpack');

module.exports = {
  context: __dirname, //if placed in different folder then modify this
  devtool: debug ? "inline-sourcemap" : null,
  entry: "./scripts.js",
  module: {
   loaders: [
     {
       test: /\.jsx?$/, //any js files are run via babel loader
       exclude: /(node_modules|bower_components)/,
       //loaders: ['react-hot', 'babel-loader?presets[]=react,presets[]=es2015'],
       loader: 'babel-loader',
       query: {
         presets: ['react', 'es2015', 'stage-0'],
         plugins: ['react-html-attrs', 'transform-decorators-legacy', 'transform-class-properties'],
       }
     }
   ]
 },
  output: {
    path: __dirname,
    filename: "scripts.min.js"
  },
  plugins: debug ? [] : [
    new webpack.optimize.DedupePlugin(), //strip duplicate code
    new webpack.optimize.OccurenceOrderPlugin(),
    new webpack.optimize.UglifyJsPlugin({ mangle: false, sourcemap: false }),
  ],
};

const path = require("path");
const HtmlWebpackPlugin = require("html-webpack-plugin");
module.exports = {
  mode: "development",
  entry: {
    index: "./public/javascript/index/main.js",
    signup: "./public/javascript/signup/main.js",
    room: "./public/javascript/room/main.js",
    calander: "./public/javascript/calander/main.js",
  },
  output: {
    path: path.resolve(__dirname, "public/javascript/webpack"),
    filename: "[name]_bundle.js",
  },
  module: {
    rules: [
      {
        test: /\.css$/,
        use: ["style-loader", "css-loader"],
      },
      {
        test: /\.scss$/,
        use: ["style-loader", "css-loader", "sass-loader"],
      },
    ],
  },
  // plugins: [
  //   new HtmlWebpackPlugin({
  //     template: "./views/index.ejs", //참고할 html파일
  //     filename: "index.ejs", //만들어질 html파일
  //     chunks: ["index"], //원하는 필요로 하는 js만 import하게 설정
  //   }),
  // ],
};

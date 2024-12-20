const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const webpack = require('webpack');
const dotenv = require('dotenv');

// Load environment variables from the appropriate `.env` file
const result = dotenv.config({
    path: `.env.${process.env.NODE_ENV}`  // Will load `.env.development` or `.env.production`
});
if (result.error) {
    console.error(result.error);
}

module.exports = {
    entry: './index.js',
    output: {
        filename: 'bundle.js',
        path: path.resolve(__dirname, 'dist'),
    },
    mode: process.env.NODE_ENV || 'development', // Default to 'development' if NODE_ENV is not set
    plugins: [
        new HtmlWebpackPlugin({
            template: './index.html', // Ensures index.html is copied to dist
        }),
        // Define environment variables that can be used in the app
        new webpack.DefinePlugin({
            'process.env.KEYCLOAK_URL': JSON.stringify(process.env.KEYCLOAK_URL),
            'process.env.BACKEND_URL': JSON.stringify(process.env.BACKEND_URL),
        }),
    ],
};

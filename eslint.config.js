
module.exports = [
    {
        files: ["**/*.js"],
        ignores: ["node_modules/**", "dist/**"],
        languageOptions: {
            ecmaVersion: "latest",
            sourceType: "commonjs",
            globals: {
                console: "readonly",
                process: "readonly",
                __dirname: "readonly",
                require: "readonly",
                module: "readonly"
            }
        },
        rules: {
            "no-unused-vars": "error",
            "no-undef": "error",
            "eqeqeq": "error"
        }
    }
];


.PHONY: build test clean

build:
	npm.cmd ci
	node build.js


test:
	node --test
	node node_modules/eslint/bin/eslint.js build.js eslint.config.js tests/app.test.js


clean:
	node -e "require('fs').rmSync('dist', { recursive: true, force: true })"

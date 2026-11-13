
const test = require("node:test");
const assert = require("node:assert/strict");
const fs = require("node:fs");
const path = require("node:path");

const fichier = path.join(__dirname, "..", "frontend", "index.html");

test("Le fichier HTML de GestiTaches existe", () => {
    assert.equal(fs.existsSync(fichier), true);
});

test("Le formulaire de creation de tache existe", () => {
    const html = fs.readFileSync(fichier, "utf8");
    assert.match(html, /id="form-tache"/);
});

test("La liste des taches existe", () => {
    const html = fs.readFileSync(fichier, "utf8");
    assert.match(html, /id="liste-taches"/);
});

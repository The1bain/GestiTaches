
const fs = require("node:fs");
const path = require("node:path");

const source = path.join(__dirname, "frontend", "index.html");
const destination = path.join(__dirname, "dist", "index.html");

if (!fs.existsSync(source)) {
  console.error("Erreur : frontend/index.html est introuvable.");
  process.exit(1);
}

fs.mkdirSync(path.dirname(destination), { recursive: true });
fs.copyFileSync(source, destination);

console.log("Build termine avec succes !");
console.log("Artefact genere : dist/index.html");

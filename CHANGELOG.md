# Changelog — GestiTaches

## [1.0.0-rc.1] — Release Candidate

### Ajouté
- Endpoint `/actuator/health` servi par Nginx.
- Journalisation des créations et retours de prêts en Java.
- Formateur de logs JSON et fichier `logging.properties`.
- Configuration Dependabot pour Maven, npm et Docker.
- SBOM CycloneDX au format JSON et XML.
- Documentation de préparation de release dans `RELEASE.md`.

### Vérifié
- 15 tests Maven réussis, sans échec.
- Application Web accessible sur `http://localhost:8080`.
- PostgreSQL démarré avec Docker Compose.
- Protection du fichier `.env` par Git.

### Limitations connues
- L'endpoint de santé est statique et ne détecte pas les pannes PostgreSQL.
- Le frontend n'est pas encore connecté à PostgreSQL.
- Les logs JSON Java sont validés pendant les tests Maven, mais ne sont pas encore intégrés au conteneur Nginx.
- Le SBOM couvre les dépendances Maven, pas l'intégralité des composants npm et Docker.

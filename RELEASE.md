
# TD5 — Release Candidate

## Partie 1 — Endpoint de santé

### Question 1.4 — Que se passe-t-il lorsque PostgreSQL est arrêté ?

Après avoir exécuté `docker compose stop db`, l'endpoint
`http://localhost:8080/actuator/health` continue de retourner
`{"status":"UP"}`.

Cela s'explique par l'architecture actuelle de GestiTaches :
le frontend est servi par Nginx et l'endpoint de santé est un
fichier JSON statique. Il ne vérifie pas la disponibilité de PostgreSQL.

Un véritable endpoint de santé dynamique permettrait de détecter
la panne d'une dépendance essentielle et de signaler que
l'application ne fonctionne plus correctement.

Cette vérification est utile pour la supervision, la détection
des incidents et le diagnostic des problèmes.

### Limitation connue

L'endpoint actuel confirme uniquement que Nginx peut servir
le fichier de santé. Il ne garantit pas la disponibilité de
PostgreSQL ni le bon fonctionnement de l'ensemble de l'application.

Cette limitation est suivie dans l'issue #39.

## Partie 2 — Logs structurés en JSON

### Configuration

La classe `JsonLogFormatter` permet de produire des journaux
structurés au format JSON avec les champs `timestamp`, `level`,
`logger` et `message`.

La configuration est définie dans `logging.properties`.

### Événements journalisés

La méthode `emprunter()` enregistre un événement lors de
la création d'un prêt.

La méthode `retourner()` enregistre un événement lors du
retour d'un prêt.

Les messages contiennent uniquement les identifiants techniques
du prêt et du matériel.

### Vérification

Les événements ont été retrouvés dans les rapports XML
de Maven Surefire.

Les tests Maven ont réussi avec 15 tests exécutés,
0 échec et 0 erreur.

### Question 2.4 — Avantages des logs JSON

Les logs JSON sont plus faciles à lire et à analyser
automatiquement par des outils de supervision.

Contrairement aux logs en texte libre, ils utilisent une structure
avec des champs comme `timestamp`, `level`, `logger` et `message`.

Cela facilite la recherche des erreurs et le diagnostic.

Il ne faut jamais enregistrer dans les logs :
- Les mots de passe.
- Les jetons d'authentification ou les clés API.

### Limitation connue

Les logs JSON concernent actuellement le service Java
exécuté pendant les tests Maven.

Le conteneur Web utilise Nginx et ne produit pas encore
les événements Java de création et de retour des prêts.

Cette limitation est suivie dans l'issue #40.

## Partie 3 — Sécurité et gestion des secrets

### Protection des variables d'environnement

Le fichier `.env` contient les variables de configuration locales,
notamment les identifiants PostgreSQL.

La commande `git ls-files` a confirmé que `.env` n'est pas
suivi par Git.

La commande `git check-ignore` a confirmé que `.env` est protégé
par une règle du fichier `.gitignore`.

### Recherche des secrets

Une recherche a été effectuée avec `git grep` pour identifier
les références aux mots de passe, jetons et clés API dans
les fichiers suivis par Git.

La seule correspondance trouvée était :
`POSTGRES_PASSWORD=change_me` dans `.env.example`.

Cette valeur est fictive et sert uniquement d'exemple.

La commande `git log --all -- .env` n'a affiché aucun commit.

### Question 3.3 — Stockage des secrets dans GitHub

Les secrets nécessaires au pipeline CI doivent être stockés
dans GitHub Actions Secrets, dans les paramètres du dépôt :

`Settings > Secrets and variables > Actions`

Ils peuvent ensuite être utilisés dans les workflows sans écrire
directement leurs valeurs dans le code.

### Question 3.4 — Mot de passe publié par erreur

Non, supprimer le fichier dans un nouveau commit ne suffit pas.

Le mot de passe peut toujours être présent dans l'historique Git.

Il faut :
- Révoquer ou changer immédiatement le mot de passe compromis.
- Vérifier les accès et les utilisations suspectes.
- Retirer le secret de l'historique Git si nécessaire.
- Vérifier qu'aucun autre secret n'a été exposé.

### Conclusion

Aucun véritable secret n'a été identifié lors des vérifications
effectuées sur les fichiers suivis.

Le fichier `.env` est ignoré par Git et aucun commit contenant
ce fichier n'a été trouvé avec la commande utilisée.

La protection contre l'envoi de secrets est également activée
dans les paramètres du dépôt GitHub.

## Partie 4 — Dependabot et SBOM

### Configuration de Dependabot

Le fichier `.github/dependabot.yml` configure la surveillance
hebdomadaire des dépendances :
- Maven.
- GitHub Actions.
- npm.
- Docker.

La configuration est publiée sur GitHub.

Dependabot alerts et Dependabot security updates sont activés.

Lors de la vérification, une alerte modérée concernant la
dépendance transitive `minimatch` était ouverte.

Aucune alerte critique n'était visible.

Cette vulnérabilité est suivie dans l'issue #41.

### Génération du SBOM

Un inventaire des composants logiciels a été généré
avec le plugin CycloneDX Maven, version 2.9.1.

Les fichiers suivants ont été créés :
- `sbom/bom.json`
- `sbom/bom.xml`

Le pipeline CI génère également les fichiers :
- `target/bom.json`
- `target/bom.xml`

Ces derniers sont publiés comme artefact `sbom`
dans GitHub Actions.

Le rapport JaCoCo est publié séparément sous
le nom `rapport-jacoco`.

### Question 4.5 — Définition et utilité du SBOM

Un SBOM (Software Bill of Materials) est un inventaire
des composants logiciels et des dépendances d'une application.

Il permet notamment :
- D'identifier les bibliothèques utilisées.
- De repérer les composants présentant des vulnérabilités.
- De faciliter les mises à jour et les audits de sécurité.

### Limitation connue

Le SBOM généré couvre la partie Maven du projet.

Il ne constitue pas un inventaire complet des dépendances
npm ni des composants des images Docker.

## Partie 5 — README et notes de version

### Documentation

Les fichiers suivants sont présents dans le dépôt :
- `README.md` : présentation et utilisation du projet.
- `CHANGELOG.md` : historique des changements.
- `RELEASE.md` : réponses et vérifications du TD5.

### Versions publiées

La Release Candidate `v1.0.0-rc.1` a été créée et publiée
sur GitHub en tant que prépublication, conformément au TD5.

Des corrections supplémentaires ont ensuite été réalisées.

La Release Candidate `v1.0.0-rc.3` a également été publiée
en tant que prépublication.

Cette dernière correspond à la version Maven `1.0.0-rc.3`
et au commit de `main` au moment de sa création.

### Question 5.4 — Signification de -rc.1

Selon le versionnement sémantique (SemVer), le suffixe
`-rc.1` signifie Release Candidate 1.

Il s'agit de la première version candidate avant
la publication d'une version stable.

La version peut encore contenir des problèmes à corriger
avant la version définitive `1.0.0`.

## Partie 6 — Check-list de livraison

### Vérifications

- [x] `docker compose up` démarre l'application sans erreur.
- [x] `/actuator/health` répond `UP`.
- [ ] Les logs JSON sont disponibles dans l'application déployée
      et ne contiennent aucune donnée personnelle.
- [x] Aucun secret identifié lors des vérifications du dépôt.
- [x] Pipeline CI vert : tests Maven, couverture JaCoCo et SBOM.
- [x] Aucune alerte Dependabot critique ouverte lors de la vérification.
- [x] README et CHANGELOG à jour.
- [x] Tag `v1.0.0-rc.1` et Release GitHub créés.

### Limitations et issues de suivi

- Issue #39 : l'endpoint de santé est statique.
  Il retourne `UP` même lorsque PostgreSQL est arrêté.

- Issue #40 : les logs JSON sont validés pendant les tests Maven,
  mais ne sont pas encore produits par le service Web déployé.

- Issue #41 : une vulnérabilité modérée concernant
  la dépendance `minimatch` reste ouverte.

### Livrables

- [x] `RELEASE.md` contient les réponses aux questions
      1.4, 2.4, 3.3, 3.4, 4.5 et 5.4.
- [x] Configuration Dependabot, plugin CycloneDX
      et artefact `sbom` dans GitHub Actions.
- [x] `README.md` et `CHANGELOG.md` complétés.
- [x] Tag `v1.0.0-rc.1` et Release GitHub marquée pre-release.
- [x] Issues créées pour les limitations non résolues.

### Version supplémentaire

La Release Candidate `v1.0.0-rc.3` a été publiée
après les dernières corrections du TD5.

Elle conserve les fonctionnalités existantes et intègre
les améliorations de la documentation, du pipeline CI
et de la génération du SBOM.

Les limitations restantes sont documentées dans
les issues GitHub #39, #40 et #41.

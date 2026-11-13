
# GestiTaches

## Description du projet

GestiTaches est une application web de gestion de tâches personnelles. Elle permet aux utilisateurs de créer des tâches avec un titre, une description et une date d'échéance, de les consulter, de les modifier, de les marquer comme terminées et de les supprimer.

Le projet comprend un frontend pour l'interface utilisateur et un backend prévu pour l'API et le stockage des tâches.

## Membres de l'équipe et responsabilités

Le projet GestiTaches est réalisé individuellement, avec l'autorisation du professeur.

**The1bain — Responsable unique du projet**

J'assure les responsabilités suivantes :

- Gestion du dépôt GitHub, des branches et des Pull Requests.
- Développement du frontend et du backend.
- Configuration du build et des outils de qualité du code.
- Création et exécution des tests automatisés.
- Gestion du backlog, des issues et des priorités.
- Rédaction et mise à jour de la documentation.

Les vérifications sont réalisées personnellement et ne constituent pas des revues indépendantes.

## Stratégie de branchement

La stratégie choisie est GitHub Flow. Elle est adaptée à ce projet individuel car elle est simple et permet de développer chaque fonctionnalité sur une branche séparée avant de la fusionner dans la branche principale via une Pull Request.

## Convention de nommage des branches

- `main` : branche principale et stable du projet.
- `feature/nom-court` : ajout d'une fonctionnalité.
- `fix/nom-court` : correction d'un bogue.
- `docs/nom-court` : modification de la documentation.
- `test/nom-court` : ajout ou vérification de tests.

Chaque modification est effectuée sur une branche dédiée, puis intégrée à `main` à travers une Pull Request.

## Build et tests - TD 2

### Prérequis

- Node.js et npm.
- GNU Make.
- Git.

### Installation des dépendances

Depuis la racine du projet :

```bash
npm install
```

Cette commande installe les dépendances nécessaires au projet.

### Construction du projet

```bash
make build
```

Cette commande :

1. Installe les dépendances avec `npm.cmd ci`.
2. Exécute le script `build.js`.
3. Génère l'artefact `dist/index.html` à partir de `frontend/index.html`.

L'artefact généré a été vérifié localement :

- **Chemin relatif :** `dist/index.html`
- **Taille mesurée :** 1 019 octets.

La commande `npm.cmd ci` est utilisée pour l'environnement Windows du TD 2.

### Exécution des tests

```bash
make test
```

Cette commande exécute trois tests automatisés avec Node.js Test Runner et vérifie la qualité du code JavaScript avec ESLint.

Résultats obtenus :

- 3 tests exécutés.
- 3 tests réussis.
- 0 test échoué.
- ESLint exécuté sans erreur.

Les tests vérifient :

1. L'existence du fichier HTML de GestiTaches.
2. La présence du formulaire de création de tâche.
3. La présence de la section contenant la liste des tâches.

### Nettoyage des fichiers générés

```bash
make clean
```

Cette commande supprime le dossier `dist/` et son contenu.

### Qualité du code

Le projet utilise :

- **EditorConfig** pour uniformiser le format des fichiers.
- **ESLint** pour vérifier le code JavaScript.
- **Node.js Test Runner** pour exécuter les tests automatisés.

Les dossiers `dist/` et `node_modules/` sont exclus du dépôt Git grâce au fichier `.gitignore`.

Une vérification avec ESLint a également été réalisée dans le cadre du TD 2.

Une variable inutilisée a été ajoutée temporairement dans `build.js` pour démontrer la détection de la règle `no-unused-vars`.

ESLint a détecté une erreur. Après suppression de la variable, une nouvelle exécution du linter n'a signalé aucune erreur.

Cette démonstration a été réalisée sur une branche temporaire, puis le fichier original a été restauré.

### Vérification des dépendances

Un audit npm a été effectué.

Résultats :

- Aucune vulnérabilité détectée dans les dépendances de production.
- Trois vulnérabilités modérées signalées dans les dépendances de développement au moment de la vérification.

Ces vulnérabilités restent à examiner séparément.

### Limites actuelles

Les tests automatisés vérifient uniquement la structure HTML de l'application.

L'enregistrement, la modification, la suppression et l'affichage dynamique des tâches ne sont pas encore implémentés.

Ces fonctionnalités seront développées dans les prochaines étapes.

## Portée du projet - TD 2

### Objectif du projet

GestiTaches est une application web destinée à permettre aux utilisateurs de gérer leurs tâches personnelles.

L'application doit permettre de créer, consulter, modifier, supprimer et organiser les tâches.

### Fonctionnalités incluses

Le périmètre du projet comprend huit fonctionnalités principales.

| Priorité | Fonctionnalité | Issue | Sprint |
|----------|----------------|-------|--------|
| Haute | Créer une tâche | #5 | Sprint 1 |
| Haute | Afficher la liste des tâches | #6 | Sprint 1 |
| Haute | Modifier une tâche | #9 | Sprint 1 |
| P1 | Consulter les détails d'une tâche | #10 | Sprint 1 |
| P2 | Marquer une tâche comme terminée | #7 | Sprint 2 |
| P2 | Supprimer une tâche | #8 | Sprint 2 |
| P2 | Filtrer les tâches par statut | #11 | Sprint 2 |
| P2 | Rechercher une tâche par titre | #12 | Sprint 2 |

Les issues #5, #6 et #9 sont les trois fonctionnalités considérées comme les plus prioritaires. Elles possèdent le label `priorité : haute` dans GitHub.

Les autres fonctionnalités sont organisées selon leur sprint de développement.

### Organisation du développement

**Sprint 1 : Fonctionnalités principales**

- Créer une tâche.
- Afficher la liste des tâches.
- Modifier une tâche.
- Consulter les détails d'une tâche.

**Sprint 2 : Finalisation et tests**

- Marquer une tâche comme terminée.
- Supprimer une tâche.
- Filtrer les tâches par statut.
- Rechercher une tâche par titre.
- Finaliser les tests et la documentation.

### Gestion du backlog

Le backlog est organisé dans GitHub Projects.

Chaque issue comprend :

- Une description de la fonctionnalité.
- Une justification de son utilité.
- Une user story.
- Des critères d'acceptation vérifiables.
- Une solution technique proposée.
- Un responsable.
- Un jalon de sprint.
- Un statut de suivi.

Les huit user stories ont été examinées dans le cadre du TD 2.

Les trois fonctionnalités prioritaires ont été placées en tête du tableau GitHub Projects.

### Fonctionnalités exclues du périmètre

Les fonctionnalités suivantes ne sont pas prévues pour cette première version :

- Authentification des utilisateurs.
- Gestion des équipes et des rôles.
- Notifications automatiques.
- Application mobile native.
- Synchronisation avec un calendrier externe.

### État actuel du projet

L'interface HTML de base est créée.

Le système de build permet d'installer les dépendances et de générer l'artefact HTML.

Les trois tests automatisés de structure fonctionnent et ESLint est configuré.

Le backlog contient huit user stories documentées et priorisées.

Les fonctionnalités dynamiques de gestion des tâches restent à développer.

## Suivi GitHub - TD 2

Le travail du TD 2 a été intégré à la branche principale `main` au moyen de Pull Requests.

- Pull Request #18 : configuration du build, des tests et de la qualité du code.
- Pull Request #19 : amélioration du Makefile pour installer les dépendances pendant le build.

Les deux Pull Requests ont été fusionnées avec succès.

### Liens du projet

- Dépôt GitHub : https://github.com/The1bain/GestiTaches
- Backlog GitHub Projects : https://github.com/users/The1bain/projects/1/views/2
- Pull Request #18 : https://github.com/The1bain/GestiTaches/pull/18
- Pull Request #19 : https://github.com/The1bain/GestiTaches/pull/19

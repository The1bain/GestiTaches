# GestiTaches

## Description du projet
GestiTaches est une application web de gestion de tâches personnelles. Elle permet aux utilisateurs de créer des tâches avec un titre, une description et une date d'échéance, de les marquer comme terminées et de les supprimer. Le projet comprend un frontend pour l'interface utilisateur et un backend pour l'API et le stockage des tâches.

## Membres de l'équipe
- The1bain — Responsable du dépôt GitHub et du projet en entier

## Stratégie de branchement

La stratégie choisie est GitHub Flow. Elle est adaptée à ce projet individuel car elle est simple et permet de développer chaque fonctionnalité sur une branche séparée avant de la fusionner dans la branche principale via une Pull Request.

## Convention de nommage des branches

- `main` : branche principale et stable du projet.
- `feature/nom-court` : ajout d'une fonctionnalité.
- `fix/nom-court` : correction d'un bogue.
- `docs/nom-court` : modification de la documentation.

Chaque modification sera effectuée sur une branche dédiée, puis intégrée à `main` à travers une Pull Request.


## Build et tests - TD 2

### Prérequis

- Node.js et npm
- GNU Make

### Installation des dépendances

Depuis la racine du projet :

```bash
npm install
```

### Construction du projet

```bash
make build
```

Cette commande exécute le script `build.js` et génère
l'artefact `dist/index.html` à partir de `frontend/index.html`.

### Exécution des tests

```bash
make test
```

Cette commande exécute trois tests automatisés et
vérifie la qualité du code JavaScript avec ESLint.

### Nettoyage des fichiers générés

```bash
make clean
```

Cette commande supprime le dossier `dist`.

### Qualité du code

Le projet utilise :
- EditorConfig pour uniformiser le format des fichiers.
- ESLint pour vérifier le code JavaScript.
- Node.js Test Runner pour les tests automatisés.

Les dossiers `dist/` et `node_modules/` sont exclus
du dépôt Git grâce au fichier `.gitignore`.

### Limites actuelles

Les tests automatisés vérifient la structure HTML.
L'enregistrement et l'affichage dynamique des tâches
seront implémentés dans une étape ultérieure.


## Portée du projet - TD 2

### Objectif du projet

GestiTaches est une application web destinée à permettre aux utilisateurs de gérer leurs tâches personnelles.

L'application doit permettre de créer, consulter, modifier, supprimer et organiser les tâches.

### Fonctionnalités incluses

Le périmètre du projet comprend huit fonctionnalités principales.

| Priorité | Fonctionnalité | Issue |
|----------|----------------|-------|
| P1 | Créer une tâche | #5 |
| P1 | Afficher la liste des tâches | #6 |
| P1 | Modifier une tâche | #9 |
| P1 | Consulter les détails d'une tâche | #10 |
| P2 | Marquer une tâche comme terminée | #7 |
| P2 | Supprimer une tâche | #8 |
| P2 | Filtrer les tâches par statut | #11 |
| P2 | Rechercher une tâche par titre | #12 |

### Organisation du développement

Sprint 1 : développement des fonctionnalités prioritaires P1.

Sprint 2 : développement des fonctionnalités complémentaires P2, finalisation et tests.

### Fonctionnalités exclues du périmètre

Les fonctionnalités suivantes ne sont pas prévues pour cette première version :

- Authentification des utilisateurs.
- Gestion des équipes et des rôles.
- Notifications automatiques.
- Application mobile native.
- Synchronisation avec un calendrier externe.

### État actuel du projet

L'interface HTML de base est créée.

Le système de build et les tests automatisés de structure sont configurés.

Les fonctionnalités dynamiques de gestion des tâches restent à développer.

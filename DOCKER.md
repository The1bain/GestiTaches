
# TD 4 — Docker et Docker Compose

## Présentation
GestiTaches est une application web de gestion de tâches personnelles.

Le frontend est construit avec Node.js et servi par Nginx dans un conteneur Docker.

## Prérequis
- Docker Desktop
- Docker Compose
- Git

## Construction de l'image
Commande :
`docker build -t gestitaches:td4 .`

## Démarrage avec Docker
Commande :
`docker run -d --name gestitaches-web -p 8080:80 gestitaches:td4`

## Démarrage avec Docker Compose
Commande :
`docker compose up -d --build`

## Vérification
Commande :
`docker compose ps`

L'application est accessible sur :
http://localhost:8080

## Arrêt des conteneurs
Commande :
`docker compose down`

## Architecture
Le Dockerfile utilise deux étapes :
1. Node.js 22 Alpine pour construire l'application.
2. Nginx Alpine pour servir les fichiers générés.

## Résultats
- Image Docker construite avec succès.
- Conteneur Docker démarré.
- Application accessible dans le navigateur.
- Configuration Docker Compose validée.
- Service Docker Compose démarré avec succès.

## Conclusion
Docker permet de déployer GestiTaches dans un environnement reproductible. Docker Compose simplifie la gestion du conteneur.

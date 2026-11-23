
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


## Réponses aux questions du TD 4

### 1.1 Différence entre une image et un conteneur
Une image Docker est un modèle qui contient les fichiers et les dépendances nécessaires pour exécuter une application. Un conteneur est une instance de cette image qui peut être démarrée, arrêtée ou supprimée.

### 1.2 Utilité d'un tag
Un tag permet d'identifier une version précise d'une image Docker, par exemple `gestitaches:0.1.0`. Il permet de distinguer les différentes versions d'une application.

### 1.3 Couche d'image
Une couche est une partie du système de fichiers d'une image Docker. Les couches peuvent être réutilisées entre plusieurs images, ce qui accélère les constructions et économise de l'espace.

### 3.2 Pourquoi ne pas copier un fichier .env ?
Un fichier `.env` peut contenir des informations sensibles comme des mots de passe. Le copier dans une image Docker risque d'exposer ces informations aux personnes ayant accès à l'image.

### 4.4 Signification de -p 8081:8080
Le premier nombre, 8081, représente le port de l'ordinateur. Le second, 8080, représente le port du conteneur. L'application serait accessible à l'adresse `http://localhost:8081`.

### 5.4 a) Pourquoi db et non localhost ?
Dans Docker Compose, `db` représente le nom du service PostgreSQL. Les conteneurs peuvent communiquer entre eux grâce aux noms des services sur le réseau Docker.

### 5.4 b) Différence entre docker compose down et down -v
`docker compose down` arrête et supprime les conteneurs ainsi que les réseaux créés par Compose. La commande `docker compose down -v` supprime également les volumes associés, ce qui peut entraîner la perte des données de PostgreSQL.

### 5.4 c) depends_on garantit-il que PostgreSQL est prêt ?
Non. `depends_on` garantit uniquement l'ordre de démarrage des services. Pour vérifier que PostgreSQL est réellement prêt, il faut utiliser un `healthcheck` avec la condition `service_healthy`.

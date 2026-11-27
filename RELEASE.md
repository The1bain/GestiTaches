# TD5 — Release Candidate

## Partie 1 — Endpoint de santé

### Question 1.4 — Que se passe-t-il lorsque PostgreSQL est arrêté ?

Après avoir exécuté `docker compose stop db`, l'endpoint
`http://localhost:8080/actuator/health` continue de retourner
`{"status":"UP"}`.

Cela s'explique par l'architecture actuelle de GestiTaches :
le frontend est servi par Nginx et l'endpoint de santé est
un fichier JSON statique. Il ne vérifie pas la disponibilité
de PostgreSQL.

Un véritable endpoint de santé dynamique permettrait de
détecter la panne d'une dépendance essentielle et de signaler
que l'application ne fonctionne plus correctement.

Cette vérification est utile pour la supervision, la détection
des incidents et le diagnostic des problèmes.

### Limitation connue

L'endpoint actuel confirme uniquement que Nginx peut servir
le fichier de santé. Il ne garantit pas la disponibilité
de PostgreSQL ni le bon fonctionnement de l'ensemble
de l'application.

## Partie 2 — Logs structurés en JSON

### Configuration

La classe JsonLogFormatter permet de produire des journaux
structurés au format JSON avec les champs timestamp, level,
logger et message.

La configuration est définie dans logging.properties.

### Événements journalisés

La méthode emprunter() enregistre un événement lors de la
création d'un prêt.

La méthode retourner() enregistre un événement lors du
retour d'un prêt.

Les messages contiennent uniquement les identifiants
techniques du prêt et du matériel.

### Vérification

Les événements ont été retrouvés dans les rapports XML
de Maven Surefire.

Les tests Maven ont réussi avec 15 tests exécutés,
0 échec et 0 erreur.

### Limitation

Les logs JSON concernent actuellement le service Java
exécuté pendant les tests Maven.

Le conteneur Web utilise Nginx et ne produit pas encore
les événements Java de création et de retour des prêts.

## Partie 3 — Sécurité et gestion des secrets

### Protection des variables d'environnement

Le fichier .env contient les variables de configuration locales,
notamment les identifiants PostgreSQL.

La commande git ls-files a confirmé que .env n'est pas suivi
par Git.

La commande git check-ignore a confirmé que .env est protégé
par une règle du fichier .gitignore.

### Recherche des secrets

Une recherche a été effectuée avec git grep pour identifier
les références aux mots de passe, jetons et clés API dans
les fichiers suivis par Git.

La seule correspondance trouvée était :
POSTGRES_PASSWORD=change_me dans .env.example.

Cette valeur est fictive et sert uniquement d'exemple.

### Conclusion

Aucun véritable secret n'a été identifié dans les fichiers
suivis lors de cette vérification.

La vérification des nouveaux fichiers et de l'historique Git
reste à effectuer.

## Partie 4 — Dependabot et SBOM

### Configuration de Dependabot

Le fichier .github/dependabot.yml configure la surveillance
hebdomadaire des dépendances Maven, npm et Docker.

Dependabot pourra proposer des mises à jour des dépendances
lorsque cette configuration sera publiée sur GitHub.

### Génération du SBOM

Un inventaire des composants logiciels a été généré
avec le plugin CycloneDX Maven.

Les fichiers suivants ont été créés :
- sbom/bom.json
- sbom/bom.xml

Ces fichiers permettent de répertorier les composants
et les dépendances Maven du projet.

### Limitation

Le SBOM généré couvre la partie Maven du projet.
Il ne constitue pas un inventaire complet des dépendances
npm ni des composants des images Docker.


# GestiTaches — TD 3 : Tests et couverture

## 1. Organisation des tests

### Différence entre mvn test et mvn verify

La commande `mvn test` exécute les tests unitaires avec Maven Surefire.

La commande `mvn verify` exécute les tests unitaires et les tests d'intégration avec Failsafe. Elle permet aussi de vérifier les seuils de couverture du code avec JaCoCo.

Les tests unitaires sont nommés `*Test.java` et les tests d'intégration `*IT.java`.

## 2. Tests unitaires des pénalités

### Pourquoi tester les valeurs 14, 15 et 16 ?

Ces valeurs correspondent à la technique des tests aux valeurs limites.

La pénalité est de 2 $ par jour de retard, avec un maximum de 30 $.

- 14 jours : pénalité de 28 $.
- 15 jours : pénalité de 30 $.
- 16 jours : pénalité toujours limitée à 30 $.

Ces tests vérifient que le plafond est appliqué correctement.

## 3. Mockito et contrôle du temps

### Pourquoi utiliser Clock au lieu de LocalDate.now() ?

L'utilisation directe de `LocalDate.now()` peut rendre les tests instables, car le résultat dépend de la date réelle d'exécution.

Avec `Clock.fixed()`, on utilise une date fixe. Les tests donnent donc des résultats reproductibles, indépendamment du jour où ils sont exécutés.

Mockito permet également de simuler les dépôts afin de tester les règles métier sans utiliser une véritable base de données.

## 4. Tests d'intégration

Le test `ServicePretIT.java` vérifie un scénario complet sans Mockito :

- Emprunt d'un matériel le 1er novembre 2026.
- Durée de prêt de 7 jours.
- Échéance le 8 novembre 2026.
- Retour le 20 novembre 2026.
- Retard de 12 jours.
- Pénalité calculée : 24 $.
- Le matériel redevient disponible.
- Aucun prêt actif ne reste pour l'emprunteur.

Le test d'intégration a été exécuté avec succès par `mvn verify`.

## 5. Couverture de code JaCoCo

### Résultats obtenus

| Indicateur | Résultat |
|---|---|
| Tests unitaires | 15 réussis |
| Tests d'intégration | 1 réussi |
| Total des tests | 16 |
| Couverture des instructions | 98 % |
| Couverture des branches | 100 % |
| Instructions non couvertes | 5 sur 315 |
| Branches non couvertes | 0 sur 14 |
| Lignes non couvertes | 1 sur 78 |
| Couverture des instructions de ServicePret | 97 % |
| Couverture des branches de ServicePret | 100 % |
| Vérification JaCoCo | BUILD SUCCESS |

Les seuils imposés sont de 80 % pour les lignes et de 70 % pour les branches.

JaCoCo confirme que tous les seuils sont respectés.

### Une couverture de 100 % garantit-elle l'absence de bogues ?

Non. Une couverture de 100 % signifie que le code a été exécuté pendant les tests, mais cela ne garantit pas que tous les comportements possibles ont été correctement vérifiés.

Par exemple, dans `ServicePret`, les tests pourraient exécuter toutes les branches du calcul des pénalités sans vérifier toutes les valeurs possibles ou certains cas particuliers.

Il faut donc combiner la couverture avec des assertions pertinentes, des tests aux limites et des tests d'intégration.

## 6. Commandes utilisées

Compilation :

`mvn compile`

Tests unitaires :

`mvn test`

Tests unitaires, intégration et couverture :

`mvn clean verify`

Rapport JaCoCo :

`target/site/jacoco/index.html`

## 7. Conclusion

Les 15 tests unitaires et le test d'intégration ont été exécutés avec succès.

La couverture mesurée atteint 98 % des instructions et 100 % des branches.

Les seuils de couverture exigés sont respectés.

Le projet est prêt pour la configuration et la vérification du pipeline CI GitHub Actions.

<div align="center" style="margin-bottom:50px">

[![Money et Dystopie](moneyetdystopie-logo.jpg)](https://github.com/M2DL-IVVQ-DevOps/ivvq-projet-2021-money-et-dystopie)

# **Money et Dystopie**

**L'argent n'apporte pas forcément le bonheur. Mais des fois oui quand même.**

</div>

# Description du projet

Le projet consiste en un site marchant sur lequel il est possible d'acheter de l'argent.

# Décomposition du projet

* Un Front-End Vue.js
* Un Back-End Spring Boot

# Contributeurs

[Alessandra FRANZESE](https://github.com/NinaNekonoran)
[Aymeric PINEL](https://github.com/amplul)
[Nicolas ANDRÉ](https://github.com/iomega11)
[Romain GOYHENEIX](https://github.com/vandorz)

# Développement front-end
La partie front-end est développée à l'aide de l'IDE **WebStorm** de JetBrains.

**ESLint** est configuré dans notre projet. Il permet d'analyser le code et d'identifier les problèmes associés.
Il est possible de l'appeler à travers le terminal.
Pour cela, il faut d'abord se positionner à la racine du projet front, puis taper la commande suivante :
```console
eslint --ext .js,.vue src
```

# Développement back-end
La partie back-end est développée à l'aide de l'IDE **IntelliJ** de JetBrains.

Le projet est accompagné du plugin Spotless, permettant de "nettoyer" le code.
Pour l'exécuter, il suffit de taper la commande :
```console
mvn spotless:apply
```

Le plugin **Spotbugs** est également présent.
Pour le lancer, il est possible de lancer la commande :
```console
mvn clean install spotbugs:check
```
Spotbugs affichera sur le terminal le résultat de l'analyse. A noter que le build maven échoue si une erreur est détectée.

Il est également possible de voir la couverture de code du back-end à l'aide de **JaCoCo**.

# Lancement du projet

Le projet est conteneurisé avec Docker, et peut-être lancé à l'aide de Docker-compose.

Pour compiler le projet :
```console
docker-compose build
```

Pour lancer le serveur :

```console
docker-compose up
```

Le serveur est alors accessible ici :
```console
http://localhost:43000/index.html
```


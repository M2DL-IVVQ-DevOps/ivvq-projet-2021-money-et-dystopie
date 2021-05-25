<div align="center" style="margin-bottom:50px">

[![Money et Dystopie](moneyetdystopie-logo.jpg)](https://github.com/M2DL-IVVQ-DevOps/ivvq-projet-2021-money-et-dystopie)

# [**Money et Dystopie**](https://money-et-dystopie.herokuapp.com/index.html)

**L'argent n'apporte pas forcément le bonheur. Mais des fois oui quand même.**

![Rendu](https://img.shields.io/badge/Rendu-27%2F05%2F2021-blue?color=brightgreen)
[![Sonar and Build Checks](https://github.com/M2DL-IVVQ-DevOps/ivvq-projet-2021-money-et-dystopie/actions/workflows/checks.yml/badge.svg?branch=develop)](https://github.com/M2DL-IVVQ-DevOps/ivvq-projet-2021-money-et-dystopie/actions/workflows/checks.yml)
![Java](https://img.shields.io/badge/Java-11-blue?logo=java)
![VueJs](https://img.shields.io/badge/Vue.js-2.6.12-blue?logo=vue-dot-js)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-9.6--alpine-blue?logo=postgresql)

</div>

# Description du projet

Le projet consiste en un site marchant sur lequel il est possible d'acheter de l'argent. Il est accessible via ce [**lien**](https://money-et-dystopie.herokuapp.com/index.html).

# Décomposition du projet

* Un Front-End Vue.js
* Un Back-End Spring Boot

# Contributeurs

[Alessandra FRANZESE](https://github.com/NinaNekonoran) **·**
[Aymeric PINEL](https://github.com/amplul) **·**
[Nicolas ANDRÉ](https://github.com/iomega11) **·**
[Romain GOYHENEIX](https://github.com/vandorz)

# Lancement du projet

Le projet est conteneurisé avec Docker, et peut-être lancé à l'aide de Docker-compose.
Pour lancer le serveur :

```console
docker-compose up --build
```

Le serveur est alors accessible ici :
`http://localhost:43000/index.html`

# Développement front-end

La partie front-end est développée à l'aide de l'IDE **WebStorm** de JetBrains. L'application fonctionne grâce à [**Vue.js**](https://vuejs.org/) et la librairie [**Vue Material**](https://vuematerial.io/).

## Commandes de base

### Installation du projet de base
```console
npm install
```

### Compilation et lancement avec hot-reload pour le développement
```console
npm run serve
```

### Compilation pour le déploiement en production
```console
npm run build
```

## ESlint

A des fins de qualité, le projet embarque [**ESLint**](https://eslint.org/). Il permet d'analyser le code et d'identifier les problèmes associés.
Il est possible de l'appeler au travers du terminal.
Pour cela, il faut d'abord se positionner à la racine du projet front, puis taper la commande suivante :
```console
npm run eslint
```

## Cypress

Afin de s'assurer du bon fonctionnement E2E de l'application, [**Cypress**](https://www.cypress.io/) est utilisé avec le [**Cypress Cucumber Preprocessor**](https://github.com/TheBrainFamily/cypress-cucumber-preprocessor). Afin de lancer les tests en local, il est possible d'utiliser les commandes suivantes :

* Pour lancer le front et les tests, avec sauvegarde des vidéos :
    ```console
    npm run serve-and-test
    ```
* Pour lancer le front et les tests, sans sauvegarde des vidéos :
    ```console
    npm run serve-and-test-novid
    ```
* Pour lancer uniquement les tests, avec sauvegarde des vidéos :
    ```console
    npm run test
    ```
* Pour lancer uniquement les tests, sans sauvegarde des vidéos :
    ```console
    npm run test-novid
    ```
S'il s'avère qu'au moins un test échoue durant l'exécution d'une Github Action, les vidéos générées peuvent être retrouvées sous forme d'archive liée à l'exécution de la Github Action.

## Couverture de code

La couverture de code est évaluée par le plugin [**Cypress Code Coverage**](https://github.com/cypress-io/code-coverage) sur un code instrumentalisé par [**Babel Istanbul**](https://github.com/istanbuljs/babel-plugin-istanbul) à chaque lancement des tests. Vous pouvez retrouver le rapport correspondant dans `moneyetdystopie-front/coverage/lcov-report/index.html` après avoir lancé les tests en local.

## Sonar et SonarQube

Toutes les informations de qualité de code générées précédemment sont transmises à [**SonarQube**](https://sonarqube.homefox.ovh/) afin de vérifier la validation des Quality Gates. Tout cela s'effectue automatiquement lors des Github Actions afin de valider une Pull Request.

# Développement back-end

La partie back-end est développée à l'aide de l'IDE **IntelliJ** de JetBrains.
Il s'agit d'un projet [**Spring Boot**](https://spring.io/projects/spring-boot) en Java.

## Spotless

Le projet est accompagné du plugin [**Spotless**](https://github.com/diffplug/spotless), permettant d'appliquer de nombreuses règles de formatage au code.
Pour l'exécuter, il faut d'abord se positionner à la racine du projet back, puis taper la commande suivante :
```console
mvn spotless:apply
```

## Spotbugs

Le plugin [**Spotbugs**](https://spotbugs.github.io/) est également présent.
Celui-ci permet d'analyser statiquement le code en vue d'identifier les potentiels bugs ou risques de sécurité.
Pour le lancer, il faut d'abord se positionner à la racine du projet back, puis taper la commande :
```console
mvn clean install spotbugs:check
```
Spotbugs affichera sur le terminal le résultat de l'analyse.
**A noter que le build maven échoue si une erreur est détectée. Cela ne veut pas dire que l'analyse a échoué.**

## Lombok

Afin de se séparer des lignes de codes superflues, nous avons décidé d'utiliser le plugin [**Lombok**](https://projectlombok.org/) pour ne plus avoir à écrire les constructeurs, getters et setters.

## Jacoco

Il est possible de visualiser la couverture de code via les tests du back-end à l'aide de [**JaCoCo**](https://www.eclemma.org/jacoco/). Afin de générer un rapport de couverture de tests, il suffit de taper la commande suivante :
```console
mvn test
```
Vous pourrez alors retrouver le rapport généré à partir du fichier suivant : `moneyetdystopie-back/target/site/jacoco/index.html`

## Sonar et SonarQube

Tous les rapports générés lors des étapes de développement ou dans les Github Actions sont en suite envoyés à SonarQube. Ce rassemblement des informations permet de définir des Quality Gates que le code doit valider lors des Pull Requests.

SonarQube permet également de rassembler toutes les informations relatives à la qualité de code en un seul endroit. Pour lancer Sonar, il faut d'abord se positionner à la racine du projet back, puis taper la commande suivante :
```console
mvn -X -e clean install spotbugs:check sonar:sonar -Dspring.profiles.active=dev -Dsonar.projectBaseDir=moneyetdystopie-back -Dsonar.projectKey=xxxx -Dsonar.host.url=https://sonarqube.homefox.ovh/ -Dsonar.login=xxxx -Dsonar.qualitygate.wait=true
```

Par soucis de sécurité, les éléments permettant de faire un envoi à SonarQube ont été obfusqués. 

Le résultat est publié sur le serveur hébergeant Sonar. Il est visible à travers son interface.

# Déploiement Heroku

Les déploiements Heroku sont effectués lors de la publication d'une release. Ils sont réalisés automatiquement via les Github Actions.

# La base de données

Le projet utilise une base de données pour stocker les éléments tels que les comptes utilisateurs,
les articles en vente ou encore les commandes passées. Le schéma de la base de données est visible ci-dessous.
![Schéma de la base de données](database-diagram.png)

## Les différentes bases de données utilisées

Le projet dispose de deux profils d'exécution : **dev** et **prod**, définis par la variable d'environnement **profile**.
* Le mode **dev** permet d'utiliser une base de données **H2**, stockant les éléments en mémoire, et réinitialisée à chaque lancement.
Utile pour développer le back-end sans passer par la conteneurisation.
* Le mode **prod** permet d'utiliser une base de données **PostgreSQL**.
C'est ce mode qui est utilisé lorsqu'on conteneurise le projet ou si l'on souhaite développer en local à l'aide du docker-compose, ainsi que lorsque le code est déployé sur Heroku.

La base de données PostgreSQL utilisée dans le conteneur local (pour le développement) et dans le conteneur distant (en production) ne sont pas les mêmes :
* Celle utilisée en local est réinitialisée à chaque lancement. Elle fonctionne à l'aide d'un conteneur Docker dédié.
* Celle utilisée en production est hébergée à travers la plateforme Heroku et n'est que rarement réinitialisée.

## Comment accéder aux bases de données

En profil d'exécution **dev** il est possible d'accéder à l'interface H2 à travers ce lien : http://localhost:8080/h2-console

En profil d'exécution **prod** aucune interface n'est disponible pour visualiser les données.
Il est indispensable d'utiliser un logiciel dédié, tel que [DBeaver](https://dbeaver.io/) :

1. Installer DBeaver à partir de ce lien
2. Lancer l'application
3. **Démarrer une nouvelle connexion** de type **postgreSQL** en renseignant
**l'hôte**, **l'URL de la base de données**, **le nom d'utilisateur** et **le mot de passe**.
Ces informations sont accessibles dans le fichier **db.env** lors d'un développement local, et dans l'interface Heroku postgres en production.

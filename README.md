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

# La base de données

## Les différentes bases de données utilisées

Le projet utilise une base de données pour stocker les éléments tels que les comptes utilisateurs,
les articles en vente ou encore les commandes passées. Le schéma de la base de données est visible ci-dessous.
![Schéma de la base de données](database-diagram.png)

Le projet dispose de deux profils d'exécution : **dev** et **prod**.
* Le mode **dev** permet d'utiliser une base de données **H2**, stockant les éléments en mémoire, et réinitialisée à chaque lancement.
Utile pour développer le back-end sans passer par la conteneurisation.
* Le mode **prod** permet d'utiliser une base de données **PostgreSQL**.
C'est ce mode qui est utilisé lorsqu'on conteneurise le projet.

La base de données utilisée dans le conteneur local (pour le développement) et dans le conteneur distant (en production) ne sont pas les mêmes.
* Celle utilisée en local est réinitialisée à chaque lancement.
Elle fonctionne à l'aide d'un conteneur Docker dédié.
* Celle utilisée en production est hébergée à travers la plateforme Heroku.

## Comment accéder à la base de données

En profil d'exécution **dev** il est possible d'accéder à la console H2 à travers ce lien : http://localhost:8080/h2-console

En profil d'exécution **prod** il est indispensable d'utiliser un logiciel dédié, tel que [DBeaver](https://dbeaver.io/) :

Après avoir installé et lancé l'application, il suffit de **démarrer une nouvelle connexion** de type **postgreSQL** et de renseigner :
* l'hôte
* la base de données
* le nom d'utilisateur
* le mot de passe

Ces informations sont accessibles de deux manières :

* Lors d'un développement local, elles sont trouvables dans le fichier **db.env**
* En production, dans l'interface Heroku postgres

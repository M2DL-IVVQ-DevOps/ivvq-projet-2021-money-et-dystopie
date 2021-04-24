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

Le projet utilise une base de données **postgreSQL** pour stocker les éléments tels que les compte utilisateurs, les articles en vente ou encore le commandes passées.
La base de données utilisée lors du développement et lors de la production ne sont pas les mêmes.
Celle utilisée pour le développement est réinitialisée à chaque lancement. Elle fonctionne à l'aide d'un conteneur Docker dédié.

Pour consulter cette base de données il est possible d'utiliser le logiciel [DBeaver](https://dbeaver.io/) :

Après avoir installé et lancé l'application, il suffit de **démarrer une nouvelle connexion** de type **postgreSQL** et de renseigner :
* l'hôte
* la base de données
* le nom d'utilisateur
* le mot de passe

Ces informations sont accessibles de deux manières :

* Lors d'un développement local, elles sont trouvables dans le fichier **db.env**
* En production, dans l'interface Heroku postgres
# Alimentations

## Description
Ce projet est une application web Java basée sur Servlets et JDBC pour gérer les informations sur les aliments et les couleurs. Elle permet aux utilisateurs de créer, lire, mettre à jour et supprimer (CRUD) des informations sur divers aliments et couleurs stockés dans une base de données MySQL.

## Fonctionnalités
- CRUD pour les aliments.
- CRUD pour les couleurs.
- Interface utilisateur JSP pour l'interaction avec l'API.
- Communication avec la base de données MySQL.

## Installation

### Prérequis
- JDK (version 8 ou ultérieure).
- Serveur Tomcat (version 9 ou ultérieure).
- MySQL Server.

### Configuration de la base de données
1. Créez une base de données MySQL nommée `alimentations`.
2. Exécutez les scripts SQL fournis pour créer les tables nécessaires.

### Configuration du Serveur
1. Clonez le dépôt ou téléchargez le code source.
    https://github.com/aphaena/JavaPOO_MysqlConnect-Maven---Tomcat.git
2. Ouvrez le projet dans votre IDE (par exemple, VS Code, IntelliJ IDEA, Eclipse).
3. Configurez le projet pour utiliser le serveur Tomcat.

## Utilisation

### Démarrage de l'Application
- Démarrez le serveur Tomcat.
- Accédez à `http://localhost:8080/` pour interagir avec l'application via les interfaces JSP.

### API Endpoints

#### Aliments
- **GET `/aliment`** : Récupère tous les aliments.
- **POST `/aliment`** : Crée un nouvel aliment.
- **PUT `/aliment`** : Met à jour un aliment existant.
- **DELETE `/aliment`** : Supprime un aliment.

#### Couleurs
- **GET `/couleur`** : Récupère toutes les couleurs.
- **POST `/couleur`** : Crée une nouvelle couleur.
- **PUT `/couleur`** : Met à jour une couleur existante.
- **DELETE `/couleur`** : Supprime une couleur.

## Développement
### Exercice 1:
- Ajouter la gestion CRUD pour la table type_aliment. Vous devrez créer TypeAliment.java, TypeAlimentDAO.java, TypeAlimentServlet.java
- Modifier les fichiers Main.java, Routeur.java
- Ajouter des requests dans request.rest pour tester vos méthodes ajoutées à l'api
### Exercice 2:
- Dans AlimentDAO.java au lieu de passer chaque attribut indépendemment modifier les méthodes pour utiliser les modèles model/Couleur.java et model/Aliment.java  en passant des objets comme attributs.
- ex:  String jsonResponse = insertAlimentAndGet(aliment);  Vous devrez instancier la classe Aliment.java pour créer l'objet aliment. Et vous devrez apporter des modifications dans le code dans plusieurs fichiers.
- Si vous êtes à l'aise faites le également pour CouleurDAO et TypeAlimentDAO

### Exercice 3:
Pour ceux qui sont en avance et qui sont motivés: Ajoutez une table recette dans la base de données alimentations et ajouter le la gestion du CRUD de cette table dans l'api.

## Licence
Pas de licence pour le moment
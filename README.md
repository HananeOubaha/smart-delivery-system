# Smart Delivery Management System (SDMS)

## Description
## Technologies et Outils
* Spring Core (IoC, DI)
* Spring Data JPA
* Hibernate
* PostgreSQL
* Maven

## Structure du Projet
* `com.smartlogi.entity`: Contient les entités JPA (Livreur, Colis).
* `com.smartlogi.repository`: Contient les interfaces Spring Data JPA.
* `com.smartlogi.service`: Contient la logique métier (CRUD, assignation...).
* `src/main/resources/applicationContext.xml`: Fichier de configuration Spring.
* `com.smartlogi.Main`: Point d'entrée pour tester l'application.

## Prérequis et Installation
1.  Assurez-vous d'avoir PostgreSQL installé et en cours d'exécution.
2.  Créez une base de données : `CREATE DATABASE smartlogi_db;`
3.  Modifiez le fichier `src/main/resources/applicationContext.xml` avec votre utilisateur et mot de passe PostgreSQL.
4.  Compilez le projet : `mvn clean install`
5.  Exécutez la classe `Main.java` pour lancer les tests de la console.
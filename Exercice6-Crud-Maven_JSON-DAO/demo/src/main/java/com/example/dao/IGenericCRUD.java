// Déclaration de l'interface Générique CRUD (Create, Read, Update, Delete)
// Cette interface définit un ensemble de méthodes génériques pour effectuer des opérations CRUD sur une ressource.
package com.example.dao;

// Importation des classes nécessaires pour la gestion des requêtes HTTP, les exceptions SQL et IO
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.io.IOException;

// Déclaration de l'interface IGenericCRUD
public interface IGenericCRUD {
    
    // Méthode pour gérer une requête HTTP GET
    void handleGet(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException;

    // Méthode pour gérer une requête HTTP POST
    void handlePost(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException;

    // Méthode pour gérer une requête HTTP PUT
    void handlePut(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException;

    // Méthode pour gérer une requête HTTP DELETE
    void handleDelete(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException;

    // Méthode pour gérer une requête de recherche par ID
    void handleFindById(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException;

    // Méthode pour gérer une requête de recherche par nom
    void handleFindByName(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException;
}

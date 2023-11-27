// Déclaration du package où se trouve la classe
package com.example.dao;

// Importation des classes nécessaires pour la gestion des requêtes SQL, des exceptions, des entrées/sorties, 
// et des classes utilitaires dans le projet
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.util.ResultSetTableDisplay;
import com.example.util.ServletUtils;

import javax.json.JsonObject;

// Déclaration de la classe TypeAlimentDAO qui implémente l'interface IGenericCRUD
public class TypeAlimentDAO implements IGenericCRUD {

    // Déclaration d'une instance de la classe DatabaseConnection pour gérer la connexion à la base de données
    private DatabaseConnection dbConnection;

    // Constructeur de la classe prenant en paramètre une instance de DatabaseConnection
    public TypeAlimentDAO(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    // Méthode pour gérer une requête HTTP GET et renvoyer une réponse au format HTML
    public void handleGetHTML(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        // Connexion à la base de données
        dbConnection.connect();
    
        // Configuration de la réponse HTTP avec le type de contenu HTML
        response.setContentType("text/html");
    
        // Récupération du flux de sortie pour écrire la réponse
        PrintWriter out = response.getWriter();          
    
        // Affichage des résultats de la requête sous forme de tableau HTML
        ResultSetTableDisplay.displayHtmlTable(listAllTypeAliment(), out);
    
        // Conversion des résultats de la requête en une chaîne de caractères contenant un tableau HTML
        String typeAlimentHtml  = ResultSetTableDisplay.toHtmlTable(listAllTypeAliment());
        System.out.println(typeAlimentHtml);
    
        // Déconnexion de la base de données
        dbConnection.disconnect();
    }

    // Implémentation de la méthode de l'interface pour gérer une requête HTTP GET
    @Override
    public void handleGet(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        try {
            // Connexion à la base de données et récupération des données
            dbConnection.connect();
            ResultSet resultSet = listAllTypeAliment();
    
            // Convertir le ResultSet en JSON
            String jsonResponse = ServletUtils.convertResultSetToJson(resultSet);
    
            // Envoyer la réponse JSON
            ServletUtils.sendJsonResponse(response, jsonResponse);
    
            // Fermer ResultSet et déconnexion de la base de données
            resultSet.close();
            dbConnection.disconnect();
        } catch (SQLException ex) {
            // Gérer les exceptions SQL en renvoyant une réponse d'erreur détaillée
            ServletUtils.handleSqlException(response, ex);
        } catch (Exception ex) {
            // Gérer d'autres exceptions génériques en renvoyant une réponse d'erreur interne du serveur avec le message d'erreur
            ServletUtils.sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Server error: " + ex.getMessage());
        }
    }
    
   
    // Implémentation de la méthode de l'interface pour gérer une requête HTTP POST
    @Override
    public void handlePost(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        try {
            // Lire et parser le corps de la requête JSON
            JsonObject jsonObject = ServletUtils.parseJsonRequest(request);

            // Récupérer les valeurs 'nom'
            String nom = jsonObject.getString("nom", "");

            // Validation
            ServletUtils.validateRequestData(jsonObject, "nom");

            // Insérer la nouveau Type d'Aliment et récupérer un ResultSet
            ResultSet resultSet = insertTypeAlimentAndGet(nom);

            // Utiliser toJson pour convertir le ResultSet en JSON
            String jsonResponse = ServletUtils.convertResultSetToJson(resultSet);

            // Envoyer la réponse JSON
            ServletUtils.sendJsonResponse(response, jsonResponse);
        } catch (SQLException ex) {
            // Gérer les exceptions SQL en renvoyant une réponse d'erreur détaillée
            ServletUtils.handleSqlException(response, ex);
        } catch (IOException ex) {
            // Gérer les exceptions liées aux opérations d'entrée/sortie en renvoyant une réponse d'erreur de mauvaise requête
            ServletUtils.sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
        } catch (Exception ex) {
            // Gérer d'autres exceptions génériques en renvoyant une réponse d'erreur interne du serveur avec le message d'erreur
            ServletUtils.sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error inserting TypeAliment: " + ex.getMessage());
        }
    }


    // Implémentation de la méthode de l'interface pour gérer une requête HTTP PUT
    @Override
    public void handlePut(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        try {
            
            System.out.println("hello");

            // Lire et parser le corps de la requête JSON
            JsonObject jsonObject = ServletUtils.parseJsonRequest(request);

            // Récupérer les données 'id', 'nom'
            int id = jsonObject.getInt("id");
            String nomTypeAliment = jsonObject.getString("nom", "");

            // Validation
            if (nomTypeAliment.isEmpty()) {
                // Si l'un des champs requis est vide, renvoyer une réponse d'erreur Bad Request
                ServletUtils.sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Missing required fields : 'nom'");
                return;
            }

            // Mettre à jour le TypeAliment
            boolean updated = updateTypeAliment(id, nomTypeAliment);

            // Envoyer une réponse en fonction du succès de la mise à jour
            if (updated) {
                // Si la mise à jour est réussie, renvoyer une réponse JSON avec un message de succès
                ServletUtils.sendJsonResponse(response, "{\"message\": \"TypeAliment updated successfully.\"}");
            } else {
                // Si la mise à jour a échoué, renvoyer une réponse d'erreur Not Found
                ServletUtils.sendErrorResponse(response, HttpServletResponse.SC_NOT_FOUND, "TypeAliment not found or not updated");
            }
        } catch (NumberFormatException ex) {
            // Gérer une exception si la conversion de l'ID en entier échoue
            ServletUtils.sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid format for ID");
        } catch (SQLException ex) {
            // Gérer une exception SQL en renvoyant une réponse d'erreur avec le détail de l'exception
            ServletUtils.handleSqlException(response, ex);
        } catch (Exception ex) {
            // Gérer d'autres exceptions génériques en renvoyant une réponse d'erreur interne du serveur avec le message d'erreur
            ServletUtils.sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Server error: " + ex.getMessage());
        }
    }


    // Implémentation de la méthode de l'interface pour gérer une requête HTTP DELETE
    @Override
    public void handleDelete(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        try {
            // Lire et parser le corps de la requête JSON
            JsonObject jsonObject = ServletUtils.parseJsonRequest(request);

            // Récupérer l'identifiant de typeAliment à supprimer
            int id = jsonObject.getInt("id");
            
            // Supprimer le typeAliment
            boolean deleted = deleteTypeAliment(id);

            // Envoyer une réponse en fonction du succès de la suppression
            if (deleted) {
                // Si la suppression est réussie, renvoyer une réponse JSON avec un message de succès
                ServletUtils.sendJsonResponse(response, "{\"message\": \"TypeAliment deleted successfully.\"}");
            } else {
                // Si la suppression a échoué, renvoyer une réponse d'erreur Not Found
                ServletUtils.sendErrorResponse(response, HttpServletResponse.SC_NOT_FOUND, "TypeAliment not found or not deleted");
            }
        } catch (NumberFormatException ex) {
            // Gérer une exception si la conversion de l'ID en entier échoue
            ServletUtils.sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid format for ID");
        } catch (SQLException ex) {
            // Gérer une exception SQL en renvoyant une réponse d'erreur avec le détail de l'exception
            ServletUtils.handleSqlException(response, ex);
        } catch (Exception ex) {
            // Gérer d'autres exceptions génériques en renvoyant une réponse d'erreur interne du serveur avec le message d'erreur
            ServletUtils.sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Server error: " + ex.getMessage());
        }
    }


    // Implémentation de la méthode de l'interface pour gérer une requête de recherche par ID
    @Override
    public void handleFindById(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        try {
            // Extraire l'ID de typeAliment à partir des paramètres de la requête
            int id = Integer.parseInt(request.getParameter("id"));

            // Récupérer la typeAliment par son ID
            ResultSet resultSet = findById(id);

            // Convertir le ResultSet en JSON
            String jsonResponse = ServletUtils.convertResultSetToJson(resultSet);

            // Envoyer la réponse JSON
            ServletUtils.sendJsonResponse(response, jsonResponse);

            // Fermer ResultSet et déconnexion de la base de données
            resultSet.close();
            dbConnection.disconnect();
        } catch (NumberFormatException ex) {
            // Gérer les exceptions liées au format incorrect de l'ID en renvoyant une réponse d'erreur de mauvaise requête
            ServletUtils.sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid ID format");
        } catch (SQLException ex) {
            // Gérer les exceptions SQL en renvoyant une réponse d'erreur détaillée
            ServletUtils.handleSqlException(response, ex);
        } catch (Exception ex) {
            // Gérer d'autres exceptions génériques en renvoyant une réponse d'erreur interne du serveur avec le message d'erreur
            ServletUtils.sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Server error: " + ex.getMessage());
        }
    }


    // Implémentation de la méthode de l'interface pour gérer une requête de recherche par nom
    @Override
    public void handleFindByName(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        try {
            // Extraire le nom de typeAliment à partir des paramètres de la requête
            String name = request.getParameter("name");

            // Récupérer le typeAliment par son nom
            ResultSet resultSet = findByName(name);

            // Convertir le ResultSet en JSON
            String jsonResponse = ServletUtils.convertResultSetToJson(resultSet);

            // Envoyer la réponse JSON
            ServletUtils.sendJsonResponse(response, jsonResponse);

            // Fermer ResultSet et déconnexion de la base de données
            resultSet.close();
            dbConnection.disconnect();
        } catch (SQLException ex) {
            // Gérer les exceptions SQL en renvoyant une réponse d'erreur détaillée
            ServletUtils.handleSqlException(response, ex);
        } catch (Exception ex) {
            // Gérer d'autres exceptions génériques en renvoyant une réponse d'erreur interne du serveur avec le message d'erreur
            ServletUtils.sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Server error: " + ex.getMessage());
        }
    }


    // Méthode pour insérer un nouveau typeAliment dans la base de données
    public boolean insertTypeAliment(String nom) throws SQLException {
        // Requête SQL pour l'insertion
        String sql = "INSERT INTO type_aliment (nom) VALUES (?)";
        
        // Connexion à la base de données
        dbConnection.connect();
        
        // Préparation de la requête SQL
        PreparedStatement statement = dbConnection.getJdbcConnection().prepareStatement(sql);
    
        // Paramétrage des valeurs à insérer
        statement.setString(1, nom);
    
        // Exécution de la requête et récupération du résultat
        boolean result = statement.executeUpdate() > 0;
    
        // Fermeture du statement et déconnexion de la base de données
        statement.close();
        dbConnection.disconnect();
        
        return result;
    }

    // Méthode pour insérer un nouveau typeAliment et récupérer les données insérées
    public ResultSet insertTypeAlimentAndGet(String nom) throws SQLException {
        // Requête SQL pour l'insertion
        String insertSql = "INSERT INTO type_aliment (nom) VALUES (?)";
        
        // Requête SQL pour la sélection d'un nouveau typeAliment
        String selectSql = "SELECT * FROM type_aliment WHERE id = ?";
        
        // Connexion à la base de données
        dbConnection.connect();
        
        // Insertion d'un nouveau typeAliment
        PreparedStatement insertStatement = dbConnection.getJdbcConnection().prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
        insertStatement.setString(1, nom);
        insertStatement.executeUpdate();
        
        // Récupération de l'identifiant généré
        ResultSet generatedKeys = insertStatement.getGeneratedKeys();
        if (!generatedKeys.next()) {
            throw new SQLException("Creating TypeAliment failed, no ID obtained.");
        }
        int newTypeAlimentId = generatedKeys.getInt(1);
        insertStatement.close();
    
        // Récupération d'un nouveau typeAliment inséré
        PreparedStatement selectStatement = dbConnection.getJdbcConnection().prepareStatement(selectSql);
        selectStatement.setInt(1, newTypeAlimentId);
        ResultSet resultSet = selectStatement.executeQuery();
    
        // Note: La gestion de la fermeture du ResultSet et de la déconnexion de la base de données devrait être effectuée par l'appelant.
        
        return resultSet;
    }

    // Méthode pour récupérer tout les nouveaux typeAliment de la base de données
    public ResultSet listAllTypeAliment() throws SQLException {
        // Requête SQL pour récupérer tout les nouveaux typeAliment
        String sql = "SELECT * FROM type_aliment";
        
        // Connexion à la base de données
        dbConnection.connect();

        // Création d'un statement pour exécuter la requête SQL
        Statement statement = dbConnection.getJdbcConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        // Note: L'appelant doit gérer la fermeture du resultSet et la déconnexion
        return resultSet;
    }

    // Méthode pour mettre à jour les informations d'un typeAliment dans la base de données
    public boolean updateTypeAliment(int id, String nomTypeAliment) throws SQLException {
        // Requête SQL pour la mise à jour d'un typeAliment
        String sql = "UPDATE type_aliment SET nom = ? WHERE id = ?";
        
        // Connexion à la base de données
        dbConnection.connect();

        // Préparation de la requête SQL
        PreparedStatement statement = dbConnection.getJdbcConnection().prepareStatement(sql);
        statement.setString(1, nomTypeAliment);
        statement.setInt(2, id);

        // Exécution de la requête et récupération du résultat
        boolean rowUpdated = statement.executeUpdate() > 0;
        
        // Fermeture du statement et déconnexion de la base de données
        statement.close();
        dbConnection.disconnect();
        
        return rowUpdated;
    }

    // Méthode pour supprimer un typeAliment de la base de données
    public boolean deleteTypeAliment(int id) throws SQLException {
        // Requête SQL pour supprimer un typeAliment par son ID
        String sql = "DELETE FROM type_aliment WHERE id = ?";

        // Connexion à la base de données
        dbConnection.connect();

        // Préparation de la requête SQL
        PreparedStatement statement = dbConnection.getJdbcConnection().prepareStatement(sql);
        statement.setInt(1, id);

        // Exécution de la requête et récupération du résultat
        boolean rowDeleted = statement.executeUpdate() > 0;
        
        // Fermeture du statement et déconnexion de la base de données
        statement.close();
        dbConnection.disconnect();
        
        return rowDeleted;
    }

    // Méthode pour récupérer le dernier typeAliment inséré dans la base de données
    public ResultSet getLastInsertedTypeAliment() throws SQLException {
        String sql = "SELECT * FROM type_aliment ORDER BY id DESC LIMIT 1";

        //dbConnection.connect();

        Statement statement = dbConnection.getJdbcConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        // Note: L'appelant doit gérer la fermeture du resultSet et la déconnexion
        return resultSet;
    }

    // Méthode pour trouver un typeAliment par son ID
    public ResultSet findById(int id) throws SQLException {
        // Requête SQL pour récupérer un typeAliment par son ID
        String sql = "SELECT * FROM type_aliment WHERE id = ?";
        
        // Connexion à la base de données
        dbConnection.connect();
        
        // Préparation de la requête SQL
        PreparedStatement statement = dbConnection.getJdbcConnection().prepareStatement(sql);
        
        // Attribution de la valeur à remplacer dans la requête
        statement.setInt(1, id);
        
        // Exécution de la requête et récupération du résultat
        ResultSet resultSet = statement.executeQuery();
        
        // Note: L'appelant doit gérer la fermeture du resultSet et la déconnexion
        
        // Retour du resultSet
        return resultSet;
    }
    

    // Méthode pour trouver un typeAliment par son nom
    public ResultSet findByName(String name) throws SQLException {
        // Requête SQL pour récupérer un typeAliment par son nom
        String sql = "SELECT * FROM type_aliment WHERE nom = ?";
        
        // Connexion à la base de données
        dbConnection.connect();
        
        // Préparation de la requête SQL
        PreparedStatement statement = dbConnection.getJdbcConnection().prepareStatement(sql);
        
        // Attribution de la valeur à remplacer dans la requête
        statement.setString(1, name);
        
        // Exécution de la requête et récupération du résultat
        return statement.executeQuery();
    }
    
}

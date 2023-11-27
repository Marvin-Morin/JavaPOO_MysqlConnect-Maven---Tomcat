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

// Déclaration de la classe CouleurDAO qui implémente l'interface IGenericCRUD
public class CouleurDAO implements IGenericCRUD {

    // Déclaration d'une instance de la classe DatabaseConnection pour gérer la connexion à la base de données
    private DatabaseConnection dbConnection;

    // Constructeur de la classe prenant en paramètre une instance de DatabaseConnection
    public CouleurDAO(DatabaseConnection dbConnection) {
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
        ResultSetTableDisplay.displayHtmlTable(listAllCouleurs(), out);
    
        // Conversion des résultats de la requête en une chaîne de caractères contenant un tableau HTML
        // Classe ResultSetTableDisplay = Création et ajout des données dans un tableau HTML
        // listAllCouleurs est une méthode que l'on créer à la ligne 317 qui est une Méthode pour récupérer toutes les couleurs de la base de données
        String couleurHtml  = ResultSetTableDisplay.toHtmlTable(listAllCouleurs());
        System.out.println(couleurHtml);
    
        // Déconnexion de la base de données
        dbConnection.disconnect();
    }

    // Implémentation de la méthode de l'interface pour gérer une requête HTTP GET
    // l'annotation @Override, est une annotation en Java utilisée pour indiquer qu'une méthode dans une sous-classe est destinée à remplacer une méthode de sa superclasse
    @Override
    public void handleGet(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        try {
            // Connexion à la base de données et récupération des données
            dbConnection.connect();
            ResultSet resultSet = listAllCouleurs();
    
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

            // Récupérer les valeurs 'nom' et 'hexadecimal_rvb'
            String nom = jsonObject.getString("nom", "");
            String hexadecimal_rvb = jsonObject.getString("hexadecimal_rvb", "");

            // Validation
            ServletUtils.validateRequestData(jsonObject, "nom", "hexadecimal_rvb");

            // Insérer la nouvelle couleur et récupérer un ResultSet
            ResultSet resultSet = insertCouleurAndGet(nom, hexadecimal_rvb);

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
            ServletUtils.sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error inserting color: " + ex.getMessage());
        }
    }


    // Implémentation de la méthode de l'interface pour gérer une requête HTTP PUT
    @Override
    public void handlePut(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        try {
            // Lire et parser le corps de la requête JSON
            JsonObject jsonObject = ServletUtils.parseJsonRequest(request);

            // Récupérer les données 'id', 'nom', et 'hexadecimal_rvb'
            int id = jsonObject.getInt("id");
            String nomCouleur = jsonObject.getString("nom", "");
            String hexadecimal_rvb = jsonObject.getString("hexadecimal_rvb", "");

            // Validation
            if (nomCouleur.isEmpty() || hexadecimal_rvb.isEmpty()) {
                // Si l'un des champs requis est vide, renvoyer une réponse d'erreur Bad Request
                ServletUtils.sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Missing required fields");
                return;
            }

            // Mettre à jour la couleur
            boolean updated = updateCouleur(id, nomCouleur, hexadecimal_rvb);

            // Envoyer une réponse en fonction du succès de la mise à jour
            if (updated) {
                // Si la mise à jour est réussie, renvoyer une réponse JSON avec un message de succès
                ServletUtils.sendJsonResponse(response, "{\"message\": \"Color updated successfully.\"}");
            } else {
                // Si la mise à jour a échoué, renvoyer une réponse d'erreur Not Found
                ServletUtils.sendErrorResponse(response, HttpServletResponse.SC_NOT_FOUND, "Color not found or not updated");
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

            // Récupérer l'identifiant de la couleur à supprimer
            int id = jsonObject.getInt("id");
            
            // Supprimer la couleur
            boolean deleted = deleteCouleur(id);

            // Envoyer une réponse en fonction du succès de la suppression
            if (deleted) {
                // Si la suppression est réussie, renvoyer une réponse JSON avec un message de succès
                ServletUtils.sendJsonResponse(response, "{\"message\": \"Color deleted successfully.\"}");
            } else {
                // Si la suppression a échoué, renvoyer une réponse d'erreur Not Found
                ServletUtils.sendErrorResponse(response, HttpServletResponse.SC_NOT_FOUND, "Color not found or not deleted");
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
            // Extraire l'ID de la couleur à partir des paramètres de la requête
            int id = Integer.parseInt(request.getParameter("id"));

            // Récupérer la couleur par son ID
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
            // Extraire le nom de la couleur à partir des paramètres de la requête
            String name = request.getParameter("name");

            // Récupérer la couleur par son nom
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


    // Méthode pour insérer une nouvelle couleur dans la base de données
    public boolean insertCouleur(String nom, String hexadecimal_rvb) throws SQLException {
        // Requête SQL pour l'insertion
        String sql = "INSERT INTO couleur (nom, hexadecimal_rvb) VALUES (?, ?)";
        
        // Connexion à la base de données
        dbConnection.connect();
        
        // Préparation de la requête SQL
        PreparedStatement statement = dbConnection.getJdbcConnection().prepareStatement(sql);
    
        // Paramétrage des valeurs à insérer
        statement.setString(1, nom);
        statement.setString(2, hexadecimal_rvb);
    
        // Exécution de la requête et récupération du résultat
        boolean result = statement.executeUpdate() > 0;
    
        // Fermeture du statement et déconnexion de la base de données
        statement.close();
        dbConnection.disconnect();
        
        return result;
    }

    // Méthode pour insérer une nouvelle couleur et récupérer les données insérées
    public ResultSet insertCouleurAndGet(String nom, String hexadecimal_rvb) throws SQLException {
        // Requête SQL pour l'insertion
        String insertSql = "INSERT INTO couleur (nom, hexadecimal_rvb) VALUES (?, ?)";
        
        // Requête SQL pour la sélection de la nouvelle couleur
        String selectSql = "SELECT * FROM couleur WHERE id = ?";
        
        // Connexion à la base de données
        dbConnection.connect();
        
        // Insertion de la nouvelle couleur
        PreparedStatement insertStatement = dbConnection.getJdbcConnection().prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
        insertStatement.setString(1, nom);
        insertStatement.setString(2, hexadecimal_rvb);
        insertStatement.executeUpdate();
        
        // Récupération de l'identifiant généré
        ResultSet generatedKeys = insertStatement.getGeneratedKeys();
        if (!generatedKeys.next()) {
            throw new SQLException("Creating color failed, no ID obtained.");
        }
        int newColorId = generatedKeys.getInt(1);
        insertStatement.close();
    
        // Récupération de la nouvelle couleur insérée
        PreparedStatement selectStatement = dbConnection.getJdbcConnection().prepareStatement(selectSql);
        selectStatement.setInt(1, newColorId);
        ResultSet resultSet = selectStatement.executeQuery();
    
        // Note: La gestion de la fermeture du ResultSet et de la déconnexion de la base de données devrait être effectuée par l'appelant.
        
        return resultSet;
    }

    // Méthode pour récupérer toutes les couleurs de la base de données
    public ResultSet listAllCouleurs() throws SQLException {
        // Requête SQL pour récupérer toutes les couleurs
        String sql = "SELECT * FROM couleur";
        
        // Connexion à la base de données
        dbConnection.connect();

        // Création d'un statement pour exécuter la requête SQL
        Statement statement = dbConnection.getJdbcConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        // Note: L'appelant doit gérer la fermeture du resultSet et la déconnexion
        return resultSet;
    }

    // Méthode pour mettre à jour les informations d'une couleur dans la base de données
    public boolean updateCouleur(int id, String nomCouleur, String hexadecimal_rvb) throws SQLException {
        // Requête SQL pour la mise à jour d'une couleur
        String sql = "UPDATE couleur SET nom = ?, hexadecimal_rvb = ? WHERE id = ?";
        
        // Connexion à la base de données
        dbConnection.connect();

        // Préparation de la requête SQL
        PreparedStatement statement = dbConnection.getJdbcConnection().prepareStatement(sql);
        statement.setString(1, nomCouleur);
        statement.setString(2, hexadecimal_rvb); // Mise à jour de la valeur hexadecimal_rvb
        statement.setInt(3, id);

        // Exécution de la requête et récupération du résultat
        boolean rowUpdated = statement.executeUpdate() > 0;
        
        // Fermeture du statement et déconnexion de la base de données
        statement.close();
        dbConnection.disconnect();
        
        return rowUpdated;
    }

    // Méthode pour supprimer une couleur de la base de données
    public boolean deleteCouleur(int id) throws SQLException {
        // Requête SQL pour supprimer une couleur par son ID
        String sql = "DELETE FROM couleur WHERE id = ?";

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

    // Méthode pour récupérer la dernière couleur insérée dans la base de données
    public ResultSet getLastInsertedColor() throws SQLException {
        String sql = "SELECT * FROM couleur ORDER BY id DESC LIMIT 1";

        //dbConnection.connect();

        Statement statement = dbConnection.getJdbcConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        // Note: L'appelant doit gérer la fermeture du resultSet et la déconnexion
        return resultSet;
    }

    // Méthode pour trouver une couleur par son ID
    public ResultSet findById(int id) throws SQLException {
        // Requête SQL pour récupérer une couleur par son ID
        String sql = "SELECT * FROM couleur WHERE id = ?";
        
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
    

    // Méthode pour trouver une couleur par son nom
    public ResultSet findByName(String name) throws SQLException {
        // Requête SQL pour récupérer une couleur par son nom
        String sql = "SELECT * FROM couleur WHERE nom = ?";
        
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

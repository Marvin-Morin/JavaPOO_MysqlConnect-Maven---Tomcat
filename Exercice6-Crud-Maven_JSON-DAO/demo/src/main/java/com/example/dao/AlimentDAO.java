// Déclaration du package où se trouve la classe
package com.example.dao;

// Importation des classes nécessaires
import java.io.IOException;
import java.sql.*;

import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import com.example.model.Aliment; // Utilisation d'un import Model Aliment si methode qui utilise le Model
import com.example.util.ServletUtils;

// Déclaration de la classe AlimentDAO qui implémente une interface IGenericCRUD
public class AlimentDAO implements IGenericCRUD{

    // Déclaration d'une variable pour la connexion à la base de données
    private DatabaseConnection dbConnection;

    // Constructeur prenant une connexion de base de données en paramètre
    public AlimentDAO(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    // Implémentation de la méthode handleGet de l'interface IGenericCRUD
    @Override
    public void handleGet(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        // Bloc try-catch pour gérer les exceptions possibles
        try {
            // Connexion à la base de données
            dbConnection.connect();

            // Récupération de tous les aliments sous forme de ResultSet
            ResultSet resultSet = listAllAliments();

            // Conversion du ResultSet en format JSON
            String jsonResponse = ServletUtils.convertResultSetToJson(resultSet);

            // Envoi de la réponse JSON à la requête HTTP
            ServletUtils.sendJsonResponse(response, jsonResponse);

            // Fermeture du ResultSet et déconnexion de la base de données
            resultSet.close();
            dbConnection.disconnect();
        } catch (SQLException ex) {
            // Gestion des exceptions liées à la base de données
            ServletUtils.handleSqlException(response, ex);
        } catch (Exception ex) {
            // Gestion des exceptions générales
            ServletUtils.sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Server error: " + ex.getMessage());
        }
    }
    
    // Implémentation de la méthode handlePost de l'interface IGenericCRUD
    @Override
    public void handlePost(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        try {
            // Lire et parser le corps de la requête JSON
            JsonObject jsonObject = ServletUtils.parseJsonRequest(request);

            // Récupérer les valeurs requises
            String nom = jsonObject.getString("nom", null); // On suppose que 'nom' est un champ obligatoire
            float poidsMoyen = (float) jsonObject.getJsonNumber("poids_moyen").doubleValue();
            int calories = jsonObject.getInt("calories");
            float vitaminesC = (float) jsonObject.getJsonNumber("vitamines_C").doubleValue();
            int typeId = jsonObject.getInt("type_id");
            int couleurId = jsonObject.getInt("couleur_id");

            // Validation (Note : La validation des données requises est souvent nécessaire à ce stade)

            // Insérer le nouvel aliment et récupérer un ResultSet
            String jsonResponse = insertAlimentAndGet(nom, poidsMoyen, calories, vitaminesC, typeId, couleurId);

            // Utiliser toJson pour convertir le ResultSet en JSON
            ServletUtils.sendJsonResponse(response, jsonResponse);

            // Envoyer la réponse JSON (Note : Vous envoyez deux fois la même réponse, cela semble redondant)
            ServletUtils.sendJsonResponse(response, jsonResponse);
        } catch (SQLException ex) {
            // Gérer les exceptions SQL en renvoyant une réponse appropriée
            ServletUtils.handleSqlException(response, ex);
        } catch (IOException ex) {
            // Gérer les exceptions liées à l'entrée/sortie en renvoyant une réponse Bad Request
            ServletUtils.sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
        } catch (Exception ex) {
            // Gérer toutes les autres exceptions en renvoyant une réponse d'erreur interne du serveur
            ServletUtils.sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error inserting aliment: " + ex.getMessage());
        } 
    }
   
    // Implémentation de la méthode handlePut de l'interface IGenericCRUD
    @Override
    public void handlePut(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        try {
            // Parser le corps JSON de la requête
            JsonObject jsonObject = ServletUtils.parseJsonRequest(request);

            // Récupérer les données de l'objet JSON
            String nom = jsonObject.getString("nom", null); // Supposant que 'nom' est un champ obligatoire
            float poidsMoyen = (float) jsonObject.getJsonNumber("poids_moyen").doubleValue();
            int calories = jsonObject.getInt("calories");
            float vitaminesC = (float) jsonObject.getJsonNumber("vitamines_C").doubleValue();
            int typeId = jsonObject.getInt("type_id");
            int couleurId = jsonObject.getInt("couleur_id");
            int id = jsonObject.getInt("id");

            // Valider les champs obligatoires
            if (nom == null || nom.isEmpty()) {
                ServletUtils.sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Champ requis manquant : nom");
                return;
            }

            // Mettre à jour l'aliment
            boolean updated = updateAliment(id, nom, poidsMoyen, calories, vitaminesC, typeId, couleurId);

            // Envoyer une réponse en fonction du succès de la mise à jour
            if (updated) {
                ServletUtils.sendJsonResponse(response, "{\"message\": \"Aliment mis à jour avec succès.\"}");
            } else {
                ServletUtils.sendErrorResponse(response, HttpServletResponse.SC_NOT_FOUND, "Aliment non trouvé ou non mis à jour");
            }
        } catch (NumberFormatException ex) {
            // Capturer une exception si le format de l'identifiant est invalide
            ServletUtils.sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Format numérique invalide");
        } catch (SQLException ex) {
            // Gérer une exception SQL en envoyant une réponse appropriée
            ServletUtils.handleSqlException(response, ex);
        } catch (Exception ex) {
            // Capturer toute autre exception et envoyer une réponse d'erreur avec un message explicatif
            ServletUtils.sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur du serveur : " + ex.getMessage());
        }
    }

    // Implémentation de la méthode handleDelete de l'interface IGenericCRUD
    @Override
    public void handleDelete(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        try {
            // Lire et parser le corps de la requête JSON
            JsonObject jsonObject = ServletUtils.parseJsonRequest(request);

            // Récupérer l'identifiant de la couleur à supprimer
            int id = jsonObject.getInt("id");

            // Appeler la méthode pour supprimer l'aliment avec l'identifiant spécifié
            boolean deleted = deleteAliment(id);

            // Vérifier si la suppression a été effectuée avec succès
            if (deleted) {
                // Envoyer une réponse JSON en cas de succès
                ServletUtils.sendJsonResponse(response, "{\"message\": \"Aliment supprimé avec succès.\"}");
            } else {
                // Envoyer une réponse d'erreur en cas d'échec de la suppression
                ServletUtils.sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors de la suppression de l'aliment");
            }
        } catch (NumberFormatException ex) {
            // Capturer une exception si le format de l'identifiant est invalide
            ServletUtils.sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Format invalide pour l'identifiant");
        } catch (SQLException ex) {
            // Gérer une exception SQL en envoyant une réponse appropriée
            ServletUtils.handleSqlException(response, ex);
        } catch (Exception ex) {
            // Capturer toute autre exception et envoyer une réponse d'erreur avec un message explicatif
            ServletUtils.sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur du serveur : " + ex.getMessage());
        }
    }

    // Implémentation de la méthode handleFindById de l'interface IGenericCRUD
    @Override
    public void handleFindById(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        try {
            // Extraire l'ID de l'aliment à partir des paramètres de la requête
            int id = Integer.parseInt(request.getParameter("id"));
    
            // Récupérer l'aliment par son ID
            ResultSet resultSet = findById(id);
    
            // Convertir le ResultSet en JSON
            String jsonResponse = ServletUtils.convertResultSetToJson(resultSet);
    
            // Envoyer la réponse JSON
            ServletUtils.sendJsonResponse(response, jsonResponse);
    
            // Fermer ResultSet et déconnexion de la base de données
            resultSet.close();
            dbConnection.disconnect();
        } catch (NumberFormatException ex) {
            // Capturer une exception si le format de l'identifiant est invalide
            ServletUtils.sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid ID format");
        } catch (SQLException ex) {
            // Gérer une exception SQL en envoyant une réponse appropriée
            ServletUtils.handleSqlException(response, ex);
        } catch (Exception ex) {
            // Capturer toute autre exception et envoyer une réponse d'erreur avec un message explicatif
            ServletUtils.sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Server error: " + ex.getMessage());
        }
    }

    // Implémentation de la méthode handleFindByName de l'interface IGenericCRUD
    @Override
    public void handleFindByName(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        try {
            // Récupérer le paramètre "name" de la requête HTTP
            String name = request.getParameter("name");

            // Appeler la méthode findByName pour récupérer un ResultSet
            ResultSet resultSet = findByName(name);

            // Convertir le ResultSet en format JSON
            String jsonResponse = ServletUtils.convertResultSetToJson(resultSet);

            // Envoyer la réponse JSON à la requête HTTP
            ServletUtils.sendJsonResponse(response, jsonResponse);

            // Fermer le ResultSet et déconnecter la base de données
            resultSet.close();
            dbConnection.disconnect();
        } catch (SQLException ex) {
            // Gérer une exception SQL en envoyant une réponse appropriée
            ServletUtils.handleSqlException(response, ex);
        } catch (Exception ex) {
            // Gérer toute autre exception en envoyant une réponse d'erreur avec un message explicatif
            ServletUtils.sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur du serveur : " + ex.getMessage());
        }
    }



    // Create (Insert) - En utilisant la class du Model Aliment
    // private boolean insertAliment(Aliment aliment) {
    //     String sql = "INSERT INTO aliments (nom, poids_moyen, calories, vitamines_C, type_id, couleur_id) VALUES (?, ?, ?, ?, ?, ?)";
    
    //     try {
    //         dbConnection.connect();
    //         try (PreparedStatement statement = dbConnection.getJdbcConnection().prepareStatement(sql)) {
    //             statement.setString(1, aliment.getNom());
    //             statement.setFloat(2, aliment.getPoidsMoyen());
    //             statement.setInt(3, aliment.getCalories());
    //             statement.setFloat(4, aliment.getVitaminesC());
    
    //             if (aliment.getTypeId() != null) {
    //                 statement.setInt(5, aliment.getTypeId());
    //             } else {
    //                 statement.setNull(5, Types.INTEGER);
    //             }
    
    //             if (aliment.getCouleurId() != null) {
    //                 statement.setInt(6, aliment.getCouleurId());
    //             } else {
    //                 statement.setNull(6, Types.INTEGER);
    //             }
    
    //             return statement.executeUpdate() > 0;
    //         } finally {
    //             dbConnection.disconnect();
    //         }
    //     } catch (SQLException e) {
    //         e.printStackTrace(); // ou une autre gestion des exceptions
    //         return false;
    //     }
    // }        

    // Méthode pour l'insertion d'un aliment dans la base de données
    public boolean insertAliment(String nom, float poidsMoyen, int calories, float vitaminesC, Integer typeId, Integer couleurId) throws SQLException {
        // Requête SQL pour l'insertion d'un nouvel aliment
        String sql = "INSERT INTO aliments (nom, poids_moyen, calories, vitamines_C, type_id, couleur_id) VALUES (?, ?, ?, ?, ?, ?)";
        // Connexion à la base de données
        dbConnection.connect();
    
        // Préparation de la requête SQL
        PreparedStatement statement = dbConnection.getJdbcConnection().prepareStatement(sql);
        statement.setString(1, nom);             // Affectation de la valeur du paramètre 'nom' à la position 1 dans la requête
        statement.setFloat(2, poidsMoyen);       // Affectation de la valeur du paramètre 'poidsMoyen' à la position 2 dans la requête
        statement.setInt(3, calories);            // Affectation de la valeur du paramètre 'calories' à la position 3 dans la requête
        statement.setFloat(4, vitaminesC);       // Affectation de la valeur du paramètre 'vitaminesC' à la position 4 dans la requête
    
        // Vérification de la présence des valeurs optionnelles typeId et couleurId
        if (typeId != null) {
            statement.setInt(5, typeId);         // Affectation de la valeur du paramètre 'typeId' à la position 5 dans la requête
        } else {
            statement.setNull(5, Types.INTEGER); // Si typeId est null, définir la valeur à NULL dans la requête
        }
        
        if (couleurId != null) {
            statement.setInt(6, couleurId);      // Affectation de la valeur du paramètre 'couleurId' à la position 6 dans la requête
        } else {
            statement.setNull(6, Types.INTEGER); // Si couleurId est null, définir la valeur à NULL dans la requête
        }
    
        // Exécution de la requête et récupération du résultat
        boolean rowInserted = statement.executeUpdate() > 0;
    
        // Fermeture du statement et déconnexion de la base de données
        statement.close();
        dbConnection.disconnect();
    
        // Retour du résultat de l'insertion
        return rowInserted;
    }
    

    // Méthode pour récupérer tous les aliments de la base de données
    public ResultSet listAllAliments() throws SQLException {
        // Requête SQL pour récupérer tous les aliments
        String sql = "SELECT * FROM aliments";
        // Connexion à la base de données
        dbConnection.connect();
    
        // Création d'une déclaration pour exécuter la requête
        Statement statement = dbConnection.getJdbcConnection().createStatement();
    
        // Exécution de la requête et récupération du résultat
        ResultSet resultSet = statement.executeQuery(sql);
    
        // Note : L'appelant doit gérer la fermeture du resultSet et la déconnexion
    
        // Retour du resultSet
        return resultSet;
    }
    
    // Méthode pour l'insertion d'un aliment dans la base de données et l'afficher
    public String insertAlimentAndGet(String nom, float poidsMoyen, int calories, float vitaminesC, Integer typeId, Integer couleurId) throws SQLException {
        // Définition de la requête SQL pour insérer un nouvel aliment
        String insertSql = "INSERT INTO aliments (nom, poids_moyen, calories, vitamines_C, type_id, couleur_id) VALUES (?, ?, ?, ?, ?, ?)";
        // Requête SQL pour récupérer les détails de l'aliment nouvellement inséré
        String selectSql = "SELECT * FROM aliments WHERE id = ?";
        
        // Connexion à la base de données
        dbConnection.connect();
        
        // Utilisation d'un try-with-resources pour s'assurer que les ressources sont correctement fermées
        try (PreparedStatement insertStatement = dbConnection.getJdbcConnection().prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
            // Paramétrage des valeurs pour l'insertion
            insertStatement.setString(1, nom);
            insertStatement.setFloat(2, poidsMoyen);
            insertStatement.setInt(3, calories);
            insertStatement.setFloat(4, vitaminesC);
    
            // Gestion des valeurs nulles pour les clés étrangères
            if (typeId != null) {
                insertStatement.setInt(5, typeId);
            } else {
                insertStatement.setNull(5, Types.INTEGER);
            }
    
            if (couleurId != null) {
                insertStatement.setInt(6, couleurId);
            } else {
                insertStatement.setNull(6, Types.INTEGER);
            }
    
            // Exécution de la requête d'insertion
            insertStatement.executeUpdate();
            
            // Récupération des clés générées (dans ce cas, l'ID de l'aliment nouvellement inséré)
            try (ResultSet generatedKeys = insertStatement.getGeneratedKeys()) {
                if (!generatedKeys.next()) {
                    // En cas d'échec de récupération de l'ID généré
                    throw new SQLException("Creating aliment failed, no ID obtained.");
                }
                int newAlimentId = generatedKeys.getInt(1);
    
                // Préparation de la requête de sélection pour récupérer les détails de l'aliment inséré
                try (PreparedStatement selectStatement = dbConnection.getJdbcConnection().prepareStatement(selectSql)) {
                    selectStatement.setInt(1, newAlimentId);
                    // Exécution de la requête de sélection
                    try (ResultSet resultSet = selectStatement.executeQuery()) {
                        // Conversion du ResultSet en format JSON
                        String jsonResponse = ServletUtils.convertResultSetToJson(resultSet);
                        // Retour du résultat JSON
                        return jsonResponse; 
                    }
                }
            }
        }
    }
    

    // Méthode pour modifier un aliment dans la base de données
    public boolean updateAliment(int id, String nom, float poidsMoyen, int calories, float vitaminesC, int typeId, int couleurId) throws SQLException {
        // Définition de la requête SQL pour mettre à jour un aliment existant
        String sql = "UPDATE aliments SET nom = ?, poids_moyen = ?, calories = ?, vitamines_C = ?, type_id = ?, couleur_id = ? WHERE id = ?";
        // Connexion à la base de données
        dbConnection.connect();
    
        // Utilisation d'un try-with-resources pour s'assurer que les ressources sont correctement fermées
        try (PreparedStatement updateStatement = dbConnection.getJdbcConnection().prepareStatement(sql)) {
            // Paramétrage des valeurs pour la mise à jour
            updateStatement.setString(1, nom);
            updateStatement.setFloat(2, poidsMoyen);
            updateStatement.setInt(3, calories);
            updateStatement.setFloat(4, vitaminesC);
            updateStatement.setInt(5, typeId);
            updateStatement.setInt(6, couleurId);
            updateStatement.setInt(7, id);
    
            // Exécution de la requête de mise à jour et récupération du résultat
            boolean state = updateStatement.executeUpdate() > 0;
    
            // Retour du résultat de la mise à jour
            return state;
        } 
        // Gestion de l'exception
        catch(Exception ex){
            return false;
        }
    }
    

    // Méthode pour supprimer un aliment dans la base de données
    public boolean deleteAliment(int id) throws SQLException {
        // Définition de la requête SQL pour supprimer un aliment en fonction de son identifiant
        String sql = "DELETE FROM aliments WHERE id = ?";
        
        // Connexion à la base de données
        dbConnection.connect();
    
        // Préparation de la requête SQL
        PreparedStatement statement = dbConnection.getJdbcConnection().prepareStatement(sql);
        
        // Paramétrage de la valeur de l'identifiant pour la suppression
        statement.setInt(1, id);
    
        // Exécution de la requête de suppression et récupération du résultat
        boolean rowDeleted = statement.executeUpdate() > 0;
    
        // Fermeture de la déclaration et déconnexion de la base de données
        statement.close();
        dbConnection.disconnect();
    
        // Retour du résultat de la suppression
        return rowDeleted;
    }
    

    // Méthode pour trouver un aliment par son ID
    public ResultSet findById(int id) throws SQLException {
        // Définition de la requête SQL pour récupérer un aliment en fonction de son identifiant
        String sql = "SELECT * FROM aliments WHERE id = ?";
        
        // Connexion à la base de données
        dbConnection.connect();
    
        // Préparation de la requête SQL
        PreparedStatement statement = dbConnection.getJdbcConnection().prepareStatement(sql);
        
        // Paramétrage de la valeur de l'identifiant pour la recherche
        statement.setInt(1, id);
    
        // Exécution de la requête et récupération du résultat dans un ResultSet
        ResultSet resultSet = statement.executeQuery();
    
        // Note : La gestion de la fermeture du ResultSet et de la déconnexion devrait être effectuée par l'appelant
    
        // Retour du ResultSet
        return resultSet;
    }
    


    // Méthode pour trouver un aliment par son Nom
    public ResultSet findByName(String name) throws SQLException {
        // Définition de la requête SQL pour récupérer un aliment en fonction de son nom
        String sql = "SELECT * FROM aliments WHERE nom = ?";
        
        // Connexion à la base de données
        dbConnection.connect();
    
        // Préparation de la requête SQL
        PreparedStatement statement = dbConnection.getJdbcConnection().prepareStatement(sql);
        
        // Paramétrage de la valeur du nom pour la recherche
        statement.setString(1, name);
    
        // Exécution de la requête et retour du ResultSet contenant les résultats de la recherche
        return statement.executeQuery();
    }
    
}

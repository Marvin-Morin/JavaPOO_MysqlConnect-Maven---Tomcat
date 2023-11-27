package com.example.util;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

// La classe ServletUtils fournit des méthodes utilitaires pour traiter les requêtes servlet.
public class ServletUtils {

    // Méthode pour parser le corps de la requête JSON à partir de la requête HTTP.
    public static JsonObject parseJsonRequest(javax.servlet.http.HttpServletRequest request) throws IOException {
        try (
            // Utilisation de try-with-resources pour assurer la fermeture automatique des ressources.
            BufferedReader reader = request.getReader();
            JsonReader jsonReader = Json.createReader(reader)
        ) {
            // Lecture et retour de l'objet JSON à partir du lecteur JSON.
            return jsonReader.readObject();
        }
    }

    // Méthode pour valider la présence de champs obligatoires dans un objet JSON.
    public static void validateRequestData(JsonObject data, String... keys) throws IOException {
        // Parcours des clés fournies.
        for (String key : keys) {
            // Si la valeur associée à la clé est vide, lever une exception indiquant le champ manquant.
            if (data.getString(key, "").isEmpty()) {
                throw new IOException("Missing required field: " + key);
            }
        }
    }

    // Méthode pour convertir un ResultSet en format JSON.
    public static String convertResultSetToJson(ResultSet resultSet) throws SQLException {
        // Utilisation d'une méthode dans une autre classe (ResultSetTableDisplay) pour effectuer la conversion.
        return ResultSetTableDisplay.toJson(resultSet);
    }

    // Méthode pour valider la présence de champs obligatoires spécifiques dans un objet JSON.
    public static void validateRequestData(JsonObject data) throws IOException {
        // Vérification des valeurs des champs 'nom' et 'hexadecimal_rvb'.
        if (data.getString("nom", "").isEmpty() || data.getString("hexadecimal_rvb", "").isEmpty()) {
            throw new IOException("Missing color name or hexadecimal value");
        }
    }

    // Méthode pour envoyer une réponse JSON à la HttpServletResponse.
    public static void sendJsonResponse(HttpServletResponse response, String json) throws IOException {
        // Configuration de l'en-tête de la réponse pour indiquer un contenu JSON.
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            // Écriture de la réponse JSON dans le flux de sortie.
            out.print(json);
        }
    }

    // Méthode pour gérer une exception SQL en renvoyant une réponse d'erreur HTTP.
    public static void handleSqlException(HttpServletResponse response, SQLException ex) throws IOException {
        // Appel à une autre méthode pour envoyer une réponse d'erreur avec le statut 400 (Bad Request) et un message personnalisé.
        sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "SQL Error: " + ex.getMessage());
    }

    // Méthode pour envoyer une réponse d'erreur avec le statut HTTP et un message personnalisé.
    public static void sendErrorResponse(HttpServletResponse response, int statusCode, String message) throws IOException {
        response.sendError(statusCode, message);
    }
}


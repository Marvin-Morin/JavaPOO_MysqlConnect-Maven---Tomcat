package com.example.util;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;


// La classe ResultSetTableDisplay fournit des méthodes pour afficher les résultats d'un ResultSet sous différentes formes.
public class ResultSetTableDisplay {

    // Méthode pour afficher le contenu d'un ResultSet dans la console.
    public static void display(ResultSet resultSet) throws SQLException {
        // Récupération des métadonnées du ResultSet.
        ResultSetMetaData metaData = resultSet.getMetaData();
        // Obtention du nombre de colonnes.
        int columnCount = metaData.getColumnCount();

        // Affichage des en-têtes de colonnes.
        for (int i = 1; i <= columnCount; i++) {
            if (i > 1) System.out.print(",  ");
            System.out.print(metaData.getColumnName(i));
        }
        System.out.println();

        // Affichage des lignes de données.
        while (resultSet.next()) {
            for (int i = 1; i <= columnCount; i++) {
                if (i > 1) System.out.print(",  ");
                System.out.print(resultSet.getString(i));
            }
            System.out.println();
        }
    }

    // Méthode pour générer / créer une table HTML à partir d'un ResultSet.
    public static String toHtmlTable(ResultSet resultSet) throws SQLException {
        // Utilisation d'un StringBuilder pour construire la table HTML.
        StringBuilder htmlTable = new StringBuilder();
        // Récupération des métadonnées du ResultSet.
        ResultSetMetaData metaData = resultSet.getMetaData();
        // Obtention du nombre de colonnes.
        int columnCount = metaData.getColumnCount();

        // Début de la balise de table HTML.
        htmlTable.append("<table border='1'>");

        // En-têtes de colonnes.
        // Tant que columnCount est superieur à i, cela créer une nouvelle colonne
        htmlTable.append("<tr>");
        for (int i = 1; i <= columnCount; i++) {
            htmlTable.append("<th>").append(metaData.getColumnName(i)).append("</th>");
        }
        // Ensuite, pour créer les coluln nous avans besoin d'apporter des tr
        htmlTable.append("</tr>");

        // Données.
        // Une boucle qui tant que la condition est vrais ajoute une colonne
        // Tant qu'il y a des resultats dans la recuperation des données
        while (resultSet.next()) {
            htmlTable.append("<tr>");
            for (int i = 1; i <= columnCount; i++) {
                // Ajout d'un champ dans une ligne
                htmlTable.append("<td>").append(resultSet.getString(i)).append("</td>");
            }
            // Ajout d'une ligne
            htmlTable.append("</tr>");
        }

        // Fin de la balise de table HTML.
        htmlTable.append("</table>");

        // Conversion du StringBuilder en chaîne de caractères.
        return htmlTable.toString();
    }
    
    // Méthode pour afficher une table HTML dans la sortie PrintWriter.
    public static void displayHtmlTable(ResultSet resultSet, PrintWriter out) throws SQLException {
        // Début du document HTML.
        out.println("<html><body>");
        // Appel à la méthode toHtmlTable pour générer la table HTML.
        out.println(toHtmlTable(resultSet));
        // Fin du document HTML.
        out.println("</body></html>");
    }

    // Méthode pour convertir un ResultSet en format JSON.
    public static String toJson(ResultSet resultSet) throws SQLException {
        // Utilisation des classes JSON javax.json pour construire un tableau JSON.
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        // Parcours des lignes du ResultSet.
        while (resultSet.next()) {
            JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
            // Parcours des colonnes.
            for (int i = 1; i <= columnCount; i++) {
                // Ajout de chaque paire clé-valeur dans l'objet JSON.
                jsonObjectBuilder.add(metaData.getColumnName(i), resultSet.getString(i));
            }
            // Ajout de l'objet JSON à l'array JSON.
            jsonArrayBuilder.add(jsonObjectBuilder);
        }

        // Construction de la chaîne JSON à partir de l'array JSON.
        return jsonArrayBuilder.build().toString();
    }
}


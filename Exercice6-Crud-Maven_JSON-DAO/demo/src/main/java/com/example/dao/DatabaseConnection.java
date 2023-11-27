// Déclaration du package où se trouve la classe
package com.example.dao;

// Importation des classes nécessaires pour la gestion de la base de données
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Déclaration de la classe DatabaseConnection
public class DatabaseConnection {

    // Déclaration des variables pour l'URL, le nom d'utilisateur et le mot de passe de la base de données
    private String jdbcURL;
    private String jdbcUsername;
    private String jdbcPassword;

    // Déclaration d'une variable pour la connexion à la base de données
    private Connection jdbcConnection;

    // Constructeur prenant l'URL, le nom d'utilisateur et le mot de passe de la base de données en paramètres
    public DatabaseConnection(String jdbcURL, String jdbcUsername, String jdbcPassword) {
        this.jdbcURL = jdbcURL;
        this.jdbcUsername = jdbcUsername;
        this.jdbcPassword = jdbcPassword;
    }

    // Méthode pour établir la connexion à la base de données
    public void connect() throws SQLException {
        try {
            // Chargement du pilote JDBC pour MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // Gestion de l'exception si le pilote JDBC n'est pas trouvé
            throw new SQLException("MySQL JDBC Driver not found.", e);
        }
        
        // Établissement de la connexion avec la base de données en utilisant l'URL, le nom d'utilisateur et le mot de passe
        jdbcConnection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

    // Méthode pour fermer la connexion à la base de données
    public void disconnect() throws SQLException {
        // Vérification si la connexion est ouverte avant de la fermer
        if (jdbcConnection != null && !jdbcConnection.isClosed()) {
            jdbcConnection.close();
        }
    }

    // Méthode pour obtenir l'objet de connexion à la base de données
    public Connection getJdbcConnection() {
        return jdbcConnection;
    }
}

package com.example;
import java.io.File;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;

import com.example.dao.DatabaseConnection;
import com.example.servlet.AlimentServlet;
import com.example.servlet.TypeAlimentServlet;
import com.example.servlet.CouleurServlet;
import com.example.servlet.DataAccessServletTest;

// La classe principale qui contient la méthode main, point d'entrée du programme.
public class Main {
    
    // La méthode principale qui est appelée au démarrage du programme.
    public static void main(String[] args) throws LifecycleException {
        
        // Création d'une instance de Tomcat, le serveur web embarqué.
        Tomcat tomcat = new Tomcat();
        
        // Configuration du port du serveur Tomcat.
        tomcat.setPort(8080);
        
        // Configuration des informations de connexion à la base de données MySQL.
        String jdbcURL = "jdbc:mysql://localhost:3306/alimentations";
        String jdbcUsername = "root";
        String jdbcPassword = "";
        // Création d'une instance de DatabaseConnection avec les informations de connexion.
        DatabaseConnection dbConnection = new DatabaseConnection(jdbcURL, jdbcUsername, jdbcPassword);

        // Configuration du chemin du contexte web et du répertoire de base du document.
        String contextPath = "/";
        String docBase = new File(".").getAbsolutePath();
        // Ajout d'une application web (contexte) avec le chemin et le répertoire spécifiés.
        Context context = tomcat.addWebapp(contextPath, docBase);

        // Ajout de l'objet DatabaseConnection au contexte de l'application pour le partager avec les servlets.
        context.getServletContext().setAttribute("DB_CONNECTION", dbConnection);

        // Configuration et ajout d'une servlet pour le traitement des données.
        Wrapper servletWrapper = Tomcat.addServlet(context, "DataAccessServlet", new DataAccessServletTest());
        servletWrapper.setLoadOnStartup(1);
        servletWrapper.addMapping("/dataaccess");

        // Configuration et ajout d'une servlet pour la gestion des couleurs.
        Wrapper couleurServletWrapper = Tomcat.addServlet(context, "CouleurServlet", new CouleurServlet());
        couleurServletWrapper.setLoadOnStartup(1);
        couleurServletWrapper.addMapping("/couleur/*");     

        // Configuration et ajout d'une servlet pour la gestion des aliments.
        Wrapper alimentServletWrapper = Tomcat.addServlet(context, "alimentServlet", new AlimentServlet());
        alimentServletWrapper.setLoadOnStartup(1);
        alimentServletWrapper.addMapping("/aliment/*");     
        
        // Configuration et ajout d'une servlet pour la gestion des TypeAliment.
        Wrapper typeAlimentServletWrapper = Tomcat.addServlet(context, "typeAlimentServlet", new TypeAlimentServlet());
        typeAlimentServletWrapper.setLoadOnStartup(1);
        typeAlimentServletWrapper.addMapping("/typealiment/*");   

        // Récupération du connecteur Tomcat.
        tomcat.getConnector();

        // Démarrage du serveur Tomcat.
        tomcat.start();
        
        // Attente indéfinie pour permettre au serveur de continuer à écouter les requêtes.
        tomcat.getServer().await();
    }
}


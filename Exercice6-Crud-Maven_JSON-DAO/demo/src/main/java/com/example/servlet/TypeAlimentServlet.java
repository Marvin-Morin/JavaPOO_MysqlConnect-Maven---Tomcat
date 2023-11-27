package com.example.servlet;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import com.example.dao.TypeAlimentDAO;
import com.example.dao.DatabaseConnection;
import com.example.util.Routeur;

import java.io.IOException;

// L'annotation @WebServlet spécifie le chemin de l'URL pour lequel cette servlet sera responsable.
// Dans ce cas, elle est associée à l'URL "/typealiment/*".
@WebServlet("/typealiment/*")
public class TypeAlimentServlet extends HttpServlet {

    // Déclaration de variables membres pour stocker les instances de TypeAlimentDAO et de Routeur.
    // Le rôle de TypeAlimentDAO est de gérer les interactions avec la base de données pour l'entité "TypeAliment".
    private TypeAlimentDAO typeAlimentDao;
    
    // Le Routeur est utilisé pour diriger le flux de contrôle en fonction du type de requête reçue.
    private Routeur routeur;

    // La méthode init() est appelée lors de l'initialisation de la servlet.
    public void init() {
        // On récupère le contexte de la servlet.
        ServletContext context = getServletContext();

        // On obtient l'objet de connexion à la base de données à partir du contexte.
        DatabaseConnection dbConnection = (DatabaseConnection) context.getAttribute("DB_CONNECTION");

        // On initialise l'instance de TypeAlimentDAO avec la connexion à la base de données.
        typeAlimentDao = new TypeAlimentDAO(dbConnection);

        // On initialise l'instance de Routeur.
        routeur = new Routeur();
    }

    // La méthode service() est héritée de HttpServlet et est utilisée pour gérer les requêtes HTTP.
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // On délègue le traitement de la requête à la méthode routeRequest de l'objet routeur.
        // On passe les objets request, response, et typeAlimentDao en tant que paramètres.
        routeur.routeRequest(request, response, typeAlimentDao);
    }
}


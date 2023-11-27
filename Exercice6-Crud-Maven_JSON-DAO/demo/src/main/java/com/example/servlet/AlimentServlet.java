package com.example.servlet;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import com.example.dao.AlimentDAO;
import com.example.dao.DatabaseConnection;
import com.example.util.Routeur;

import java.io.IOException;

// L'annotation @WebServlet spécifie le chemin de l'URL pour lequel cet servlet sera responsable.
// Dans ce cas, elle est associée à l'URL "/aliment/*".
@WebServlet("/aliment/*")
public class AlimentServlet extends HttpServlet {

    // Déclaration de variables membres pour stocker les instances d'AlimentDAO et de Routeur.
    private AlimentDAO alimentDao;
    private Routeur routeur;

    // La méthode init() est appelée lors de l'initialisation de la servlet.
    public void init() {
        // On récupère le contexte de la servlet.
        ServletContext context = getServletContext();

        // On obtient l'objet de connexion à la base de données à partir du contexte.
        DatabaseConnection dbConnection = (DatabaseConnection) context.getAttribute("DB_CONNECTION");

        // On initialise l'instance d'AlimentDAO avec la connexion à la base de données.
        alimentDao = new AlimentDAO(dbConnection);

        // On initialise l'instance de Routeur.
        routeur = new Routeur();
    }

    // La méthode service() est héritée de HttpServlet et est utilisée pour gérer les requêtes HTTP.
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // On délègue le traitement de la requête à la méthode routeRequest de l'objet routeur.
        // On passe les objets request, response, et alimentDao en tant que paramètres.
        routeur.routeRequest(request, response, alimentDao);
    }

    // Vous pouvez ajouter des méthodes supplémentaires si nécessaire.
}
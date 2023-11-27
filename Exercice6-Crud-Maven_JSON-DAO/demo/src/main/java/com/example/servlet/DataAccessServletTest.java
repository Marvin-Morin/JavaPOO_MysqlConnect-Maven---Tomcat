package com.example.servlet;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.example.dao.AlimentDAO;
import com.example.dao.CouleurDAO;
import com.example.dao.DatabaseConnection;
import com.example.util.ResultSetTableDisplay;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

// L'annotation @WebServlet spécifie le chemin de l'URL pour lequel cette servlet sera responsable.
// Dans ce cas, elle est associée à l'URL "/dataaccess".
@WebServlet("/dataaccess")
public class DataAccessServletTest extends HttpServlet  {

    // Déclaration de variables membres pour stocker les instances de DatabaseConnection, CouleurDAO et AlimentDAO.
    private DatabaseConnection dbConnection;
    private CouleurDAO couleurDao;
    private AlimentDAO alimentDao;

    // La méthode init() est appelée lors de l'initialisation de la servlet.
    public void init() {
        // On récupère le contexte de la servlet.
        ServletContext contextdb = getServletContext();

        // On obtient l'objet de connexion à la base de données à partir du contexte.
        dbConnection = (DatabaseConnection) contextdb.getAttribute("DB_CONNECTION");

        // On initialise les instances de CouleurDAO et AlimentDAO avec la connexion à la base de données.
        couleurDao = new CouleurDAO(dbConnection);
        alimentDao = new AlimentDAO(dbConnection);
    }

    // La méthode doGet() est appelée pour gérer les requêtes HTTP de type GET.
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // On établit la connexion à la base de données.
            dbConnection.connect();

            // Ici, vous pouvez effectuer des opérations sur la base de données.
            // Dans cet exemple, les données de toutes les couleurs et tous les aliments sont récupérées et affichées.

            // Configuration de la réponse HTTP pour renvoyer du contenu HTML.
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            
            // Envoi du HTML à la réponse en utilisant la classe ResultSetTableDisplay.
            ResultSetTableDisplay.displayHtmlTable(couleurDao.listAllCouleurs(), out);
            ResultSetTableDisplay.displayHtmlTable(alimentDao.listAllAliments(), out);

            // On termine la connexion à la base de données.
            dbConnection.disconnect();
        } catch (SQLException e) {
            // En cas d'erreur SQL, imprimer la trace de la pile.
            e.printStackTrace();
        }
    }
}


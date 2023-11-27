package com.example.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.dao.IGenericCRUD;

import java.io.IOException;

// La classe Routeur gère le routage des requêtes HTTP vers les méthodes correspondantes d'un objet DAO (Data Access Object).
public class Routeur {

    // Méthode pour router la requête HTTP vers la méthode appropriée de l'objet dao.
    public void routeRequest(HttpServletRequest request, HttpServletResponse response, IGenericCRUD dao) throws IOException {
        // Récupération de la méthode HTTP de la requête.
        String method = request.getMethod();
        // Récupération des paramètres de requête nécessaires.
        String idParam = request.getParameter("id");
        String nameParam = request.getParameter("name");

        try {
            // Vérification des conditions pour certaines méthodes.
            if ("GET".equals(method) && nameParam != null) {
                // Si la méthode est GET et le paramètre "name" est présent, appeler la méthode handleFindByName de l'objet dao.
                dao.handleFindByName(request, response);
            } else if ("GET".equals(method) && idParam != null) {
                // Si la méthode est GET et le paramètre "id" est présent, appeler la méthode handleFindById de l'objet dao.
                dao.handleFindById(request, response);
            } else {
                // Utilisation d'une instruction switch pour déterminer la méthode et appeler la méthode correspondante de l'objet dao.
                switch (method) {
                    case "GET":
                        dao.handleGet(request, response);
                        break;
                    case "POST":
                        dao.handlePost(request, response);
                        break;
                    case "PUT":
                        dao.handlePut(request, response);
                        break;
                    case "DELETE":
                        dao.handleDelete(request, response);
                        break;
                    default:
                        // Si la méthode n'est pas reconnue, renvoyer une erreur "Méthode non supportée".
                        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Méthode non supportée");
                        break;
                }
            }
        } catch (Exception e) {
            // En cas d'exception, imprimer la trace de l'exception et renvoyer une erreur "Erreur interne du serveur".
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur interne du serveur");
        }
    }
}


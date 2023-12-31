/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/9.0.41
 * Generated at: 2023-11-20 10:12:01 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.demo.webapps.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class AlimentUpdateForm_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = null;
  }

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public javax.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
      throws java.io.IOException, javax.servlet.ServletException {

    if (!javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
      final java.lang.String _jspx_method = request.getMethod();
      if ("OPTIONS".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        return;
      }
      if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPs only permit GET, POST or HEAD. Jasper also permits OPTIONS");
        return;
      }
    }

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\n");
      out.write("<html>\n");
      out.write("<head>\n");
      out.write("    <title>Update Aliment</title>\n");
      out.write("    <link rel=\"stylesheet\" type=\"text/css\" href=\"../css/style.css\">\n");
      out.write("    <!-- Inclure les fichiers CSS/JS si nécessaire -->\n");
      out.write("</head>\n");
      out.write("<body>\n");
      out.write("    <!-- Inclure le menu -->\n");
      out.write("    ");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "menu.jsp", out, false);
      out.write("\n");
      out.write("\n");
      out.write("    <h1>Alimentations</h1>\n");
      out.write("    <h2>Update Aliment Form</h2>\n");
      out.write("    <form id=\"updateAlimentForm\" action=\"/aliment\" method=\"post\">\n");
      out.write("        <label for=\"id\">ID de l'Aliment:</label>\n");
      out.write("        <select id=\"id\" name=\"id\" required></select><br><br>\n");
      out.write("\n");
      out.write("        <label for=\"nom\">Nom:</label>\n");
      out.write("        <input type=\"text\" id=\"nom\" name=\"nom\" required><br><br>\n");
      out.write("\n");
      out.write("        <label for=\"poids_moyen\">Poids Moyen:</label>\n");
      out.write("        <input type=\"number\" step=\"0.01\" id=\"poids_moyen\" name=\"poids_moyen\" required><br><br>\n");
      out.write("\n");
      out.write("        <label for=\"calories\">Calories:</label>\n");
      out.write("        <input type=\"number\" id=\"calories\" name=\"calories\" required><br><br>\n");
      out.write("\n");
      out.write("        <label for=\"vitamines_C\">Vitamines C:</label>\n");
      out.write("        <input type=\"number\" step=\"0.01\" id=\"vitamines_C\" name=\"vitamines_C\" required><br><br>\n");
      out.write("\n");
      out.write("        <label for=\"type_id\">Type ID:</label>\n");
      out.write("        <input type=\"number\" id=\"type_id\" name=\"type_id\" required><br><br>\n");
      out.write("\n");
      out.write("        <label for=\"couleur_id\">Couleur ID:</label>\n");
      out.write("        <select id=\"couleur_id\" name=\"couleur_id\" required></select><br><br>\n");
      out.write("\n");
      out.write("        <input type=\"submit\" value=\"Update\">\n");
      out.write("    </form>\n");
      out.write("    <!-- Élément pour afficher la réponse -->\n");
      out.write("    <div id=\"response\"></div>\n");
      out.write("\n");
      out.write("    <script>\n");
      out.write("        // Fonction pour charger les données d'un aliment spécifique\n");
      out.write("        function loadFormData(alimentId) {\n");
      out.write("            fetch('http://localhost:8080/aliment?id=' + alimentId)\n");
      out.write("                .then(response => response.json())\n");
      out.write("                .then(data => {\n");
      out.write("                    if (data.length > 0) {\n");
      out.write("                        var aliment = data[0];\n");
      out.write("                        document.getElementById('nom').value = aliment.nom;\n");
      out.write("                        document.getElementById('poids_moyen').value = aliment.poids_moyen;\n");
      out.write("                        document.getElementById('calories').value = aliment.calories;\n");
      out.write("                        document.getElementById('vitamines_C').value = aliment.vitamines_C;\n");
      out.write("                        document.getElementById('type_id').value = aliment.type_id;\n");
      out.write("                        document.getElementById('couleur_id').value = aliment.couleur_id;\n");
      out.write("                    }\n");
      out.write("                })\n");
      out.write("                .catch(error => console.error('Error:', error));\n");
      out.write("        }\n");
      out.write("    \n");
      out.write("        window.onload = function() {\n");
      out.write("            var urlParams = new URLSearchParams(window.location.search);\n");
      out.write("            var alimentId = urlParams.get('id');\n");
      out.write("    \n");
      out.write("            // Chargement des options pour l'ID de l'aliment\n");
      out.write("            fetch('http://localhost:8080/aliment')\n");
      out.write("                .then(response => response.json())\n");
      out.write("                .then(data => {\n");
      out.write("                    var selectAliment = document.getElementById('id');\n");
      out.write("                    data.forEach(function(aliment) {\n");
      out.write("                        var option = new Option(aliment.nom, aliment.id);\n");
      out.write("                        selectAliment.add(option);\n");
      out.write("                    });\n");
      out.write("                    if (alimentId) {\n");
      out.write("                        selectAliment.value = alimentId;\n");
      out.write("                        loadFormData(alimentId);\n");
      out.write("                    }\n");
      out.write("                })\n");
      out.write("                .catch(error => console.error('Error:', error));\n");
      out.write("    \n");
      out.write("            // Chargement des options de couleur\n");
      out.write("            fetch('http://localhost:8080/couleur')\n");
      out.write("                .then(response => response.json())\n");
      out.write("                .then(data => {\n");
      out.write("                    var selectCouleur = document.getElementById('couleur_id');\n");
      out.write("                    data.forEach(function(couleur) {\n");
      out.write("                        var option = new Option(couleur.nom, couleur.id);\n");
      out.write("                        selectCouleur.add(option);\n");
      out.write("                    });\n");
      out.write("                })\n");
      out.write("                .catch(error => console.error('Error:', error));\n");
      out.write("    \n");
      out.write("            // Mettre à jour les champs lors du changement de l'ID sélectionné\n");
      out.write("            document.getElementById('id').addEventListener('change', function() {\n");
      out.write("                loadFormData(this.value);\n");
      out.write("            });\n");
      out.write("        };\n");
      out.write("    \n");
      out.write("        // Logique de soumission du formulaire\n");
      out.write("        document.getElementById('updateAlimentForm').addEventListener('submit', function(event) {\n");
      out.write("            event.preventDefault();\n");
      out.write("    \n");
      out.write("            var formData = new FormData(this);\n");
      out.write("            var jsonData = {};\n");
      out.write("            for (var [key, value] of formData.entries()) {\n");
      out.write("                if (key === 'poids_moyen' || key === 'vitamines_C') {\n");
      out.write("                    jsonData[key] = parseFloat(value);\n");
      out.write("                } else if (key === 'calories' || key === 'type_id' || key === 'couleur_id' || key === 'id') {\n");
      out.write("                    jsonData[key] = parseInt(value);\n");
      out.write("                } else {\n");
      out.write("                    jsonData[key] = value;\n");
      out.write("                }\n");
      out.write("            }\n");
      out.write("    \n");
      out.write("            fetch('http://localhost:8080/aliment', {\n");
      out.write("                method: 'PUT',\n");
      out.write("                headers: {\n");
      out.write("                    'Content-Type': 'application/json',\n");
      out.write("                },\n");
      out.write("                body: JSON.stringify(jsonData)\n");
      out.write("            })\n");
      out.write("            .then(response => {\n");
      out.write("                if (!response.ok) {\n");
      out.write("                    throw new Error('Network response was not ok: ' + response.statusText);\n");
      out.write("                }\n");
      out.write("                return response.json();\n");
      out.write("            })\n");
      out.write("            .then(data => {\n");
      out.write("                document.getElementById('response').innerHTML = 'Response: ' + JSON.stringify(data);\n");
      out.write("            })\n");
      out.write("            .catch(error => {\n");
      out.write("                console.error('Error:', error);\n");
      out.write("                document.getElementById('response').innerHTML = 'Error: ' + error.toString();\n");
      out.write("            });\n");
      out.write("        });\n");
      out.write("    </script>\n");
      out.write("</body>\n");
      out.write("</html>\n");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Menu de Navigation</title>
    <!-- Ajouter ici le CSS si nécessaire -->
    <style>
        body {
            font-family: Arial, sans-serif;
        }

        nav ul {
            list-style-type: none;
            margin: 0;
            padding: 0;
            overflow: hidden;
            background-color: #333;
        }

        nav ul li {
            float: left;
        }

        nav ul li a {
            display: block;
            color: white;
            text-align: center;
            padding: 14px 16px;
            text-decoration: none;
        }

        nav ul li a:hover {
            background-color: #111;
        }
    </style>

</head>
<body>
    <nav>
        <ul>
            <li><a href="http://localhost:8080/demo/webapps/jsp/AlimentForm.jsp">Create Aliment</a></li>
            <li><a href="http://localhost:8080/demo/webapps/jsp/AlimentUpdateForm.jsp">Update Aliment</a></li>
            <li><a href="http://localhost:8080/demo/webapps/jsp/AlimentListForm.jsp">List Aliments</a></li>

            <li><a href="http://localhost:8080/demo/webapps/jsp/CouleurForm.jsp">Create Couleur</a></li>
            <li><a href="http://localhost:8080/demo/webapps/jsp/CouleurUpdateForm.jsp">Update Couleur</a></li>
            <li><a href="http://localhost:8080/demo/webapps/jsp/CouleurListForm.jsp">List Couleurs</a></li>
            <!-- Ajouter d'autres liens si nécessaire -->
        </ul>
    </nav>

    <!-- Contenu de la page -->
</body>
</html>

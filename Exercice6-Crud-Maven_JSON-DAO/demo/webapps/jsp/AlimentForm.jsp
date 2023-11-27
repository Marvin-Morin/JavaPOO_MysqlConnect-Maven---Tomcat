<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Aliment</title>
    <link rel="stylesheet" type="text/css" href="../css/style.css">
    <!-- Inclure les fichiers CSS/JS si nécessaire -->
    <!-- http://localhost:8080/demo/webapps/jsp/AlimentForm.jsp -->
</head>
<body>
     <!-- Inclure le menu -->
     <jsp:include page="menu.jsp" />

     <h1>Alimentations</h1>
    <h2>Create Aliment Form</h2>
    <form id="createAlimentForm" action="/aliment" method="post">
        <label for="nom">Name:</label>
        <input type="text" id="nom" name="nom" required><br><br>
        
        <label for="poids_moyen">Average Weight:</label>
        <input type="number" step="0.01" id="poids_moyen" name="poids_moyen" required><br><br>
        
        <label for="calories">Calories:</label>
        <input type="number" id="calories" name="calories" required><br><br>
        
        <label for="vitamines_C">Vitamin C:</label>
        <input type="number" step="0.01" id="vitamines_C" name="vitamines_C" required><br><br>
        
        <label for="type_id">Type ID:</label>
        <input type="number" id="type_id" name="type_id" required><br><br>
        
        <label for="couleur_id">Color ID:</label>
        <select id="couleur_id" name="couleur_id" required></select><br><br>
        
        <input type="submit" value="Submit">
    </form>
    <!-- Élément pour afficher la réponse -->
    <div id="response"></div>
    <script>
        // Charger les options de couleur au chargement de la page
        window.onload = function() {
            fetch('http://localhost:8080/couleur')
                .then(response => response.json())
                .then(data => {
                    var select = document.getElementById('couleur_id');
                    data.forEach(function(couleur) {
                        var option = new Option(couleur.nom, couleur.id);
                        select.add(option);
                    });
                })
                .catch(error => console.error('Error:', error));
        };

      document.getElementById('createAlimentForm').addEventListener('submit', function(event) {
    event.preventDefault();

    var formData = new FormData(this);
    var jsonData = {};

    for (var [key, value] of formData.entries()) {
        if (key === 'poids_moyen' || key === 'vitamines_C'  ) {
        jsonData[key] = parseFloat(value);
    } else if( key === 'calories' || key === 'type_id' || key === 'couleur_id'){
        jsonData[key] = parseInt(value);
    } 
    else {
        jsonData[key] = value;
    }
    }

    console.log(jsonData);

    fetch('http://localhost:8080/aliment', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(jsonData)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok: ' + response.statusText);
        }
        return response.json();
    })
    .then(data => {
        // Traiter la réponse (par exemple, afficher un message de succès)
        document.getElementById('response').innerHTML = 'Response: ' + JSON.stringify(data);
    })
    .catch(error => {
        // Gérer les erreurs ici
        console.error('Error:', error);
    });
});

    </script>
    

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update Aliment</title>
    <link rel="stylesheet" type="text/css" href="../css/style.css">
    <!-- Inclure les fichiers CSS/JS si nécessaire -->
</head>
<body>
    <!-- Inclure le menu -->
    <jsp:include page="menu.jsp" />

    <h1>Alimentations</h1>
    <h2>Update Aliment Form</h2>
    <form id="updateAlimentForm" action="/aliment" method="post">
        <label for="id">ID de l'Aliment:</label>
        <select id="id" name="id" required></select><br><br>

        <label for="nom">Nom:</label>
        <input type="text" id="nom" name="nom" required><br><br>

        <label for="poids_moyen">Poids Moyen:</label>
        <input type="number" step="0.01" id="poids_moyen" name="poids_moyen" required><br><br>

        <label for="calories">Calories:</label>
        <input type="number" id="calories" name="calories" required><br><br>

        <label for="vitamines_C">Vitamines C:</label>
        <input type="number" step="0.01" id="vitamines_C" name="vitamines_C" required><br><br>

        <label for="type_id">Type ID:</label>
        <input type="number" id="type_id" name="type_id" required><br><br>

        <label for="couleur_id">Couleur ID:</label>
        <select id="couleur_id" name="couleur_id" required></select><br><br>

        <input type="submit" value="Update">
    </form>
    <!-- Élément pour afficher la réponse -->
    <div id="response"></div>

    <script>
        // Fonction pour charger les données d'un aliment spécifique
        function loadFormData(alimentId) {
            fetch('http://localhost:8080/aliment?id=' + alimentId)
                .then(response => response.json())
                .then(data => {
                    if (data.length > 0) {
                        var aliment = data[0];
                        document.getElementById('nom').value = aliment.nom;
                        document.getElementById('poids_moyen').value = aliment.poids_moyen;
                        document.getElementById('calories').value = aliment.calories;
                        document.getElementById('vitamines_C').value = aliment.vitamines_C;
                        document.getElementById('type_id').value = aliment.type_id;
                        document.getElementById('couleur_id').value = aliment.couleur_id;
                    }
                })
                .catch(error => console.error('Error:', error));
        }
    
        window.onload = function() {
            var urlParams = new URLSearchParams(window.location.search);
            var alimentId = urlParams.get('id');
    
            // Chargement des options pour l'ID de l'aliment
            fetch('http://localhost:8080/aliment')
                .then(response => response.json())
                .then(data => {
                    var selectAliment = document.getElementById('id');
                    data.forEach(function(aliment) {
                        var option = new Option(aliment.nom, aliment.id);
                        selectAliment.add(option);
                    });
                    if (alimentId) {
                        selectAliment.value = alimentId;
                        loadFormData(alimentId);
                    }
                })
                .catch(error => console.error('Error:', error));
    
            // Chargement des options de couleur
            fetch('http://localhost:8080/couleur')
                .then(response => response.json())
                .then(data => {
                    var selectCouleur = document.getElementById('couleur_id');
                    data.forEach(function(couleur) {
                        var option = new Option(couleur.nom, couleur.id);
                        selectCouleur.add(option);
                    });
                })
                .catch(error => console.error('Error:', error));
    
            // Mettre à jour les champs lors du changement de l'ID sélectionné
            document.getElementById('id').addEventListener('change', function() {
                loadFormData(this.value);
            });
        };
    
        // Logique de soumission du formulaire
        document.getElementById('updateAlimentForm').addEventListener('submit', function(event) {
            event.preventDefault();
    
            var formData = new FormData(this);
            var jsonData = {};
            for (var [key, value] of formData.entries()) {
                if (key === 'poids_moyen' || key === 'vitamines_C') {
                    jsonData[key] = parseFloat(value);
                } else if (key === 'calories' || key === 'type_id' || key === 'couleur_id' || key === 'id') {
                    jsonData[key] = parseInt(value);
                } else {
                    jsonData[key] = value;
                }
            }
    
            fetch('http://localhost:8080/aliment', {
                method: 'PUT',
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
                document.getElementById('response').innerHTML = 'Response: ' + JSON.stringify(data);
            })
            .catch(error => {
                console.error('Error:', error);
                document.getElementById('response').innerHTML = 'Error: ' + error.toString();
            });
        });
    </script>
</body>
</html>

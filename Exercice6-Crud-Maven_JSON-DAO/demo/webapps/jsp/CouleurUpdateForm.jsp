<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <html>

    <head>
        <title>Update Couleur</title>
        <link rel="stylesheet" type="text/css" href="../css/style.css">
        <!-- Inclure les fichiers CSS/JS si nécessaire -->
        <!-- http://localhost:8080/demo/webapps/jsp/CouleurUpdateForm.jsp -->
    </head>

    <body>
        <!-- Inclure le menu -->
        <jsp:include page="menu.jsp" />

        <h1>Alimentations</h1>
        <h2>Update Couleur Form</h2>
        <form id="updateCouleurForm" action="/couleur" method="post">
            <label for="id">ID de la Couleur:</label>
            <select id="id" name="id" required></select><br><br>

            <label for="nom">Nom de la Couleur:</label>
            <input type="text" id="nom" name="nom" required><br><br>

            <label for="hexadecimal_rvb">Code Hexadecimal RGB:</label>
            <input type="text" id="hexadecimal_rvb" name="hexadecimal_rvb" required><br><br>

            <input type="submit" value="Update">
        </form>
        <!-- Élément pour afficher la réponse -->
        <div id="response"></div>
        <script>

        // Charger les IDs au chargement de la page
         // Charger les IDs au chargement de la page et pré-sélectionner l'ID passé dans l'URL
         window.onload = function() {
            var queryParams = new URLSearchParams(window.location.search);
            var selectedId = queryParams.get('id'); // Récupérer l'ID depuis l'URL

            fetch('http://localhost:8080/couleur')
                .then(response => response.json())
                .then(data => {
                    var select = document.getElementById('id');
                    data.forEach(function(couleur) {
                        var option = new Option(couleur.id, couleur.id);
                        select.add(option);
                        if (couleur.id == selectedId) {
                            option.selected = true; // Pré-sélectionner l'option si elle correspond à l'ID
                        }
                    });

                    // Charger les données de la couleur sélectionnée
                    if (selectedId) {
                        fetch('http://localhost:8080/couleur?id=' + selectedId)
                            .then(response => response.json())
                            .then(data => {
                                if(data.length > 0) {
                                    var couleur = data[0];
                                    document.getElementById('nom').value = couleur.nom;
                                    document.getElementById('hexadecimal_rvb').value = couleur.hexadecimal_rvb;
                                }
                            })
                            .catch(error => console.error('Error:', error));
                    }
                });
        };

        // Mettre à jour les champs lors du changement de l'ID sélectionné
        document.getElementById('id').addEventListener('change', function() {
                var id = this.value;
                fetch('http://localhost:8080/couleur?id=' + id)
                    .then(response => response.json())
                    .then(data => {
                        if(data.length > 0) {
                            var couleur = data[0]; // Supposant que la réponse est un tableau d'objets
                            document.getElementById('nom').value = couleur.nom;
                            document.getElementById('hexadecimal_rvb').value = couleur.hexadecimal_rvb;
                        }
                    })
                    .catch(error => console.error('Error:', error));
            });


            document.getElementById('updateCouleurForm').addEventListener('submit', function (event) {
                event.preventDefault();

                var formData = new FormData(this);
                var jsonData = {};

                for (var [key, value] of formData.entries()) {
                    if (key === 'id') {
                        jsonData[key] = parseInt(value);
                    } else {
                        jsonData[key] = value;
                    }
                }

                console.log(jsonData);

                // Remplacer par l'URL et la méthode appropriées pour votre API
                fetch('http://localhost:8080/couleur', {
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
                        // Traiter la réponse (par exemple, afficher un message de succès)
                        document.getElementById('response').innerHTML = 'Response: ' + JSON.stringify(data);
                    })
                    .catch(error => {
                        // Gérer les erreurs ici
                        console.error('Error:', error);
                    });
            });
        </script>
    </body>

    </html>
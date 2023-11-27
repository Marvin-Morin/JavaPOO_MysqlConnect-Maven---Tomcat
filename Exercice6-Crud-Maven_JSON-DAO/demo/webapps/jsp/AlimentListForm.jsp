<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Liste des Aliments</title>
    <link rel="stylesheet" type="text/css" href="../css/style.css">
    <!-- Inclure les fichiers CSS/JS si nécessaire -->
</head>
<body>
    <!-- Inclure le menu -->
    <jsp:include page="menu.jsp" />

    <h1>Liste des Aliments</h1>
    <form id="deleteAlimentForm">
        <table>
            <tr>
                <th>ID</th>
                <th>Nom</th>
                <th>Poids Moyen</th>
                <th>Calories</th>
                <th>Vitamines C</th>
                <th>Type ID</th>
                <th>Couleur ID</th>
                <th>Supprimer</th>
                <th>Modifier</th>
            </tr>
            <!-- Les lignes de la table seront remplis par un script JavaScript -->
        </table>
        <input type="submit" value="Supprimer les aliments sélectionnés">
    </form>

    <script>
        window.onload = function() {
            fetch('http://localhost:8080/aliment')
                .then(response => response.json())
                .then(data => {
                    var table = document.querySelector('#deleteAlimentForm table');
                    data.forEach(function(aliment) {
                        var row = table.insertRow();
                        row.insertCell().textContent = aliment.id;
                        row.insertCell().textContent = aliment.nom;
                        row.insertCell().textContent = aliment.poids_moyen;
                        row.insertCell().textContent = aliment.calories;
                        row.insertCell().textContent = aliment.vitamines_C;
                        row.insertCell().textContent = aliment.type_id;
                        row.insertCell().textContent = aliment.couleur_id;

                        // Ajouter une checkbox pour la suppression
                        var deleteCell = row.insertCell();
                        var checkbox = document.createElement('input');
                        checkbox.type = 'checkbox';
                        checkbox.name = 'delete';
                        checkbox.value = aliment.id;
                        deleteCell.appendChild(checkbox);

                        // Ajouter un bouton pour la modification
                        var modifyCell = row.insertCell();
                        var modifyButton = document.createElement('button');
                        modifyButton.textContent = 'Modifier';
                        modifyButton.onclick = function() {
                            event.preventDefault(); // Empêcher la soumission du formulaire
                            window.location.href = 'AlimentUpdateForm.jsp?id=' + aliment.id;
                        };
                        modifyCell.appendChild(modifyButton);
                    });
                })
                .catch(error => console.error('Error:', error));
        };

        document.getElementById('deleteAlimentForm').addEventListener('submit', function(event) {
            event.preventDefault();

            var checkboxes = document.querySelectorAll('input[name="delete"]:checked');
            if (checkboxes.length === 0) {
                alert("Aucun aliment n'est sélectionné pour la suppression.");
                return;
            }
            // Afficher un popup de confirmation
            var userConfirmed = confirm("Êtes-vous sûr de vouloir supprimer les aliments sélectionnés ?");
            if (!userConfirmed) {
                return; // Arrêter si l'utilisateur n'a pas confirmé
            }
            
            var idsToDelete = Array.from(checkboxes).map(cb => cb.value);

            idsToDelete.forEach(function(id) {
                fetch('http://localhost:8080/aliment', {
                    method: 'DELETE',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({ id: parseInt(id) })
                })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Network response was not ok: ' + response.statusText);
                    }
                    return response.json();
                })
                .then(data => {
                    console.log('Aliment supprimé:', data);
                    // Vous pouvez ici actualiser la page ou supprimer la ligne correspondante du tableau
                     window.location.reload(); // Recharger la page
                })
                .catch(error => {
                    console.error('Error:', error);
                });
            });
        });
    </script>
</body>
</html>

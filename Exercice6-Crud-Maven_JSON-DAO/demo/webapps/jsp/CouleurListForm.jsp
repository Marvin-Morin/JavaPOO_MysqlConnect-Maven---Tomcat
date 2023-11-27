<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Liste des Couleurs</title>
    <link rel="stylesheet" type="text/css" href="../css/style.css">
    <!-- Inclure les fichiers CSS/JS si nécessaire -->
</head>
<body>
    <!-- Inclure le menu -->
    <jsp:include page="menu.jsp" />

    <h1>Liste des Couleurs</h1>
    <form id="deleteCouleurForm">
        <table>
            <tr>
                <th>ID</th>
                <th>Nom</th>
                <th>Hexadecimal RVB</th>
                <th>Supprimer</th>
                <th>Modifier</th>
            </tr>
            <!-- Les lignes de la table seront remplis par un script JavaScript -->
        </table>
        <input type="submit" value="Supprimer les couleurs sélectionnées">
    </form>

    <script>
        window.onload = function() {
            fetch('http://localhost:8080/couleur')
                .then(response => response.json())
                .then(data => {
                    var table = document.querySelector('#deleteCouleurForm table');
                    data.forEach(function(couleur) {
                        var row = table.insertRow();
                        row.insertCell().textContent = couleur.id;
                        row.insertCell().textContent = couleur.nom;
                        row.insertCell().textContent = couleur.hexadecimal_rvb;
 
                        // Ajouter une checkbox pour la suppression
                        var deleteCell = row.insertCell();
                        var checkbox = document.createElement('input');
                        checkbox.type = 'checkbox';
                        checkbox.name = 'delete';
                        checkbox.value = couleur.id;
                        deleteCell.appendChild(checkbox);

                        // Ajouter un bouton pour la modification
                        var modifyCell = row.insertCell();
                        var modifyButton = document.createElement('button');
                        modifyButton.textContent = 'Modifier';
                        modifyButton.onclick = function() {
                            event.preventDefault(); // Empêcher la soumission du formulaire
                            window.location.href = 'CouleurUpdateForm.jsp?id=' + couleur.id;
                        };
                        modifyCell.appendChild(modifyButton);
                    });
                })
                .catch(error => console.error('Error:', error));
        };

        document.getElementById('deleteCouleurForm').addEventListener('submit', function(event) {
            event.preventDefault();

            var checkboxes = document.querySelectorAll('input[name="delete"]:checked');
            if (checkboxes.length === 0) {
                alert("Aucune couleur n'est sélectionnée pour la suppression.");
                return;
            }
            // Afficher un popup de confirmation
            var userConfirmed = confirm("Êtes-vous sûr de vouloir supprimer les couleurs sélectionnées ?");
            if (!userConfirmed) {
                return; // Arrêter si l'utilisateur n'a pas confirmé
            }
            
                     
            var idsToDelete = Array.from(checkboxes).map(cb => cb.value);

            idsToDelete.forEach(function(id) {
                fetch('http://localhost:8080/couleur', {
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
                    console.log('Couleur supprimée:', data);
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

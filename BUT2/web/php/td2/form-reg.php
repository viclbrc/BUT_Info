<html>
    <head>
        <link rel="stylesheet" type="text/css" href="style.css">
    </head>
    <body>
        <header>
            <h1>Formulaire département et région</h1>
        </header>
        <main>
        <form method="post" action="liste-depts.php">
            <label for="dept">Nom du département :</label>
            <br>
            <input type="text" id="dept" name="dept" required>
            <br>

            <label for="region">Région :</label>
            <br>
            <select id="region" name="region" required>
                <option value="">Choisissez une région</option>
                <?php
                require_once 'regions.php';
                foreach ($regions as $region => $deptCodes) {
                    echo "<option value=\"$region\">$region</option>";
                }
                ?>
            </select>
            <br>
            <input type="submit" value="Soumettre">
        </form>
        </main>
    </body>
</html>
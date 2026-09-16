function plusOuMoins() {
    let val = Math.round(Math.random() * 100);
    let valFinale = val;
    let cmp = 0;
    let guess;
    let rematch;

    while (guess != valFinale) {
        guess = prompt("Devinez le nombre entre 0 et 100 : ");
        if (typeof(guess) != "number") {
            alert("Veuillez entrer un nombre valide.");
        } else {
            cmp++;
            if (guess < valFinale) {
                alert("C'est plus !");
            } else if (guess > valFinale) {
                alert("C'est moins !");
            }
        }
    }
    if (guess == valFinale) {
        rematch = prompt("Bravo ! Vous avez trouvé le nombre en " + cmp + " tentatives. Voulez-vous rejouer ? (O/N) ");
        if (rematch == "O" || rematch == "o") {
            plusOuMoins();
        }
    }
}

plusOuMoins();
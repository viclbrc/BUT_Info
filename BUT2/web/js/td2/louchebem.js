function louchebem() {
    let mot = prompt("Entrez un mot en louchébem : ");
    mot = mot.trim();
    console.log(mot);
    mot = mot.toLowerCase();
    console.log(mot);
    let premiere_lettre = mot.charAt(0);
    mot = mot.replace(mot.charAt(0), "l");
    console.log(mot);
    console.log(premiere_lettre);
    mot = mot.concat(premiere_lettre);
    switch (premiere_lettre) {
        case "b":
            mot = mot.concat("em");
            break;
        case "d":
        case "p":
            mot = mot.concat("é");
            break;
        case "f":
            mot = mot.concat("oque");
            break;
        case "m":
            mot = mot.concat("uche");
            break;
        default:
            mot = mot.concat("c");
    }
    console.log(mot);
    alert("Le mot en louchébem est : " + mot);
}

louchebem();
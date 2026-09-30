let maFenetre;

function ouvrirFenetre() {
    maFenetre = open('', 'popUp', "width=250, height=100");
    maFenetre.document.write("<h1>Bonjour à tous !</h1>");
    maFenetre.focus();
}
function placerFenetre() {
    maFenetre.moveTo(100, 250);
}
function decalerFenetre() {
    maFenetre.moveBy(0, 200);
}
function agrandirFenetre() {
    maFenetre.resizeTo(500, 200);
}
function fermerFenetre() {
    maFenetre.close();
}

ouvrirFenetre();
setTimeout(placerFenetre, 1500);
setTimeout(decalerFenetre, 2500);
setTimeout(agrandirFenetre, 3500);
setTimeout(fermerFenetre, 4500);
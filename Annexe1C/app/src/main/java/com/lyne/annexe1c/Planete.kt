package com.lyne.annexe1c

//data : représenter des classes qui servent à entreposer des données dans faire du traitement dessus
//avantage : une méthode equals (==) est automatiquement générée, une méthode toString adaptée aussi
data class Planete (var nom: String, var nbSatellites:Int) {

    //constructeur primaire, on ne peut pas faire d'autres initialisations, seulement y passer des paramètres
    // le constructeur n'a pas de code
}
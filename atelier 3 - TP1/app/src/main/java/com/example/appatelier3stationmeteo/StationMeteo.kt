package com.example.appatelier3stationmeteo

class StationMeteo : Sujet{

    var temperature: Double = 0.0

    private val observateurs = ArrayList<Observateur>()

    fun changerTemperature(nouvelleTemperature: Double) {
        temperature = nouvelleTemperature
        avertirObservateurs()
    }





    override fun ajouterObservateur(o: Observateur) {
        observateurs.add(o)
    }

    override fun retirerObservateur(o: Observateur) {
        observateurs.remove(o)
    }

    override fun avertirObservateurs() {
        for (o in observateurs) {
            o.actualiser(temperature)
        }
    }

}
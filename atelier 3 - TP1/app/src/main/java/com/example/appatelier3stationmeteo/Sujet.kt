package com.example.appatelier3stationmeteo

interface Sujet {
    fun ajouterObservateur(o: Observateur)
    fun retirerObservateur(o: Observateur)
    fun avertirObservateurs()
}
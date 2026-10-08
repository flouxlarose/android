package com.example.appatelier3stationmeteo


import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment

class AfficheurFragment : Fragment(R.layout.fragment_afficheur), Observateur {

    private lateinit var texte: TextView

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        texte = view.findViewById(R.id.texteTemperature)
    }

    override fun onStart() {
        super.onStart()

        val station = (requireActivity() as MainActivity).station
        station.ajouterObservateur(this)
        actualiser(station.temperature)
    }

    override fun onStop() {
        super.onStop()
        val station = (requireActivity() as MainActivity).station
        station.retirerObservateur(this)

    }

    override fun actualiser(temperature: Double) {
        texte.text = "$temperature °C"
    }
}
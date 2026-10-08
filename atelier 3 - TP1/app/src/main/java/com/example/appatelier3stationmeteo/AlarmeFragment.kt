package com.example.appatelier3stationmeteo

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment

class AlarmeFragment : Fragment(R.layout.fragment_alarme), Observateur {

    private lateinit var texte: TextView

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        texte = view.findViewById(R.id.texteAlarme)
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
        // on peut faire ca !!
        texte.text = if (temperature > 30)
            "ALARME : chaleur excessive !"
        else
            "Température normale"
    }
}

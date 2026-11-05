package com.decinfo.tp1

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon
import org.json.JSONArray

object Modele {
    var ListeChanson: ListeChanson = ListeChanson()
    val array = ArrayList<HashMap<String, Any>>()

    fun chercherListeChansons(c: Context, ob: Observateur, url: String){
        val queue = Volley.newRequestQueue(c)
        val url = url

        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            { response -> decomposeResponse(response, ob)},
            { error -> println(error)}
        )
        queue.add(stringRequest)
    }
    fun decomposeResponse(response:String, ob: Observateur) {
        // Klaxon remplit l'objet ListeChanson à partir du JSON
        ListeChanson = Klaxon().parse<ListeChanson>(response) ?: ListeChanson()
        array.clear()


        for (i in ListeChanson.results.drop(1)) {   // ignore le premier entrant (pcq c'est un album et non une chanson)
            val temp = HashMap<String, Any>()
            temp.put("nomChanson", i.trackName)
            temp.put("artiste", i.artistName)
            temp.put("album", i.collectionName)
            temp.put("image", i.artworkUrl100)
            temp.put("previewUrl", i.previewUrl)

            array.add(temp)
        }
        ob.remplirListe()
    }

}
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

    fun chercherListeChansons(c: Context, id: Int, ob: Observateur){
        val queue = Volley.newRequestQueue(c)
        val url = "https://itunes.apple.com/lookup?id=6806780462&entity=song&country=fr"

        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            { response -> decomposeResponse(response, ob)},
            { error -> println(error)}
        )
        queue.add(stringRequest)
    }
    fun decomposeResponse(response:String, ob: Observateur) {
        ListeChanson = Klaxon().parse<ListeChanson>(response) ?: ListeChanson()

        for (i in ListeChanson.chansons) {
            val temp = HashMap<String, Any>()
            temp.put("trackName", i.nom)
            temp.put("artistName", i.artiste)
            temp.put("collectionName", i.album)
            temp.put("artworkUrl100", i.artworkURL100)
            temp.put("previewUrl", i.previewURL)

            array.add(temp)
        }
        ob.listePrete()
    }

}
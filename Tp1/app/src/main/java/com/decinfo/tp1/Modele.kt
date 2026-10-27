package com.decinfo.tp1

import com.android.volley.toolbox.Volley
import org.json.JSONArray

class Modele {

    fun chercherJSON(){
        val queue = Volley.newRequestQueue(this)
        val url = "https://www.ericlabonte.com/articles.json"
    }


    fun decomposerReponse(tab: JSONArray){
        val remplir = ArrayList<HashMap<String, Any>>()


    }

}
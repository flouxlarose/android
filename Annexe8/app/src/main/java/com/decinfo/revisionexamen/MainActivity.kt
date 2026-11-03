package com.decinfo.revisionexamen

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray

class MainActivity : AppCompatActivity() {
    var compteur = 0
    var liste:List<Jeu> = emptyList()
    lateinit var champReponse: TextView

    lateinit var champReponse2: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        champReponse = findViewById(R.id.champReponse)

        // requete Volley sans klaxon
        val queue = Volley.newRequestQueue(this)
        val url = "https://www.ericlabonte.com/jeux_video.json"

        val jsonarrayrequest = JsonArrayRequest(Request.Method.GET,
            url,
            null,
            {response: JSONArray ->
                val tab = response
                for (i in 0 until tab.length())
                {
                    if(tab.getJSONObject(i).get("console") == "PlayStation 5")
                        compteur++
                }
                champReponse.text = compteur.toString()
            },
            {error: VolleyError -> println("probleme")})


        val stringRequest = StringRequest(Request.Method.GET,
            url,
            {response:String ->
                liste = Klaxon().parseArray<Jeu>(response) ?: emptyList()
                var total = 0.0
                for (jeu in liste){
                    total += jeu.prix
                }
                var moy = total / liste.size
                champReponse2.text = moy.toString()
            },
            {error: VolleyError -> println("probleme")})


        queue.add(jsonarrayrequest)
        queue.add(stringRequest)
    }
}
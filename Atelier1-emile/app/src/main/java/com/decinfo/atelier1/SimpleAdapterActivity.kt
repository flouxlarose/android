package com.decinfo.atelier1

import android.os.Bundle
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray

class SimpleAdapterActivity : AppCompatActivity() {
    lateinit var liste : ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_simple_adapter)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        liste = findViewById(R.id.liste)
        val queue = Volley.newRequestQueue(this)
        val url = "https://www.ericlabonte.com/articles.json"

        val jsonRequest = JsonObjectRequest(Request.Method.GET,
                                                        url,
                                            null,
                                            {response -> val tab = response.getJSONArray("articles")
                                                decomposerReponse(tab)},
                                            {Toast.makeText(this, "ca marche pas", LENGTH_LONG).show()})

        queue.add(jsonRequest)

        liste.setOnItemClickListener{ _,view,_, _ -> val clic = view as ConstraintLayout
                                                    val temp : TextView = clic.findViewById(R.id.textePrix)
                                                    Toast.makeText(this, temp.getText().toString(), LENGTH_LONG).show()
        }

    }


    fun decomposerReponse (tab : JSONArray) {
        val remplir = ArrayList<HashMap<String, Any>>()

        //faire le tour des éléments du JSONArray pour créer et remplir une HashMap à la fois
        for ( i in 0..tab.length() - 1 ) {
            val temp = HashMap<String, Any>()
            temp.put("nom", tab.getJSONObject(i).getString("nom"))
            temp.put("prix", tab.getJSONObject(i).getDouble("prix"))
            remplir.add(temp)
        }

        val adapt = SimpleAdapter(this,
            remplir,
            R.layout.item,
            arrayOf("nom", "prix"),
            intArrayOf(R.id.texteNom, R.id.textePrix)
        )
        liste.adapter = adapt
    }
}
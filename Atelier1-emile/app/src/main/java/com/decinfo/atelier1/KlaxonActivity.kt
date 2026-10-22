package com.decinfo.atelier1

import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon

class KlaxonActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_klaxon)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val queue = Volley.newRequestQueue(this)
        val url = "https://www.ericlabonte.com/articles.json"
        val stringRequest = StringRequest(Request.Method.GET,
            url,
            {reponse -> val li: ListeProduits = Klaxon().parse<ListeProduits>(reponse) ?: ListeProduits() },
            {Toast.makeText(this@KlaxonActivity, "ne fonctionne pas", LENGTH_LONG)} )

        queue.add(stringRequest)
    }
}
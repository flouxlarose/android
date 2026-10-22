package com.decinfo.tp1

import android.os.Bundle
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class liste_musique : AppCompatActivity() {
    lateinit var liste: ListView

    val v = ArrayList<HashMap<String, Any>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_liste_musique)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        liste = findViewById(R.id.liste)

        val adapteur = AdapterCustom(this, remplirArrayList(), R.layout.item,
            arrayOf("position", "nom", "date", "image"), intArrayOf(R.id.position, R.id.nom, R.id.date, R.id.image))
        liste.adapter = adapteur

        // toast pour tester si fonctionne
        liste.setOnItemClickListener { _, _, position, _ -> Toast.makeText(this, v[position]["nom"].toString(), LENGTH_LONG).show() }
    }

    fun remplirArrayList(): ArrayList<HashMap<String, Any>>{
        var h = HashMap<String, Any>()
        h["position"] = 8
        h["nom"] = "Touch Me"
        h["date"] = "22/03/86"
        h["image"] = "https://www.cvm.qc.ca/wp-content/uploads/mycelium_EVENEMENT.jpg"
        v.add(h)

        return v;
    }
}
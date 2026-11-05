package com.decinfo.tp1

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class liste_musique : AppCompatActivity(), Observateur {
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


        Modele.chercherListeChansons(this, this, intent.getStringExtra("lienMusique").toString())
    }

    override fun remplirListe(){
        val adapteur = AdapterCustom(
            this,
            Modele.array,
            R.layout.item,
            arrayOf("nomChanson", "album", "artiste", "image"),
            intArrayOf(R.id.nomChanson, R.id.album, R.id.artiste, R.id.image))
        liste.adapter = adapteur

        liste.setOnItemClickListener { _, _, position, _ ->
            val i = Intent(this@liste_musique, MusicPlayer::class.java)
            i.putExtra("position", position)
            startActivity(i)
        }
    }
}
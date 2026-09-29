package com.lyne.annexe3

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.lyne.annexe1.R
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.InputStreamReader

class AfficherActivity : AppCompatActivity() {
    lateinit var liste: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_afficher)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        liste = findViewById(R.id.listeMemo)

        liste.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, trierMemos())
    }

    fun trierMemos(): ArrayList<String>
    {
        val trier = ArrayList<String>()
        //val listeActuelle = ArrayList<Memo>()
        var listeActuelle: ArrayList<Memo>? = null //type soit arraylist ou null

        try {
            listeActuelle = SingletonSimple.getListe()
            listeActuelle.sortBy { it.echeance }

            listeActuelle.forEach { it -> trier.add(it.memo) }

            for(it in listeActuelle){
                trier.add(it.memo)
            }
        }catch (f : FileNotFoundException)
        {
            Toast.makeText(this, "pas de fichier", LENGTH_LONG).show()
            finish()

        }

        return trier
    }
}

package com.decinfo.annexe_1

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivity3 : AppCompatActivity() {
    lateinit var liste: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main3)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        liste = findViewById(R.id.liste)
        liste.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, lireMemo())
    }

    fun lireMemo() : ArrayList<String> {
        val fis = openFileInput("fichier.txt")
        val isr = InputStreamReader(fis)
        val br = BufferedReader(isr)

        val a = ArrayList<String>()
        // fonction de haut niveau, un seul param qui est une lambda donc pas besoin de ()
        br.use{
            // a = br.readLines() as ArrayList<String>
            br.forEachLine { ligne -> a.add(ligne) }    // ligne represente un objet temporaire
        }
        return a
    }
}
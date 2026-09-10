package com.lyne.annexe1

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.BufferedWriter
import java.io.OutputStreamWriter

class AjouterActivity : AppCompatActivity() {
    lateinit var champMemo: EditText
    lateinit var boutonAjouterMemo : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ajouter)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        boutonAjouterMemo = findViewById(R.id.onAdd)
        champMemo = findViewById(R.id.addText)

        boutonAjouterMemo.setOnClickListener{
            //lorsqu'on clique sur le bouton
            var text = champMemo.text.toString()

            //MODE_APPEND pour qu'il écrive à la fin du fichier
            val fos = openFileOutput("fichier.txt", MODE_APPEND) //écrire à la fin du fichier
            val osw = OutputStreamWriter(fos) //transforme le flux binaire en flux de caractères
            val bw = BufferedWriter(osw)

            bw.use {
                //use exécute le bloc et ferme les flux de données (les ressources) remplace le close
                //fonction de haut niveau prend un paramètre lambda
                bw.write(text)
                bw.newLine()
                finish()
            }
        }
    }
}
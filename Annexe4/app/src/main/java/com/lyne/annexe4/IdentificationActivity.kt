package com.lyne.annexe4

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class IdentificationActivity : AppCompatActivity() {

    lateinit var prenomChar: EditText
    lateinit var nomChar: EditText
    lateinit var onConnexion: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_inscription)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        prenomChar = findViewById(R.id.prenom)
        nomChar = findViewById(R.id.nom)
        onConnexion = findViewById(R.id.buttonConfirmer)

        onConnexion.setOnClickListener {
            val retour = Intent() //intent de retour il sait où aller en raison du boomerang
            var u = Utilisateur(prenomChar.text.toString(), nomChar.text.toString())
            retour.putExtra("util", u)
            setResult(RESULT_OK, retour)
            finish()
        }
    }
}
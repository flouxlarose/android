package com.lyne.annexe1

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    //nullabilité : on ne peut plus faire de nullpointerexception
    //donc une variable ne peut être nulle
    //var valeur modifiable, val valeur immuable (constante)

    lateinit var onAjouter: Button
    lateinit var onAfficher: Button
    lateinit var onQuit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        onAjouter = findViewById(R.id.bouton_ajouter)
        onAfficher = findViewById(R.id.bouton_afficher)
        onQuit = findViewById(R.id.bouton_quitter)

        //étape 1 : créer une instance de la classe ecouteur
        val ec = Ecouteur()

        //étape 2 : ratacher les boutons à l'écouteur
        onAfficher.setOnClickListener(ec)
        onAjouter.setOnClickListener(ec)
        //onQuit.setOnClickListener(ec)

        //autre facon pour gérer sans l'écouteur et sa classe interne

        //interface fonctionnelle : interface qui n'a qu'une méthode
        //une interface fonctionnelle peut être remplacée par une expression lambda (arrow function)
        //expression dont la syntaxe est simplifiée (expression lambda)

        //onQuit.setOnCLickListener{ v -> finish()}
        //onQuit.setOnCLickListener {finish()} car le paramètre de la méthode n'est pas utilisé
        //pourquoi il n'y a plus de () ? à cause de la règle du "lambda tailing"
        onQuit.setOnClickListener { finish() }

    }
    //3e étape
    //inner pour créer une classe interne
    // : pour implements
    inner class Ecouteur : View.OnClickListener
    {
        //View? : source soit de type View soit nul
        override fun onClick(v: View?) {
            if(v == onAjouter){
                val i = Intent(this@MainActivity, AjouterActivity::class.java)
                startActivity(i)

            }else if( v == onAfficher){
                val i = Intent(this@MainActivity, AfficherActivity::class.java)
                startActivity(i)
            }else{
                finish()
            }
        }
    }
}
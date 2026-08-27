package com.decinfo.annexe_1

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    lateinit var boutonAjouter: Button
    lateinit var boutonAfficher: Button
    lateinit var boutonQuitter: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        boutonAfficher = findViewById(R.id.buttonAfficher)
        boutonQuitter = findViewById(R.id.buttonQuitter)
        boutonAjouter = findViewById(R.id.buttonAjouter)

        val ec = Ecouteur()

        boutonAfficher.setOnClickListener(ec)
        // boutonQuitter.setOnClickListener(ec)
        boutonAjouter.setOnClickListener(ec)


        // autre façon
        // interface fonctionnelle : interface qui n'a qu'une méthode
        // une interface fonctionelle peut être remplacer par une expression lambda
        // boutonQuitter.setOnClickListener { v -> finish() }
        boutonQuitter.setOnClickListener { finish() }
        // Pourquoi il n'y a plu de parenthèses ? à cause de la règle du "lambda trailling"



    }
    inner class Ecouteur : View.OnClickListener
    {
        override fun onClick(v: View?) {
            if( v == boutonQuitter)
                finish()
            else if ( v == boutonAjouter)
            {
                val i = Intent(this@MainActivity, MainActivity2::class.java)
                startActivity(i)
            }
            else {
                val i = Intent(this@MainActivity, MainActivity3::class.java)
                startActivity(i)
            }
        }
    }
}
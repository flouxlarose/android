package com.decinfo.tp1

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var categorie1: Button
    lateinit var categorie2: Button
    lateinit var categorie3: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        categorie1 = findViewById(R.id.categorie1)
        categorie2 = findViewById(R.id.categorie2)
        categorie3 = findViewById(R.id.categorie3)

        val ec = Ecouteur()
        categorie1.setOnClickListener(ec)
        categorie2.setOnClickListener(ec)
        categorie3.setOnClickListener(ec)
    }

    inner class Ecouteur : View.OnClickListener
    {
        //View? : source soit de type View soit nul
        override fun onClick(v: View?) {
            if(v == categorie1) {
                val i = Intent(this@MainActivity, liste_musique::class.java)
                startActivity(i)
            }
//            else if( v == categorie2){
//                val i = Intent(this@MainActivity, liste_musique::class.java)
//                startActivity(i)
//            }else if( v == categorie3){
//                val i = Intent(this@MainActivity, liste_musique::class.java)
//                startActivity(i)
//            }
            else{
                finish()
            }
        }
    }
}
package com.decinfo.annexe_1b

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

//         selon gemini:
//        fun calculerNbLignes1(): Int {
//            return openFileInput("exercice.txt").bufferedReader().useLines { lines ->
//                lines.count()
//            }
//        }

        fun calculerNbLignes() : Int {
            val fis = openFileInput("exercice.txt")
            val isr = InputStreamReader(fis)
            val br = BufferedReader(isr)
            var compteur = 0

            br.use {
                var ligne = br.readLine()
                while (ligne != null){
                    compteur ++
                    ligne = br.readLine()
                }
            }
            return compteur
        }

        fun calculerNbChar() : Int {
            val fis = openFileInput("exercice.txt")
            val isr = InputStreamReader(fis)
            val br = BufferedReader(isr)
            var compteur = 0

            br.use {
                var ligne = br.readLine()
                while (ligne != null) {
                    compteur += ligne.length
                    ligne = br.readLine()
                }
            }
            return compteur
        }
    }

    fun nbCharC() : Int {
        val fis = openFileInput("exercice.txt")
        val isr = InputStreamReader(fis)
        val br = BufferedReader(isr)
        var compteur = 0

        br.use {
            var ligne = br.readLine()
            while (ligne != null){
                for (i in 0 until ligne.length){
                    if(ligne[i] == 'c')
                        compteur ++
                }
            }
        }
        return compteur
    }
}
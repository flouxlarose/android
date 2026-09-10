package com.lyne.annexe1c

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.Scanner
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    lateinit var nbreLignes: TextView
    lateinit var nbreChar : TextView
    lateinit var nbreDeC : TextView
    lateinit var nbreDeMots : TextView
    lateinit var nom : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//        nbreLignes = findViewById(R.id.nombreLigne)
//        nbreChar = findViewById(R.id.nbrCaract)
//        nbreDeC = findViewById(R.id.nbreDeC)
//        nbreDeMots = findViewById(R.id.nbreDeMots)
//        nom = findViewById(R.id.editScan)

        nbreLignes.text = "Nombre de lignes : " + calculerNbLignes().toString()
        nbreChar.text = "Nombre de caractères : " + calculerNbreChar().toString()
        nbreDeC.text = "Nombre de 'c' : " + calculerNombreDeC().toString()
        nbreDeMots.text = "Nombre de mots : " + calculerNombreMots().toString()
        ecrireLigne(nom.text.toString())
    }
    fun calculerNbLignes() : Int{
        val a = ArrayList<String>()

        try {
            val fis = openFileInput("fichier.txt")
            val isr = InputStreamReader(fis)
            val br = BufferedReader(isr)

            br.forEachLine { ligne -> a.add(ligne) }

        }catch(e : FileNotFoundException)
        {
            val toast = Toast.makeText(this, "il n'y a pas de mémo", Toast.LENGTH_LONG)
            toast.show()
            finish()
        }
        return a.size
    }

    //autre facon
    fun nbLignes(): Int{
        val fis = openFileInput("fichier.txt")
        val isr = InputStreamReader(fis)
        val br = BufferedReader(isr)
        var compteur = 0;

        br.use{
            for (line in br.lines())
                compteur++

            //compteur = br.readLines().size
        }
        return compteur
    }

    fun calculerNbreChar(): Int{
        val fis = openFileInput("fichier.txt")
        val isr = InputStreamReader(fis)
        val br = BufferedReader(isr)
        var compteurChar =0

        br.forEachLine {compteurChar += it.length}

        return compteurChar
    }

    fun calculerNombreDeC(): Int{
        val fis = openFileInput("fichier.txt")
        val isr = InputStreamReader(fis)
        val br = BufferedReader(isr)
        var compteurC = 0

        br.forEachLine {
                line -> for (lettre in line){
            if(lettre == 'c' || lettre == 'C'){
                compteurC++
            }
        }
        }
        return compteurC
    }

    fun ecrireLigne( ligne:String)
    {
        val fis : FileOutputStream = openFileOutput("fichier.txt", MODE_APPEND)
        val isr = OutputStreamWriter(fis)
        val br = BufferedWriter(isr)
        br.use {
            br.write(ligne)
            br.newLine()

            val toast = Toast.makeText(this,"Mot ajouté", Toast.LENGTH_LONG)
            toast.show()
        }
    }

    fun calculerNombreMots() : Int{
        var compteur = 0
        val fis = openFileInput("fichier.txt")
        val scan = Scanner(fis)
        //on utilis le delimitateur par défaut
        scan.use {
            while(scan.hasNext()){
                scan.next()
                compteur++
            }
        }

        return compteur
    }

    fun annexe1C():Int{
        var total: Int = 0
        val fis = getResources().openRawResource(R.raw.fichierPlanete)
        val s = Scanner(fis)
        val v = ArrayList<Planete>()

        while(s.hasNext()){
            var temp = Planete(s.next(), s.nextInt())
            v.add(temp)
            total += temp.nbSatellites
        }
        log.i("test", v.elementAt(3).nom)

        return v.size
    }
}
package com.decinfo.examen2

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon
import kotlin.text.get

class MainActivity : AppCompatActivity() {
    lateinit var texteTitre: TextView
    lateinit var liste: ListView
    lateinit var boutonVerifier: Button
    lateinit var ecListe: EcouteurListe
    lateinit var ec: Ecouteur
    //    val array = ArrayList<HashMap<String, Any>>()
//    var ListeQuestion: ListeQuestion = ListeQuestion()
    var nombreQuestion: Int = 1

    var question1 = "Quel type de requete Volley doit être utilisée si on veut utiliser la librairie klaxon ?"
    var question2 = "A quoi sert le patron de conception Observer"
    var arrayQuestions = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        texteTitre = findViewById(R.id.texteTitre)
        liste = findViewById(R.id.liste)
        boutonVerifier = findViewById(R.id.boutonVerifier)
        boutonVerifier.isEnabled = false

        chargerQuestions()
        ecListe = EcouteurListe()
        liste.setOnItemClickListener(ecListe)

        ec = Ecouteur()
        boutonVerifier.setOnClickListener(ec)
    }


//        // aller chercher la liste
//        val queue = Volley.newRequestQueue(this)
//        val url = "https://ericlabonte.com/question_kotlin.json"
//
//        val stringRequest = StringRequest(
//            Request.Method.GET,
//            url,
//            { response ->
//                decomposerReponse(response)
//            },
//            { error -> println(error) }
//        )
//        queue.add(stringRequest)
//
//        texteTitre.text = array.get(0).get("libelle").toString()
//    }

//    fun remplirListe() {
//        val arrayTemp: ArrayList<String> = array.get(1).get("choix_reponses") as ArrayList<String>
//        val adapteur = ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayTemp)
//        liste.adapter = adapteur
//    }

//    fun decomposerReponse(response: String) {
//        ListeQuestion = Klaxon().parse<ListeQuestion>(response) ?: ListeQuestion
//        array.clear()
//
//        for (i in ListeQuestion.questions) {
//            val temp = HashMap<String, Any>()
//            temp.put("libelle", i.libelle)
//            val arrayTemp = ArrayList<String>()
//            arrayTemp.add(i.choix_reponses.get("A").toString())
//            arrayTemp.add(i.choix_reponses.get("B").toString())
//            arrayTemp.add(i.choix_reponses.get("C").toString())
//            temp.put("choix_reponses", arrayTemp)
//
//            array.add(temp)
//        }
//        remplirListe()
//    }
    fun chargerQuestions(){
        boutonVerifier.text = "Button"
        boutonVerifier.isEnabled =false

        if(nombreQuestion == 1){
            texteTitre.text = question1
        }
        else if(nombreQuestion == 2){
            texteTitre.text = question2
        }
        arrayQuestions.clear()
        if (nombreQuestion == 1) {
            arrayQuestions.add("JsonArrayRequest")
            arrayQuestions.add("JsonObjectRequest")
            arrayQuestions.add("StringRequest")
        }
        else if (nombreQuestion == 2){
            arrayQuestions.add("avertir les observateurs qu'une variable d'instance du sujet a ete modifiee")
            arrayQuestions.add("avertir les observateurs qu'une variable d'instance du sujet a ete accedee")
            arrayQuestions.add("avertir le sujet qu'une variable de l'observateur a ete detruite")
        }
        val adapteur = ArrayAdapter(this,
            android.R.layout.simple_list_item_1,
            arrayQuestions)
        liste.adapter = adapteur
    }
    inner class Ecouteur: View.OnClickListener {
        override fun onClick(v: View?) {
            if (v == boutonVerifier) {
                chargerQuestions()
            }
        }

    }
    inner class EcouteurListe: AdapterView.OnItemClickListener{
        override fun onItemClick(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            if (position == 2 && nombreQuestion == 1){   // si la reponse clické est StringRequest à la premiere question
                boutonVerifier.isEnabled = true
                boutonVerifier.text = "Bonne reponse! Question Suivante"
                nombreQuestion ++
            }
            else if(position == 0 && nombreQuestion == 2){
                boutonVerifier.isEnabled = true
                boutonVerifier.text = "Bonne reponse! Vous Avez Terminé !"
            }
        }
    }
}


package com.decinfo.annexe5

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    lateinit var liste: ListView

//    lateinit var position: TextView
//    lateinit var nom: TextView
//    lateinit var date: TextView
//    lateinit var image: ImageView
    val v = ArrayList<HashMap<String, Any>>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        liste = findViewById(R.id.liste)

        val adapteur = SimpleAdapter(this, remplirArrayList(), R.layout.item,
            arrayOf("position", "nom", "date", "image"), intArrayOf(R.id.position, R.id.nom, R.id.date, R.id.image))

        liste.adapter = adapteur

        liste.setOnItemClickListener { _, _, position, _ -> Toast.makeText(this, v.get(position).get("nom").toString(), LENGTH_LONG).show() }
    }

    fun remplirArrayList() : ArrayList<HashMap<String, Any>>{
        var h = HashMap<String, Any>()
        h["position"] = 8
        h["nom"] = "Touch Me"
        h["date"] = "22/03/86"
        h["image"] = R.drawable.touchme
        v.add(h)

        h = HashMap()
        h["position"] = 8
        h["nom"] = "Nothing's gonna stop me now"
        h["date"] = "30/05/86"
        h["image"] = R.drawable.nothing
        v.add(h)

        h = HashMap()
        h["position"] = 31
        h["nom"] = "Santa Maria"
        h["date"] = "28/03/1998"
        h["image"] = R.drawable.santamaria
        v.add(h)

        h = HashMap()
        h["position"] = 108
        h["nom"] = "Hot Boy"
        h["date"] = "10/04/2018"
        h["image"] = R.drawable.hotboy
        v.add(h)

        return v
    }
}
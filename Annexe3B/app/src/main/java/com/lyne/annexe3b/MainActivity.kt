package com.lyne.annexe3b

import android.content.Context
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.SeekBar
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.makeText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class MainActivity : AppCompatActivity() {
    lateinit var sonnerieProgress : SeekBar
    lateinit var mediaProgress : SeekBar
    lateinit var notifProgress : SeekBar

    var volume: Volume? = null //type volume ou null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        sonnerieProgress = findViewById(R.id.sonnerieBar)
        mediaProgress = findViewById(R.id.mediaBar)
        notifProgress = findViewById(R.id.notifBar)

        recupererVolumes()

    }
    fun recupererVolumes(){
        try {
            val fis = openFileInput("fichier.ser")
            val ois = ObjectInputStream(fis)
            ois.use{
                volume = ois.readObject() as Volume
                sonnerieProgress.progress = volume!!.sonnerie
                mediaProgress.progress = volume!!.media
                notifProgress.progress = volume!!.notif
            }
        } catch (e: FileNotFoundException) {
            makeText(this, "1ère utilisation", LENGTH_LONG).show()
        }
    }

    override fun onStop() {
        super.onStop()

        //serialise
        try{
            //mode private pour ajouter au début et écraser les anciennes valeurs
            val fos = openFileOutput("fichier.ser", MODE_PRIVATE)
            val oos = ObjectOutputStream(fos)
            oos.use {
                //use fonction de haut niveau,
                //writeObject peut contenir plusiers objets/données
                oos.writeObject(Volume(sonnerieProgress.progress, mediaProgress.progress, notifProgress.progress))
            }
        }
        catch(e: IOException){
            e.printStackTrace()
        }
    }

}
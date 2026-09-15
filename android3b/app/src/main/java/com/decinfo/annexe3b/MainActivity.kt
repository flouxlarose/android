package com.decinfo.annexe3b

import android.os.Bundle
import android.widget.SeekBar
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.FileNotFoundException
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class MainActivity : AppCompatActivity() {
    lateinit var seek1Sonnerie: SeekBar
    lateinit var seek2Media: SeekBar
    lateinit var seek3Notif: SeekBar

    var volume: Volumme? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        seek1Sonnerie = findViewById(R.id.seekBar1)
        seek2Media = findViewById(R.id.seekBar2)
        seek3Notif = findViewById(R.id.seekBar3)

        seek1Sonnerie.progress = 45
        seek2Media.progress = 60
        seek3Notif.progress = 25

        // deserialiser
        recupererVolummes()
    }

    fun recupererVolummes(){
        try {
            val fos = openFileInput("serialisation.ser")
            val oos = ObjectInputStream(fos)
            oos.use{
                volume = oos.readObject() as Volumme
                seek1Sonnerie.progress = volume!!.sonnerie
                seek2Media.progress = volume!!.media
                seek3Notif.progress = volume!!.notif
            }
        } catch (e: FileNotFoundException) {
            Toast.makeText(this, "1ere utilisation", LENGTH_LONG).show()
        }
    }

    // override onStop faire ctrl - o
    override fun onStop() {
        super.onStop()

        try {
            val fos = openFileOutput("serialisation.ser", MODE_PRIVATE)
            val oos = ObjectOutputStream(fos)
            oos.use {
                oos.writeObject(
                    Volumme(
                        seek1Sonnerie.progress,
                        seek2Media.progress,
                        seek3Notif.progress
                    )
                )
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
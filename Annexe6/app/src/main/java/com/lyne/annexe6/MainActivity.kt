package com.lyne.annexe6

import android.content.Intent
import android.content.Intent.ACTION_OPEN_DOCUMENT
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var image: ImageView
    lateinit var bouton: Button

    //étape1: créer un lanceur
    var lanceur: ActivityResultLauncher<Intent>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        image = findViewById(R.id.imageView)
        bouton = findViewById(R.id.button)

        //étape 2 : initialiser le  lanceur de boomerang
        lanceur = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            CallBackImage()
        )
        bouton.setOnClickListener {
            val intent = Intent(ACTION_OPEN_DOCUMENT)
            intent.setType("image/*")
            //dans le cas où l'initialisation du launcher a pas marché le ? est plus prudent
            lanceur?.launch(intent)

        }
    }

    inner class CallBackImage : ActivityResultCallback<ActivityResult>{
        //c'est ici que le boomerang reviendra
        override fun onActivityResult(result: ActivityResult){
            var intent = result.data    //il permet de retourner l'intent
            var uri = intent!!.data     //Uri !! on assume que notre intent n'est pas null
            image.setImageURI(uri)
        }
    }
}
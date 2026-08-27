package com.decinfo.annexe_1

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.BufferedWriter
import java.io.OutputStreamWriter

class MainActivity2 : AppCompatActivity() {

    lateinit var boutonAjouterMemo : Button
    lateinit var champMemo : EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        boutonAjouterMemo = findViewById(R.id.boutonAjouterMemo)
        champMemo = findViewById(R.id.textMemo)


        boutonAjouterMemo.setOnClickListener {
            var texte = champMemo.text.toString()

            val fos = openFileOutput("fichier.txt", MODE_APPEND) //pour qu'il écrive à la fin du fichier
            val osw = OutputStreamWriter(fos)
            val bw = BufferedWriter(osw)

            bw.use{
                bw.write(texte)
                bw.newLine()
                finish()
            }
        }
    }
}
package com.lyne.annexe3

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class AjouterActivity : AppCompatActivity() {
    lateinit var champMemo: EditText
    lateinit var boutonAjouterMemo : Button
    lateinit var boutonEcheance : Button
    lateinit var date : TextView
    var dateChoisie =  LocalDate.now().plusDays(1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ajouter)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        boutonAjouterMemo = findViewById(R.id.onAdd)
        champMemo = findViewById(R.id.addText)
        boutonEcheance = findViewById(R.id.echeance)
        date = findViewById(R.id.date)

        boutonAjouterMemo.setOnClickListener{
            //lorsqu'on clique sur le bouton
            var text = champMemo.text.toString()
            SingletonSimple.addMemo(memoAjouter = Memo(text, dateChoisie))
            finish()
        }
        boutonEcheance.setOnClickListener {
            //INTERFACE FONCTIONNELLE Onclicklistener
            val d = DatePickerDialog(this)

            d.setOnDateSetListener { d, annee, mois, jour ->
                dateChoisie = LocalDate.of(annee, mois+1, jour)
                val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
                val text: String? = dateChoisie.format(formatter)

                date.text = text
            }
            d.show()
        }
    }
}
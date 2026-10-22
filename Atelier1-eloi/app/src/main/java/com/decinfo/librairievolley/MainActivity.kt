package com.decinfo.librairievolley

import android.os.Bundle
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.Response.Listener
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class MainActivity : AppCompatActivity() {
    lateinit var liste: ListView
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

        val queue = Volley.newRequestQueue(this)
        val url = "https://www.ericlabonte.com/articles.json"

        val stringRequest = StringRequest(Request.Method.GET, url, Repondeur(), RepondeurErreurs())

        val stringRequest2 = StringRequest(Request.Method.GET, url,
                                {response -> Toast.makeText(this@MainActivity, response.toString(), LENGTH_LONG).show()},
                            {Toast.makeText(this@MainActivity, "ne fonctionne pas", LENGTH_LONG).show()})






        queue.add(stringRequest2)
        liste.setOnItemClickListener{_,view,_,_ -> val clic = view as ConstraintLayout
                                                val temp: TextView = clic.findViewById(R.id.textePrix)
                                                Toast.makeText(this, temp.text.toString(), LENGTH_LONG).show()
                                                }
    }

    inner class Repondeur: Response.Listener<String> {
        override fun onResponse(response: String?) {
            Toast.makeText(this@MainActivity, response.toString(), LENGTH_LONG).show()
        }
    }

    inner class RepondeurErreurs: Response.ErrorListener{
        override fun onErrorResponse(p0: VolleyError?) {
            Toast.makeText(this@MainActivity, "ne fonctionne pas", LENGTH_LONG).show()
        }
    }
}
package com.decinfo.atelier1

import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

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

        val queue = Volley.newRequestQueue(this)
        val url = "https://www.ericlabonte.com/articles.json"

        val stringRequest = StringRequest(Request.Method.GET, url, Repondeur(), RepondeurErreurs())

        queue.add(stringRequest)
    }

    inner class Repondeur : Response.Listener<String> {
        override fun onResponse(reponse: String?) {
            Toast.makeText(this@MainActivity, reponse.toString(), LENGTH_LONG).show()
        }
    }

    inner class RepondeurErreurs : Response.ErrorListener {
        override fun onErrorResponse(p0: VolleyError?) {
            Toast.makeText(this@MainActivity, "ne fonctionne pas", LENGTH_LONG).show()
        }
    }
}
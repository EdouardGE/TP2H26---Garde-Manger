package com.example.a25_demo03_cycles

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(), TextWatcher {

    // Constantes pour les clés de sauvegarde
    private val STATE = "increment"
    private val BTLOGIN = "btlogin"
    private val TAG = "cours3"

    // variables membres
    private var increment = 0
    private lateinit var et_edition: EditText
    private lateinit var btFin: Button
    private lateinit var btLogin: Button
    private lateinit var tvCopy: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // En cas de redémarrage sans passer par onDestroy, savedInstanceState n'est pas à null
        if (savedInstanceState != null) {
            increment = savedInstanceState.getInt(STATE)
        }
        else
            increment = 0

        et_edition = findViewById(R.id.et_edition)
        btFin = findViewById(R.id.bt_fin)
        btFin.setOnClickListener(View.OnClickListener { finish() })
        btLogin = findViewById(R.id.bt_login)

        btLogin.setOnClickListener(View.OnClickListener { btLogin.setText("Logout") })

        tvCopy = findViewById(R.id.tv_copy)

        et_edition.addTextChangedListener(this)

        Log.i(TAG, "onCreate $increment")

        // Affichage dans un toast de la valeur de increment
        Toast.makeText(this, "onCreate $increment", Toast.LENGTH_SHORT).show()
        // Ci-dessous juste pour montrer comment référencer dans le code des valeurs de strings.xml
        Toast.makeText(this, "onCreate " + getString(R.string.Cycle), Toast.LENGTH_LONG).show()
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(TAG, "onRestart $increment")
        Toast.makeText(this, "onRestart $increment", Toast.LENGTH_SHORT).show()
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart $increment")
        Toast.makeText(this, "onStart $increment", Toast.LENGTH_SHORT).show()
    }

    // c'est ici que onRestoreInstantState sera exécuté
    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume $increment")
        Toast.makeText(this, "onResume $increment", Toast.LENGTH_SHORT).show()
    }

    // increment +1 lorsque l'activité se met en pause
    override fun onPause() {
        increment++
        super.onPause()
        Log.i(TAG, "onPause " + increment + if (isFinishing) " Finishing" else "")
        Toast.makeText(this, "onPause $increment", Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop $increment")
        Toast.makeText(this, "onStop $increment", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy" + increment + if (isFinishing) " Finishing" else "")
        Toast.makeText(
            this,
            "onDestroy " + increment + if (isFinishing) " Finishing" else "",
            Toast.LENGTH_SHORT
        ).show()
    }

    // on range la valeur de increment ici car on a accès au Bundle
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save increment
        outState.putInt(STATE, increment)
        // Save texte du bouton login
        outState.putString(BTLOGIN, btLogin.text.toString())

        Log.i(TAG, "onSaveInstanceState à $increment")
        Toast.makeText(this, "onSaveInstanceState ", Toast.LENGTH_SHORT).show()
    }

    // opération inverse
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // Restore increment
        increment = savedInstanceState.getInt(STATE, 0)
        // Replace texte du bouton après une rotation par exemple
        btLogin.text = savedInstanceState.getString(BTLOGIN, "")

        Log.i(TAG, "onRestoreInstanceState à $increment")
        Toast.makeText(this, "onRestoreInstanceState ", Toast.LENGTH_SHORT).show()
    }

    // Champ de saisie -> textView
    // Noter que ces contenus sont gérés par Android
    // Pas besoin de gérer les sauvegardes dans onSaveInstanceState
    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        tvCopy!!.text = s.toString()
    }

    override fun afterTextChanged(s: Editable) {}

}
package com.example.appacademylogin

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appacademylogin.classes.Candidato
import com.example.appacademylogin.csv.Csv
import com.example.appacademylogin.datastructure.testMyArrayList


class MainActivity : AppCompatActivity() {
    private lateinit var textView: TextView
    private lateinit var candidate: TextView
    private lateinit var testButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get the candidate information's from the login screen.
        val message = intent.getStringExtra(CANDIDATE)

        // View's
        candidate = findViewById<TextView>(R.id.candidate).apply {
            text = message
        }
        textView = findViewById(R.id.textView)
        testButton = findViewById(R.id.test_button)

        // Get the candidates list.
        val candidatesList = Csv.getCandidates(this.baseContext)

        // Get iOS instructor.
        val iosInstructor: Candidato? = Csv.getIosInstructor()

        // Get Android instructor.
        val androidInstructor: Candidato? = Csv.getAndroidInstructor(candidatesList, iosInstructor)

        val newCan = Candidato("Alex", 2000, "SP")

        textView.text =
            "${Csv.getPercentage(candidatesList)}\n ${Csv.getUniqueStates(candidatesList)}" +
                    "\n New Candidate = ${newCan.nome} ${newCan.idade} ${newCan.vaga} ${newCan.estado}"

        testButton.setOnClickListener {
            Toast.makeText(this, testMyArrayList(), Toast.LENGTH_LONG).show()
        }
    }

}
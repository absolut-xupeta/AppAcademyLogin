package com.example.appacademylogin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.appacademylogin.classes.Candidato
import com.example.appacademylogin.csv.Csv


class MainActivity : AppCompatActivity() {
    private lateinit var textView: TextView
    private lateinit var candidate: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get the candidate information's from the login screen.
        val message = intent.getStringExtra(CANDIDATE)

        // TextView's
        candidate = findViewById<TextView>(R.id.candidate).apply {
            text = message
        }
        textView = findViewById(R.id.textView)

        // Get the candidates list.
        val candidatesList = Csv.getCandidates(this.baseContext)

        // Get iOS instructor.
        //val iosInstructor: Candidato? = Csv.getIosInstructor(candidates)

        // Get Android instructor.
        //val androidInstructor: Candidato? = Csv.getAndroidInstructor(candidates, iosInstructor)

        val newCan = Candidato("Alex", 2000, "SP")

        textView.text =
            "${Csv.getPercentage(candidatesList)}\n ${Csv.getUniqueStates(candidatesList)}\n New Candidate = ${newCan.nome} ${newCan.idade} ${newCan.vaga} ${newCan.estado}"
    }

}
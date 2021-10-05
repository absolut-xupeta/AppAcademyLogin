package com.example.appacademylogin

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.appacademylogin.classes.Candidate
import com.example.appacademylogin.csv.Csv

class MainActivity : AppCompatActivity() {
    private lateinit var candidate: TextView
    private lateinit var percentages: TextView
    private lateinit var uniqueStates: TextView
    private lateinit var iosInstructor: TextView
    private lateinit var androidInstructor: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val message = intent.getStringExtra(CANDIDATE)

        percentages = findViewById(R.id.percentages)
        uniqueStates = findViewById(R.id.uniqueStates)
        iosInstructor = findViewById(R.id.iosInstructor)
        androidInstructor = findViewById(R.id.androidInstructor)

        candidate = findViewById<TextView>(R.id.candidate).apply {
            text = message
        }

        displayInfo()
    }

    private fun displayInfo() {
        val candidatesList = Csv.getCandidates(this.baseContext)

        val iosIns: Candidate? = Csv.getIosInstructor()

        val androidIns: Candidate? = Csv.getAndroidInstructor(candidatesList, iosIns)

        percentages.text = Csv.getPercentage(candidatesList)
        uniqueStates.text = Csv.getUniqueStates(candidatesList)
        iosInstructor.text = iosIns?.name ?: "iOS instructor not found"
        androidInstructor.text = androidIns?.name ?: "Android instructor not found"
    }
}
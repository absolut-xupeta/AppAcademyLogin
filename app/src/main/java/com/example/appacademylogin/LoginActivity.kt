package com.example.appacademylogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.*
import com.example.appacademylogin.classes.Candidate
import com.example.appacademylogin.csv.Csv

const val CANDIDATE = "CANDIDATE"

class LoginActivity : AppCompatActivity() {
    private lateinit var usernameField: EditText
    private lateinit var passwordField: EditText
    private lateinit var loginButton: LinearLayout
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usernameField = findViewById(R.id.usernameField)
        passwordField = findViewById(R.id.passwordField)
        loginButton = findViewById(R.id.customButton)
        progressBar = findViewById(R.id.loginProgressBar)

        val candidates = Csv.getCandidates(this.baseContext)

        loginButton.setOnClickListener {
            changeButtonState(true)

            val result: Candidate? = validateLogin(
                usernameField.text.toString(),
                passwordField.text.toString(),
                candidates
            )

            delay {
                if (result != null) {
                    Toast.makeText(this, "Logged in successfully", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java).apply {
                        putExtra(
                            CANDIDATE,
                            "${result.name}, ${result.age}, ${result.stack}, ${result.state}"
                        )
                    }

                    startActivity(intent)
                    finish()
                } else {
                    changeButtonState(false)

                    Toast.makeText(
                        this,
                        "Wrong login or password.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun validateLogin(
        username: String,
        password: String,
        candidatesList: ArrayList<Candidate>
    ): Candidate? {

        candidatesList.forEach {
            if (it.username == username && it.password.toString() == password) {
                return it
            }
        }
        return null
    }

    private fun delay(delay: Long = 1500, action: () -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed(action, delay)
    }

    private fun changeButtonState(isLoading: Boolean) {
        if (isLoading) {
            progressBar.visibility = View.VISIBLE
            loginButton.apply {
                isClickable = false
                isFocusable = false
                alpha = 0.5f
            }
        } else {
            progressBar.visibility = View.GONE
            loginButton.apply {
                isClickable = true
                isFocusable = true
                alpha = 1f
            }
        }
    }
}
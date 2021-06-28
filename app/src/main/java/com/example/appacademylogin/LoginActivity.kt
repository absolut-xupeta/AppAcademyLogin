package com.example.appacademylogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.*
import com.example.appacademylogin.classes.Candidato
import com.example.appacademylogin.csv.Csv
import com.example.appacademylogin.datastructure.MyArrayList
import com.example.appacademylogin.utils.forEach

const val CANDIDATE = "CANDIDATE"

class LoginActivity : AppCompatActivity() {
    private lateinit var usernameField: EditText
    private lateinit var passwordField: EditText
    private lateinit var loginButton: LinearLayout
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Transform Views to its respective types.
        usernameField = findViewById(R.id.usernameField)
        passwordField = findViewById(R.id.passwordField)
        loginButton = findViewById(R.id.customButton)
        progressBar = findViewById(R.id.loginProgressBar)

        // Get the candidates list.
        val candidates = Csv.getCandidates(this.baseContext)

        // On button click.
        loginButton.setOnClickListener {

            // Set the login button to not clickable.
            changeButtonState(true)

            // Get the result from the credentials.
            val result: Candidato? = validateLogin(
                usernameField.text.toString(),
                passwordField.text.toString(),
                candidates
            )

            // Go to the next screen if the login credentials are right.
            delay {
                if (result != null) {
                    Toast.makeText(this, "Logged in successfully", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java).apply {
                        putExtra(
                            CANDIDATE,
                            "${result.nome}, ${result.idade}, ${result.vaga}, ${result.estado}"
                        )
                    }

                    // Go to the next screen and terminate the login screen.
                    startActivity(intent)
                    finish()

                } else {
                    // Make button clickable and change color again.
                    changeButtonState(false)

                    // Display failure message.
                    Toast.makeText(
                        this,
                        "Wrong login or password.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    /**
     * Validate the candidate login credentials.
     */
    private fun validateLogin(
        username: String,
        password: String,
        candidatesList: MyArrayList<Candidato>
    ): Candidato? {

        candidatesList.forEach {
            if (it.username == username && it.password.toString() == password) {
                return it
            }
        }
        return null
    }

    /**
     * Delay a function execution.
     */
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
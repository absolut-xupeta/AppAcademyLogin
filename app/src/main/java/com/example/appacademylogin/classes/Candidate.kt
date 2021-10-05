package com.example.appacademylogin.classes

import com.example.appacademylogin.utils.Utils
import java.util.*

/**
 * Class to store the candidates information's.
 */
data class Candidate(
    val name: String,
    val age: Int,
    val stack: String,
    val state: String
) {
    val username = Utils.getLogin(name)
    val password = Calendar.getInstance().get(Calendar.YEAR) - age
}
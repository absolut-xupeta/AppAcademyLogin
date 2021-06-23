package com.example.appacademylogin.csv

import android.content.Context

import com.example.appacademylogin.classes.Candidato
import com.example.appacademylogin.utils.Utils
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * Csv information extractor.
 */
object Csv {
    private var candidatesList: List<Candidato>? = null

    /**
     * Get the candidates information's and add them to a list of 'Candidatos'.
     */
    fun getCandidates(context: Context): List<Candidato> {
        return candidatesList ?: arrayListOf<Candidato>().also { list ->
            val streamReader = InputStreamReader(context.assets.open("AppAcademy_Candidates.csv"))
            val bufferedReader = BufferedReader(streamReader)
            var row: List<String>

            bufferedReader.readLine()

            while (bufferedReader.ready()) {
                row = bufferedReader.readLine().split(';')
                list.add(
                    Candidato(
                        row[0],
                        row[1],
                        row[2].filter { it.isDigit() }.toInt(),
                        row[3]
                    )
                )
            }
            candidatesList = list
        }
    }

    /**
     * Gets the percentage of each job in the candidates list and
     * the average QA age.
     */
    fun getPercentage(candidates: List<Candidato>?): String {
        // Total of each candidate job.
        var totalIos = 0
        var totalAndroid = 0
        var totalQA = 0

        // AverageQA age.
        var averageQA = 0

        for (a in candidates ?: return "List is empty.") {
            when (a.vaga) {
                "qa" -> {
                    val age = a.idade
                    averageQA += age
                    totalQA += 1
                }
                "ios" -> totalIos += 1
                "android" -> totalAndroid += 1
            }
        }

        // Total candidates
        val totalCandidates = candidates.size

        // Average QA candidates age.
        averageQA /= totalQA

        // Percentage of candidates by job and average QA candidates age.
        return "Android: ${Utils.percentage(totalAndroid, totalCandidates)}% \n" +
                "iOS: ${Utils.percentage(totalIos, totalCandidates)}% \n" +
                "QA: ${Utils.percentage(totalQA, totalCandidates)}% \n" +
                "Average QA age: $averageQA"
    }

    /**
     * Gets the number of unique states in the list and the 2 states with
     * the least candidates.
     */
    fun getUniqueStates(candidates: List<Candidato>?): String {
        // Find the unique states inside the candidates list.
        val unique = HashMap<String, Int?>()

        for (a in candidatesList ?: return "List is empty.") {
            if (unique.containsKey(a.estado)) {
                val u: Int? = unique.getValue(a.estado)
                unique[a.estado] = u?.plus(1)
            } else {
                unique[a.estado] = 1
            }
        }
        val uniqueStates = unique.size

        // List the states with less candidates.
        val (least1, least2) =
            unique.toList().sortedBy { (_, value) -> value }.dropLast(uniqueStates - 2)

        val leasStates =
            "\n${least1.first.uppercase()} = ${least1.second} candidato(s)\n${least2.first.uppercase()} = ${least2.second} candidato(s)"

        return "Unique states: $uniqueStates\n" +
                "States with least candidates: " +
                leasStates
    }

    /**
     * Searches for the iOS instructor inside the candidates list.
     */
    fun getIosInstructor(candidates: List<Candidato>?): Candidato? {
        // Find the iOS instructor.
        for (a in candidates ?: return null) {
            if (a.idade in (20..31).toList().filter { it % 2 != 0 && Utils.primeNumber(it) }) {
                if (a.vaga != "ios" && a.nome[Utils.getIndex(a.nome) + 1] == 'V') {
                    return a
                }
            }
        }
        return null
    }

    /**
     * Searches for the Android instructor inside the candidates list.
     */
    fun getAndroidInstructor(list: List<Candidato>?, ios: Candidato?): Candidato? {
        // Find the Android instructor.
        list?.forEach {
            it.apply {
                if (idade < (ios?.idade ?: 0) && idade % 2 != 0) {
                    if (nome[Utils.getIndex(nome) - 1] == 'o' && vaga != "android") {
                        val possibleInstructor =
                            nome.lowercase().split(" ").dropLast(1).toString()

                        val vowels = listOf('a', 'e', 'i', 'o', 'u')
                        var count = 0

                        for (i in possibleInstructor) {
                            if (i in vowels) {
                                count++
                            }
                        }
                        if (count == 3) {
                            return this
                        }
                    }
                }
            }
        }
        return null
    }
}

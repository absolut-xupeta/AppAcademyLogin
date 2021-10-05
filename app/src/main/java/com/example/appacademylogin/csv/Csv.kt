package com.example.appacademylogin.csv

import android.content.Context

import com.example.appacademylogin.classes.Candidate
import com.example.appacademylogin.utils.Utils
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * Csv information extractor.
 */
object Csv {
    private var candidatesList: ArrayList<Candidate>? = null

    /**
     * Get the candidates information's and add them to a list of 'Candidates'.
     */
    fun getCandidates(context: Context): ArrayList<Candidate> {
        return candidatesList ?: ArrayList<Candidate>().also { list ->
            val streamReader = InputStreamReader(context.assets.open("AppAcademy_Candidates.csv"))
            val bufferedReader = BufferedReader(streamReader)
            var row: List<String>

            bufferedReader.readLine()

            while (bufferedReader.ready()) {
                row = bufferedReader.readLine().split(';')
                list.add(
                    Candidate(
                        name = row[0],
                        age = row[2].filter { it.isDigit() }.toInt(),
                        stack = row[1].lowercase(),
                        state = row[3].lowercase()
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
    fun getPercentage(candidates: ArrayList<Candidate>?): String {
        // Total of each candidate job.
        var totalIos = 0
        var totalAndroid = 0
        var totalQA = 0

        // AverageQA age.
        var averageQA = 0

        candidates?.forEach {
            when (it.stack) {
                "qa" -> {
                    val age = it.age
                    averageQA += age
                    totalQA += 1
                }
                "ios" -> totalIos += 1
                "android" -> totalAndroid += 1
                else -> throw Exception("Check the strings above and see if they match the candidates list stack")
            }
        } ?: return "List is empty."

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
    fun getUniqueStates(candidates: ArrayList<Candidate>?): String {
        val unique = HashMap<String, Int?>()

        candidates?.forEach {
            if (unique.containsKey(it.state)) {
                val u: Int? = unique.getValue(it.state)
                unique[it.state] = u?.plus(1)
            } else {
                unique[it.state] = 1
            }
        } ?: return "List is empty"

        val uniqueStates = unique.size

        val (least1, least2) =
            unique.toList().sortedBy { (_, value) -> value }.dropLast(uniqueStates - 2)

        val leastStates =
            "\n${least1.first.uppercase()} = ${least1.second} candidato(s)\n${least2.first.uppercase()} = ${least2.second} candidato(s)"

        return "Unique states: $uniqueStates\n" +
                "States with least candidates: " +
                leastStates
    }

    /**
     * Searches for the iOS instructor inside the candidates list.
     */
    fun getIosInstructor(): Candidate? {
        candidatesList?.forEach {
            if (it.age in (20..31).toList().filter { it % 2 != 0 && Utils.primeNumber(it) }) {
                if (it.stack != "ios" && it.name[Utils.getIndex(it.name) + 1] == 'V') {
                    return it
                }
            }
        } ?: return null
        return null
    }

    /**
     * Searches for the Android instructor inside the candidates list.
     */
    fun getAndroidInstructor(list: ArrayList<Candidate>?, ios: Candidate?): Candidate? {
        list?.forEach {
            it.apply {
                if (age < (ios?.age ?: 0) && age % 2 != 0) {
                    if (name[Utils.getIndex(name) - 1] == 'o' && stack != "android") {
                        val possibleInstructor =
                            name.lowercase().split(" ").dropLast(1).toString()

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

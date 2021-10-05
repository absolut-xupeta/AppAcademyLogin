package com.example.appacademylogin.utils

object Utils {

    /**
     * Find the percentage of each job by the total number of candidates.
     */
    fun percentage(stack: Int, total: Int): Int {
        return (100 * stack.toFloat() / total.toFloat()).toInt()
    }

    /**
     * Compares a number to a range and determine if it is a prime number.
     */
    fun primeNumber(n: Int): Boolean {
        for (i in 2 until n) {
            if (n % i == 0) {
                return false
            }
        }
        return true
    }

    /**
     * Gets the index of the division from a name to know where the first name ends
     * and the last begins.
     */
    fun getIndex(name: String): Int = name.indexOf(char = ' ')

    /**
     * Login name is auto-generated, separating spaces and replacing them for underlines
     * and making the name lowercase like: john_doe
     */
    fun getLogin(name: String): String {
        return name.replace(oldChar = ' ', newChar = '_').lowercase()
    }
}
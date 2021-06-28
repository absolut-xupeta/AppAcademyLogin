package com.example.appacademylogin.utils

import com.example.appacademylogin.datastructure.PSList

fun <T : Any> PSList<T>.toList(): List<T> {
    val returnList = ArrayList<T>()
    for (i in 0 until size()) {
        returnList.add(this[i])
    }
    return returnList
}

inline fun <T : Any> PSList<T>.forEach(action: (T) -> Unit) {
    for (i in 0 until size()) action(this[i])
}
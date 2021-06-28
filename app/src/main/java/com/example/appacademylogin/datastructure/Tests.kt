package com.example.appacademylogin.datastructure

fun testMyArrayList(): String {
    val simpleArrayList = MyArrayList<String>()
    val (passed, message) = testPsListImplementation(simpleArrayList, false)
    return if (passed) "MyArrayList: All tests passed" else "Test failed with: ${message ?: ""}"
}

//private fun testMyLinkedList() {
//    val simpleLinkedList = MyLinkedList<String>()
//    val (passed, message) = testPsListImplementation(simpleLinkedList, true)
//    return if (passed) "MyLinkedList: All tests passed" else "Test failed with: ${message ?: ""}"
//}

fun testPsListImplementation(psList: PSList<String>, isLinkedList: Boolean): Pair<Boolean, String?> {
    try {
        psList.add("String 1")
        psList.add("String 2")
        psList.add("String 3")

        if (psList.size() != 3 || (!isLinkedList && psList.realSize() != DEFAULT_LIST_INIT_SIZE)) {
            return Pair(false, "Test1: Expected size = 3 and realSize = $DEFAULT_LIST_INIT_SIZE")
        }

        psList.add("String 4")
        psList.add("String 5")

        if (psList.size() != 5 || (!isLinkedList && psList.realSize() != DEFAULT_LIST_INIT_SIZE)) {
            return Pair(false, "Test2: Expected size = 5 and realSize = $DEFAULT_LIST_INIT_SIZE")
        }

        if (!psList.remove(4)) {
            return Pair(false, "Test3: Failed to remove element at position 4")
        }

        if (psList.size() != 4 || (!isLinkedList && psList.realSize() != DEFAULT_LIST_INIT_SIZE)) {
            return Pair(false, "Test4: Expected size = 4 and realSize = $DEFAULT_LIST_INIT_SIZE")
        }

        psList.add("String 5")

        if (!psList.contains("String 5")) {
            return Pair(false, "Test5: Expected to contain \"String 5\"")
        }

        psList.add("String 6")

        if (psList.size() != 6 || (!isLinkedList && psList.realSize() != DEFAULT_LIST_INIT_SIZE * 2)) {
            return Pair(false, "Test6: Expected size = 6 and realSize = $DEFAULT_LIST_INIT_SIZE")
        }

        for (i in 0 until psList.size()) {
            if (psList[i] != "String ${i + 1}") {
                return Pair(false, "Test7: Expected string \"String ${i + 1}\" at position $i")
            }
        }

        psList.remove(1)

        if (psList[0] != "String 1") {
            return Pair(false, "Test8: Expected string \"String 1\" at position 0")
        }

        for (i in 1 until psList.size()) {
            if (psList[i] != "String ${i + 2}") {
                return Pair(false, "Test9: Expected string \"String ${i + 2}\" at position $i")
            }
        }

        psList.remove(2)

        if (psList.size() != 4 || (!isLinkedList && psList.realSize() != DEFAULT_LIST_INIT_SIZE)) {
            return Pair(false, "Test10: Expected size = 4 and realSize = $DEFAULT_LIST_INIT_SIZE")
        }

        for (i in 2 until psList.size()) {
            if (psList[i] != "String ${i + 3}") {
                return Pair(false, "Test11: Expected string \"String ${i + 3}\" at position $i")
            }
        }

        psList.remove(0)
        psList.remove(0)

        if (!psList.contains("String 5")) {
            return Pair(false, "Test12: Expected to contain \"String 5\"")
        }

        psList.remove(0)

        if (!psList.contains("String 6")) {
            return Pair(false, "Test13: Expected to contain \"String 6\"")
        }

        psList.remove(0)

        if (psList.size() != 0 || (!isLinkedList && psList.realSize() != DEFAULT_LIST_INIT_SIZE)) {
            return Pair(false, "Test14: Expected size = 0 and realSize = $DEFAULT_LIST_INIT_SIZE")
        }

        psList.add("final test")

        if (psList.size() != 1 || (!isLinkedList && psList.realSize() != DEFAULT_LIST_INIT_SIZE)) {
            return Pair(false, "Test15: Expected size = 1 and realSize = $DEFAULT_LIST_INIT_SIZE")
        }

        if (psList[0] != "final test") {
            return Pair(false, "Test16: Expected string \"final test\" at position 0")
        }

        return Pair(true, null)
    } catch (e: Exception) {
        return Pair(false, e.message ?: "Unknown exception")
    }
}
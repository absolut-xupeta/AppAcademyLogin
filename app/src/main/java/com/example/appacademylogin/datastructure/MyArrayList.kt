package com.example.appacademylogin.datastructure

class MyArrayList<T : Any> : PSList<T> {

    private var dataArray: Array<Any?> = Array(DEFAULT_LIST_INIT_SIZE) {
        null
    }
    private var count: Int = 0

    override fun add(item: T) {
        if (count < dataArray.size) {
            dataArray[count++] = item
            return
        }

        val newArray: Array<Any?> = Array(dataArray.size + DEFAULT_LIST_INIT_SIZE) {
            when (it) {
                in 0 until count -> dataArray[it]
                else -> Any()
            }
        }
        newArray[count++] = item
        dataArray = newArray
    }

    override fun contains(item: T): Boolean {
        dataArray.forEach {
            if (item == it) {
                return true
            }
        }
        return false
    }

    override operator fun get(position: Int): T {
        if (position < 0 || position > count - 1) {
            throw IndexOutOfBoundsException("You tried to access index $position but the array size is: ${dataArray.size}")
        }
        return dataArray[position] as T
    }

    fun isEmpty(): Boolean {
        return count == 0
    }

    override fun remove(position: Int): Boolean {
        if (position < 0 || position > count - 1) {
            throw IndexOutOfBoundsException("You tried to access index $position but the array size is: ${dataArray.size}")
        }
        if (dataArray.size - count < DEFAULT_LIST_INIT_SIZE) {
            for (index in dataArray.indices) {
                when (index) {
                    in 0 until position -> continue
                    in position until count -> {
                        if (index + 1 == dataArray.size) {
                            dataArray[index] = Any()
                        } else {
                            dataArray[index] = dataArray[index + 1]
                        }
                    }
                    else -> dataArray[index] = Any()

                }
            }
        } else {
            val trimmedArray: Array<Any?> = Array(dataArray.size - DEFAULT_LIST_INIT_SIZE) {
                when (it) {
                    in 0 until position -> dataArray[it]
                    in position until count -> {
                        if (it + 1 == dataArray.size) {
                            Any()
                        } else {
                            dataArray[it + 1]
                        }

                    }
                    else -> Any()
                }
            }
            dataArray = trimmedArray
        }
        count--
        return true
    }

    override fun size() = count

    override fun realSize() = dataArray.size
}
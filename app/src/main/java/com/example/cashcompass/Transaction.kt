package com.example.cashcompass

data class Transaction(
    val id: Int = generateId(),
    val title: String,
    private val _values: MutableList<Double> = mutableListOf()
) {
    companion object {
        private var currentId: Int = 0 // Static variable to keep track of the last assigned ID
        private fun generateId(): Int {
            currentId++
            return currentId
        }
    }

    fun addValue(value: Double) {
        _values.add(value)
    }

    fun getValues(): List<Double> {
        return _values.toList() // Return a copy of the list
    }

    fun getTotalValue(): Double {
        return _values.sum() // Sum up the values in the list
    }
}
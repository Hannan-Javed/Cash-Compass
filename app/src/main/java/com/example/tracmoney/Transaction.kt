package com.example.tracmoney

data class TalliedAmounts(
    val dates: String,
    val amount: Double,
    var tallyCount: Int
)

data class Transaction(
    private var id: Int? = null,
    val title: String,
    val dates: MutableList<String> = mutableListOf(),
    private val amounts: MutableList<Double> = mutableListOf()
) {
    fun setId(id: Int) {
        this.id = id
    }

    fun getId(): Int? {
        return id
    }

    fun addAmount(value: Double) {
        amounts.add(value)
    }

    fun getFirstAmount(): Double {
        return amounts.firstOrNull() ?: 0.0
    }

    fun getTalliedAmounts(): List<TalliedAmounts> {
        val amountCountMap = mutableMapOf<Double, MutableList<String>>()
        for ((index, amount) in amounts.withIndex()) {
            amountCountMap.computeIfAbsent(amount) { mutableListOf() }.add(dates[index])
        }

        return amountCountMap.map { (amount, dateList) ->
            TalliedAmounts(dateList.joinToString(", "), amount, dateList.size)
        }
    }

    fun getTotalAmount(): Double {
        return amounts.sum()
    }
}
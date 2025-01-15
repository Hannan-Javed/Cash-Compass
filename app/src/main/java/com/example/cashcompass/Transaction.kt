package com.example.cashcompass

data class TalliedAmounts(
    val dates: String,
    val amount: Double,
    var tallyCount: Int
)

data class Transaction(
    private var id: Int? = null,
    val title: String,
    val dates: MutableList<String> = mutableListOf(),
    private val _amounts: MutableList<Double> = mutableListOf()
) {

    fun setId(id: Int) {
        this.id = id
    }

    fun addAmount(value: Double) {
        _amounts.add(value)
    }

    fun getFirstAmount(): Double {
        return _amounts.first()
    }

    fun getTalliedAmounts(): List<TalliedAmounts> {
        val amountCountMap = mutableMapOf<Double, MutableList<String>>()

        for ((index, amount) in _amounts.withIndex()) {
            amountCountMap.computeIfAbsent(amount) { mutableListOf() }.add(dates[index])
        }

        return amountCountMap.map { (amount, dateList) ->
            TalliedAmounts(dateList.joinToString(", "), amount, dateList.size)
        }
    }

    fun getTotalAmount(): Double {
        return _amounts.sum()
    }
}

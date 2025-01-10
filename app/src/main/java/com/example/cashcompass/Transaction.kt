package com.example.cashcompass

data class Tally(
    val amount: Double,
    val count: Int
)

data class Transaction(
    private var id: Int? = null,
    val title: String,
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

    fun getTalliedAmounts(): List<Tally> {
        val amountCountMap = mutableMapOf<Double, Int>()

        for (amount in _amounts) {
            amountCountMap[amount] = amountCountMap.getOrDefault(amount, 0) + 1
        }
        return amountCountMap.map { (amount, count) -> Tally(amount, count) }
    }

    fun getTotalAmount(): Double {
        return _amounts.sum()
    }
}
package com.example.cashcompass

data class TransactionSection(
    var id: Int,
    val title: String,
    val currency: String,
    var transactions: MutableList<Transaction>? = mutableListOf()
){

    fun getTotalAmount(): Double {
        return transactions!!.sumOf { it.getTotalAmount() }
    }

    fun addTransaction(t: Transaction) {
        transactions?.let {
            val existingTransaction =
                it.find { transaction -> transaction.title == t.title }
            if (existingTransaction != null) {
                existingTransaction.addAmount(t.getFirstAmount())
                existingTransaction.dates.addAll(t.dates)
            } else {
                val newId = if (it.isNotEmpty()) {
                    it.last().getId()?.plus(1)
                } else {
                    1
                }
                if (newId != null) {
                    t.setId(newId)
                }
                it.add(t)
            }
        }
    }
}
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
            } else {
                t.setId(it.size + 1)
                it.add(t)
            }
        }
    }
}
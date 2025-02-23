package com.example.tracmoney

data class TransactionSection(
    var id: Int,
    val title: String,
    val currency: String,
    var transactions: MutableList<Transaction> = mutableListOf()
) {
    fun getTotalAmount(): Double {
        return transactions.sumOf { it.getTotalAmount() }
    }

    fun addTransaction(transaction: Transaction) {
        transactions.add(transaction)
    }

    fun removeTransaction(transactionId: Int) {
        transactions.removeIf { it.getId() == transactionId }
    }
}
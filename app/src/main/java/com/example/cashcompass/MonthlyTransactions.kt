package com.example.cashcompass
import com.example.cashcompass.Transaction

data class MonthlyTransactions(
    var month: Int,
    val year: Int,
    private var transactions: MutableList<Transaction>? = mutableListOf()
) {

    fun addTransaction(transaction: Transaction) {
        if (transactions == null) {
            transactions = mutableListOf()
        }
        transactions?.add(transaction)
    }

    fun getTransactions(): List<Transaction> {
        return transactions!!.toList()
    }

    fun getTotalValue(): Double {
        return transactions!!.sumOf { it.getTotalValue() }
    }

    fun getTransactionCount(): Int {
        return transactions!!.size
    }
}
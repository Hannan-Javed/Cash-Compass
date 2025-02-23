package com.example.tracmoney
data class MonthlySpending(
    val title: String,
    val currency: String,
    val totalValue: Double
)
data class MonthlyTransactions(
    val month: Int,
    val year: Int,
    var sections: MutableList<TransactionSection> = mutableListOf()
) {
    fun addSection(section: TransactionSection) {
        sections.add(section)
    }

    fun removeSection(sectionId: Int) {
        sections.removeIf { it.id == sectionId }
    }

    fun addTransactionToSection(sectionId: Int, transaction: Transaction) {
        val section = sections.find { it.id == sectionId }
        section?.addTransaction(transaction)
    }

    fun getMonthlySpending(): List<MonthlySpending> {
        return sections.map { section ->
            MonthlySpending(section.title, section.currency, section.getTotalAmount())
        }
    }
}
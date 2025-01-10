package com.example.cashcompass

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

    fun getSectionById(sectionId: Int): TransactionSection? {
        return sections.find { it.id == sectionId }
    }

    fun addTransactionToSection(sectionId: Int, transaction: Transaction) {
        val section = getSectionById(sectionId)
        section?.addTransaction(transaction)
    }

    fun getMonthlySpending(): List<MonthlySpending> {
        val totalSpending = mutableListOf<MonthlySpending>()
        for (section in sections) {
            totalSpending.add(MonthlySpending(section.title, section.currency, section.getTotalAmount()))
        }
        return totalSpending
    }
}
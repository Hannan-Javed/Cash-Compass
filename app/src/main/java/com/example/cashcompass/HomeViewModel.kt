package com.example.cashcompass

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    companion object {
        var totalSectionsCreated: Int = 1
    }
    private val _selectedSectionId = MutableLiveData<Int>()
    val selectedSectionId: LiveData<Int> get() = _selectedSectionId

    private val _monthlyTransactions = MutableLiveData<MonthlyTransactions>()
    val monthlyTransactions: MutableLiveData<MonthlyTransactions> get() = _monthlyTransactions

    init {
        _monthlyTransactions.value = MonthlyTransactions(
            month = 1,
            year = 2023,
            sections = mutableListOf(
                TransactionSection(
                    id = 1,
                    title = "Default Section",
                    currency = "USD"
                )
            )
        )
        _selectedSectionId.value = monthlyTransactions.value?.sections?.first()?.id
    }

    fun getNewId(): Int {
        return ++totalSectionsCreated
    }

    fun selectSection(sectionId: Int) {
        _selectedSectionId.value = sectionId
    }

    fun addSection(newSection: TransactionSection) {
        _monthlyTransactions.value?.sections?.add(newSection)
        _selectedSectionId.value = newSection.id
    }

    fun addTransaction(transaction: Transaction) {
        _monthlyTransactions.value?.sections?.find { it.id == selectedSectionId.value }?.addTransaction(transaction)
    }

    fun deleteSection(sectionId: Int) {
        if (_selectedSectionId.value == sectionId) {
            _selectedSectionId.value = monthlyTransactions.value?.sections?.first()?.id
        }
        _monthlyTransactions.value?.sections?.removeIf { it.id == sectionId }
    }
}
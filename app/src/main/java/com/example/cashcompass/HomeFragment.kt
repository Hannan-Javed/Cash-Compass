package com.example.cashcompass

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var buttonAddSection: Button
    private lateinit var buttonAddTransaction: Button
    private lateinit var sectionAdapter: SectionAdapter
    private val randomTransactions: MutableList<Transaction> =
        mutableListOf(
            Transaction(
                dates = mutableListOf("2023-01-01"),
                title = "Transaction 1",
                _amounts = mutableListOf(10.0)
            ),
            Transaction(
                dates = mutableListOf("2023-01-02"),
                title = "Transaction 2",
                _amounts = mutableListOf(20.0)
            ),
            Transaction(
                dates = mutableListOf("2023-01-03"),
                title = "Transaction 3",
                _amounts = mutableListOf(30.0)
            ),
            Transaction(
                dates = mutableListOf("2023-01-04"),
                title = "Transaction 4",
                _amounts = mutableListOf(40.0)
            ),
            )
    // Hardcoded MonthlyTransactions with a default section
    private val monthlyTransactions = MonthlyTransactions(
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
    private var selectedSectionId: Int = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewTransactions)
        buttonAddSection = view.findViewById(R.id.buttonAddSection)
        buttonAddTransaction = view.findViewById(R.id.buttonAddTransaction)

        val dateTextView: TextView = view.findViewById(R.id.textViewDate)
        val currentDate = LocalDate.now().toString()
        dateTextView.text = currentDate

        setupRecyclerView()
        setupButtonClick()

        return view
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        sectionAdapter = SectionAdapter(
            monthlyTransactions,
            selectedSectionId
        ) { sectionId ->
            selectedSectionId = sectionId
        }
        recyclerView.adapter = sectionAdapter
    }

    private fun setupButtonClick() {
        buttonAddSection.setOnClickListener {
            val newSection = TransactionSection(
                id = monthlyTransactions.sections.last().id + 1,
                title = "New Section",
                currency = "USD"
            )
            monthlyTransactions.addSection(newSection)
            sectionAdapter.updateSectionSelection(newSection.id)
            // now selectedSection is updated to the new section inside adapter
            sectionAdapter.notifyItemChanged(monthlyTransactions.sections.size - 1)

        }

        buttonAddTransaction.setOnClickListener {
            monthlyTransactions.addTransactionToSection(
                selectedSectionId,
                randomTransactions[(0..3).random()]
            )
            sectionAdapter.
                    notifyItemChanged(monthlyTransactions.sections.indexOfFirst { it.id == selectedSectionId })
        }
    }
}
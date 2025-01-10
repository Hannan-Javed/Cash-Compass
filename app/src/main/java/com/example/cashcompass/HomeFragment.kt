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

    private var selectedSection: TransactionSection = monthlyTransactions.sections.first()

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
        sectionAdapter =
            SectionAdapter(monthlyTransactions.sections, { section: TransactionSection ->
                selectedSection = section
            })
        recyclerView.adapter = sectionAdapter
    }

    private fun setupButtonClick() {
        buttonAddSection.setOnClickListener {
            val newSection = TransactionSection(
                id = monthlyTransactions.sections.size + 1,
                title = "New Section",
                currency = "USD"
            )
            monthlyTransactions.addSection(newSection)
            selectedSection = newSection
            sectionAdapter.notifyDataSetChanged()
        }

        buttonAddTransaction.setOnClickListener {
            monthlyTransactions.addTransactionToSection(
                selectedSection.id,
                Transaction(title = "New Transaction", _amounts  = mutableListOf(10.0))
            )
            sectionAdapter.notifyDataSetChanged()
        }
    }
}
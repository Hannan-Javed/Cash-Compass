package com.example.cashcompass

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var buttonAddSection: Button
    private lateinit var buttonAddTransaction: Button
    private lateinit var sectionAdapter: SectionAdapter

    private val randomTransactions = listOf(
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewSections)
        buttonAddSection = view.findViewById(R.id.buttonAddSection)
        buttonAddTransaction = view.findViewById(R.id.buttonAddTransaction)

        val dateTextView: TextView = view.findViewById(R.id.textViewDate)
        dateTextView.text = LocalDate.now().toString()

        setupRecyclerView()
        setupButtonClick()
        observeViewModel()

        return view
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        sectionAdapter = SectionAdapter(
            selectedSectionId = viewModel.selectedSectionId.value ?: 0,
            onSectionClick = { sectionId ->
                viewModel.selectSection(sectionId)
            },
            onDeleteSection = { sectionId ->
                viewModel.deleteSection(sectionId)
            }
        )
        recyclerView.adapter = sectionAdapter
    }

    private fun setupButtonClick() {
        buttonAddSection.setOnClickListener {
            val newSectionId = viewModel.getNewId()
            val newSection = TransactionSection(
                id = newSectionId,
                title = "New Section $newSectionId",
                currency = "USD"
            )
            viewModel.addSection(newSection)
        }

        buttonAddTransaction.setOnClickListener {
            val randomTransaction = randomTransactions.random()
            viewModel.addTransaction(randomTransaction)
            val sectionIndex = viewModel.monthlyTransactions.value?.sections?.indexOfFirst { it.id == viewModel.selectedSectionId.value }
            if (sectionIndex != null) {sectionAdapter.notifyItemChanged(sectionIndex)}
        }
    }

    private fun observeViewModel() {

        viewModel.monthlyTransactions.observe(viewLifecycleOwner) { monthlyTransactions ->
            monthlyTransactions?.let {
                sectionAdapter.submitList(it.sections)
            }
        }

        viewModel.selectedSectionId.observe(viewLifecycleOwner) { sectionId ->
            sectionAdapter.updateSelectedSection(sectionId)
        }
    }
}
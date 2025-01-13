package com.example.cashcompass

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SectionAdapter(
    private val monthlyTransactions: MonthlyTransactions,
    private var selectedSectionId: Int,
    private val onSectionClick: (Int) -> Unit
) : RecyclerView.Adapter<SectionAdapter.SectionViewHolder>() {

    private val adapterMap = mutableMapOf<Int, TransactionAdapter>()

    fun onSectionDelete(sectionId: Int) {
        val sectionIndex = monthlyTransactions.sections.indexOfFirst { it.id == sectionId }
        if (sectionIndex != -1) {
            // Remove the section's transactions adapter from the map
            adapterMap.remove(sectionId)

            // Remove the section from the monthly transactions
            monthlyTransactions.removeSection(sectionId)

            // Notify the adapter of the item removal
            notifyItemRemoved(sectionIndex)

            // Update selected section if necessary
            if (selectedSectionId == sectionId) {
                updateSectionSelection(monthlyTransactions.sections.last().id)
            }
        }
    }

    val updateSectionSelection = { sectionId: Int ->
            val previousSelected = selectedSectionId
            selectedSectionId = sectionId
            val previousIndex = monthlyTransactions.sections.indexOfFirst { it.id == previousSelected }
            val newIndex = monthlyTransactions.sections.indexOfFirst { it.id == sectionId }

            if (previousIndex != -1) notifyItemChanged(previousIndex)
            if (newIndex != -1) notifyItemChanged(newIndex)
            onSectionClick(sectionId)
    }

    inner class SectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.textViewSectionTitle)
        private val deleteImageView: ImageView = itemView.findViewById(R.id.imageViewDelete)
        private val sectionLayout: LinearLayout = itemView.findViewById(R.id.sectionLayout)
        private val transactionsRecyclerView: RecyclerView = itemView.findViewById(R.id.recyclerViewTransactions)

        fun bind(section: TransactionSection) {
            titleTextView.text = section.title

            if (section.id == 1) {
                deleteImageView.visibility = View.GONE
            } else {
                deleteImageView.visibility = View.VISIBLE
            }

            itemView.setBackgroundColor(if (section.id == selectedSectionId) Color.LTGRAY else Color.WHITE)

            val transactionAdapter = adapterMap.getOrPut(section.id) {
                TransactionAdapter(
                    section.transactions ?: emptyList(),
                    section.id,
                    updateSectionSelection
                )
            }

            transactionsRecyclerView.layoutManager = LinearLayoutManager(itemView.context)
            transactionsRecyclerView.adapter = transactionAdapter

            deleteImageView.setOnClickListener {
                onSectionDelete(section.id)
            }

            sectionLayout.setOnClickListener {
                updateSectionSelection(section.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_section, parent, false)
        return SectionViewHolder(view)
    }

    override fun onBindViewHolder(holder: SectionViewHolder, position: Int) {
        holder.bind(sections[position])
    }

    override fun getItemCount(): Int = sections.size
}
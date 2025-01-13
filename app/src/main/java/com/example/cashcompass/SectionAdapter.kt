package com.example.cashcompass

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SectionAdapter(
    private val sections: List<TransactionSection>,
    private var selectedSection: Int,
    private val onSectionClick: (Int) -> Unit
) : RecyclerView.Adapter<SectionAdapter.SectionViewHolder>() {

    private val adapterMap = mutableMapOf<Int, TransactionAdapter>()
    val updateSectionSelection = { sectionId: Int ->
        if (selectedSection != sectionId) {
            val previousSelected = selectedSection
            selectedSection = sectionId
            val previousIndex = sections.indexOfFirst { it.id == previousSelected }
            val newIndex = sections.indexOfFirst { it.id == sectionId }

            if (previousIndex != -1) notifyItemChanged(previousIndex)
            if (newIndex != -1) notifyItemChanged(newIndex)
            onSectionClick(sectionId)
        }
    }

    inner class SectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.textViewSectionTitle)
        private val sectionLayout: LinearLayout = itemView.findViewById(R.id.sectionLayout)
        private val transactionsRecyclerView: RecyclerView = itemView.findViewById(R.id.recyclerViewTransactions)

        fun bind(section: TransactionSection) {
            titleTextView.text = section.title

            itemView.setBackgroundColor(if (section.id == selectedSection) Color.LTGRAY else Color.WHITE)

            val transactionAdapter = adapterMap.getOrPut(section.id) {
                TransactionAdapter(
                    section.transactions ?: emptyList(),
                    section.id,
                    updateSectionSelection
                )
            }

            transactionsRecyclerView.layoutManager = LinearLayoutManager(itemView.context)
            transactionsRecyclerView.adapter = transactionAdapter

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
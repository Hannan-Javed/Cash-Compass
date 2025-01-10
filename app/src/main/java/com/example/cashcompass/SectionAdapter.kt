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
    private val onSectionClick: (TransactionSection) -> Unit
) : RecyclerView.Adapter<SectionAdapter.SectionViewHolder>() {

    inner class SectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.textViewSectionTitle)
        private val transactionsRecyclerView: RecyclerView = itemView.findViewById(R.id.recyclerViewTransactions)

        fun bind(section: TransactionSection) {
            titleTextView.text = section.title

            // Set up nested RecyclerView for transactions
            val transactionAdapter = section.transactions?.let { TransactionAdapter(it) }
            transactionsRecyclerView.layoutManager = LinearLayoutManager(itemView.context)
            transactionsRecyclerView.adapter = transactionAdapter

            itemView.setOnClickListener {
                onSectionClick(section)
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

    override fun getItemCount(): Int {
        return sections.size
    }
}

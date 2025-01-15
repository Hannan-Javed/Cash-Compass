package com.example.cashcompass

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class SectionAdapter(
    private var selectedSectionId: Int,
    private val onSectionClick: (Int) -> Unit,
    private val onDeleteSection: (Int) -> Unit
) : ListAdapter<TransactionSection, SectionAdapter.SectionViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TransactionSection>() {
            override fun areItemsTheSame(
                oldItem: TransactionSection,
                newItem: TransactionSection
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: TransactionSection,
                newItem: TransactionSection
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    // Map to hold TransactionAdapters for each section
    private val adapterMap = mutableMapOf<Int, TransactionAdapter>()

    // Update selected section and notify adapter about changes
    fun updateSelectedSection(sectionId: Int) {
        val previousSelected = selectedSectionId
        selectedSectionId = sectionId

        val previousPosition = currentList.indexOfFirst { it.id == previousSelected }
        val newPosition = currentList.indexOfFirst { it.id == sectionId }

        if (previousPosition != -1) {
            notifyItemChanged(previousPosition)
        }
        if (newPosition != -1) {
            notifyItemChanged(newPosition)
        }
    }

    inner class SectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.textViewSectionTitle)
        private val deleteImageView: ImageView = itemView.findViewById(R.id.imageViewDelete)
        private val sectionLayout: LinearLayout = itemView.findViewById(R.id.sectionLayout)
        private val transactionsRecyclerView: RecyclerView = itemView.findViewById(R.id.recyclerViewTransactions)

        fun bind(section: TransactionSection) {
            titleTextView.text = section.title

            // Show/hide delete button based on section ID
            deleteImageView.visibility = if (section.id == 1) View.GONE else View.VISIBLE

            // Highlight the selected section
            itemView.setBackgroundColor(
                if (section.id == selectedSectionId) Color.LTGRAY else Color.WHITE
            )

            // Set up the TransactionAdapter for this section
            val transactionAdapter = adapterMap.getOrPut(section.id) {
                TransactionAdapter(
                    section.transactions?: emptyList<Transaction>().toMutableList(),
                    section.id,
                    onSectionClick
                )
            }

            transactionsRecyclerView.layoutManager = LinearLayoutManager(itemView.context)
            transactionsRecyclerView.adapter = transactionAdapter

            deleteImageView.setOnClickListener {
                onDeleteSection(section.id)
                notifyItemRemoved(adapterPosition)
            }

            sectionLayout.setOnClickListener {
                onSectionClick(section.id)
                updateSelectedSection(section.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_section, parent, false)
        return SectionViewHolder(view)
    }

    override fun onBindViewHolder(holder: SectionViewHolder, position: Int) {
        val section = getItem(position)
        holder.bind(section)
    }
}
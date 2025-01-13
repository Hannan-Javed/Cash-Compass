package com.example.cashcompass

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class TransactionAdapter(
    private val transactions: List<Transaction>,
    private var transactionSection: Int,
    private val updateSectionSelection: (Int) -> Unit
) : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    // Set to keep track of expanded item positions
    private val expandedPositions = mutableSetOf<Int>()

    inner class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.textViewTitle)
        private val totalValueTextView: TextView = itemView.findViewById(R.id.textViewTotalValue)
        private val expandedLayout: LinearLayout = itemView.findViewById(R.id.expandedLayout)
        private val transactionsListRecyclerView: RecyclerView = itemView.findViewById(R.id.recyclerViewTransactionsList)

        fun bind(transaction: Transaction, position: Int) {
            titleTextView.text = transaction.title
            totalValueTextView.text = transaction.getTotalAmount().toString()

            // Set up the tally RecyclerView
            transactionsListRecyclerView.layoutManager = LinearLayoutManager(itemView.context)
            transactionsListRecyclerView.adapter = TransactionDetailListAdapter(
                transaction.getTalliedAmounts(),
                transactionSection,
                updateSectionSelection
                )

            // Set visibility based on whether the position is expanded
            expandedLayout.visibility = if (expandedPositions.contains(position)) View.VISIBLE else View.GONE

            // Handle click to expand/collapse
            itemView.setOnClickListener {
                if (expandedPositions.contains(position)) {
                    expandedPositions.remove(position)
                } else {
                    expandedPositions.add(position)
                }
                notifyItemChanged(position)
                updateSectionSelection(transactionSection)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_transaction, parent, false)
        return TransactionViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(transactions[position], position)
    }

    override fun getItemCount(): Int {
        return transactions.size
    }
}
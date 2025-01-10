package com.example.cashcompass

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TransactionAdapter(private val transactions: MutableList<Transaction>) :
    RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    inner class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.textViewTitle)
        private val totalValueTextView: TextView = itemView.findViewById(R.id.textViewTotalValue)
        private val tallyLayout: LinearLayout = itemView.findViewById(R.id.layoutTally)

        fun bind(transaction: Transaction) {
            val talliedAmounts = transaction.getTalliedAmounts()
            titleTextView.text = transaction.title

            if (talliedAmounts.size == 1) {
                // Single tally
                totalValueTextView.text = "${talliedAmounts[0].amount} (${talliedAmounts[0].count})"
                tallyLayout.visibility = View.GONE
            } else {
                // Multiple tallies: clickable line
                totalValueTextView.text = "${transaction.getTotalAmount()}"
                tallyLayout.visibility = View.VISIBLE
                tallyLayout.removeAllViews()

                for (tally in talliedAmounts) {
                    val tallyTextView = TextView(itemView.context).apply {
                        text = "${tally.count} x ${tally.amount}"
                    }
                    tallyLayout.addView(tallyTextView)
                }

                itemView.setOnClickListener {
                    // Toggle visibility of tallies
                    tallyLayout.visibility = if (tallyLayout.visibility == View.VISIBLE) {
                        View.GONE
                    } else {
                        View.VISIBLE
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_transaction, parent, false)
        return TransactionViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(transactions[position])
    }

    override fun getItemCount(): Int {
        return transactions.size
    }
}
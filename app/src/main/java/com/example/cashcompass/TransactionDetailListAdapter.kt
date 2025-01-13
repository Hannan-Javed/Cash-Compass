package com.example.cashcompass

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TransactionDetailListAdapter(
    private val transactionsList: List<TalliedAmounts>,
    private var transactionSection: Int,
    private val updateSectionSelection: (Int) -> Unit
    ) : RecyclerView.Adapter<TransactionDetailListAdapter.TallyViewHolder>() {

    inner class TallyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val datesTextView: TextView = itemView.findViewById(R.id.textViewTallyDates)
        private val amountTextView: TextView = itemView.findViewById(R.id.textViewTallyAmount)

        fun bind(talliedAmounts: TalliedAmounts) {
            datesTextView.text = talliedAmounts.dates
            amountTextView.text = talliedAmounts.amount.toString()

            itemView.setOnClickListener {
                updateSectionSelection(transactionSection)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TallyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_transaction_detail_list, parent, false)
        return TallyViewHolder(view)
    }

    override fun onBindViewHolder(holder: TallyViewHolder, position: Int) {
        holder.bind(transactionsList[position])
    }

    override fun getItemCount(): Int {
        return transactionsList.size
    }
}
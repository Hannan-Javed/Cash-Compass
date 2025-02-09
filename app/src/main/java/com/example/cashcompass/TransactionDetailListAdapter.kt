package com.example.cashcompass

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TransactionDetailListAdapter(
    private val transactionsList: List<TalliedAmounts>,
    private val transactionSectionId: Int,
    private val updateSectionSelection: (Int) -> Unit
) : RecyclerView.Adapter<TransactionDetailListAdapter.TallyViewHolder>() {

    private fun onMinusButtonClicked(talliedAmounts: TalliedAmounts) {
        if (talliedAmounts.tallyCount > 0) {
            talliedAmounts.tallyCount--
        }
        notifyItemChanged(transactionsList.indexOf(talliedAmounts))
    }

    inner class TallyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val datesTextView: TextView = itemView.findViewById(R.id.textViewTallyDates)
        private val tallyRepresentationTextView: TextView = itemView.findViewById(R.id.textViewTallyRepresentation)
        private val amountTextView: TextView = itemView.findViewById(R.id.textViewTallyAmount)
        private val minusButton: ImageView = itemView.findViewById(R.id.ic_minus)
        private val plusButton: ImageView = itemView.findViewById(R.id.ic_plus)

        fun bind(talliedAmounts: TalliedAmounts) {
            datesTextView.text = talliedAmounts.dates
            amountTextView.text = talliedAmounts.amount.toString()
            tallyRepresentationTextView.text = getTallyRepresentation(talliedAmounts.tallyCount)

            minusButton.setOnClickListener {
                // Call the local `onMinusButtonClicked` function
                onMinusButtonClicked(talliedAmounts)
                updateSectionSelection(transactionSectionId)
            }

            plusButton.setOnClickListener {
                talliedAmounts.tallyCount++
                tallyRepresentationTextView.text = getTallyRepresentation(talliedAmounts.tallyCount)
                notifyItemChanged(adapterPosition)
                updateSectionSelection(transactionSectionId)
            }

            itemView.setOnClickListener {
                updateSectionSelection(transactionSectionId)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TallyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_transaction_detail_list, parent, false)
        return TallyViewHolder(view)
    }

    override fun onBindViewHolder(holder: TallyViewHolder, position: Int) {
        holder.bind(transactionsList[position])
    }

    override fun getItemCount(): Int = transactionsList.size

    private fun getTallyRepresentation(count: Int): String {
        val fullGroups = count / 5
        val remainder = count % 5
        val tallyRepresentation = StringBuilder()

        repeat(fullGroups) {
            tallyRepresentation.append("卌 ") // Four single tally marks and a slash
        }
        repeat(remainder) {
            tallyRepresentation.append("|")
        }

        return tallyRepresentation.toString().trimEnd()
    }
}
package com.project.jangburich.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.jangburich.api.response.home.PurchaseHistory
import com.project.jangburich.databinding.RowWalletPaymentBinding
import com.project.jangburich.ui.MainActivity

class WalletAdapter(
    private var activity: MainActivity,
    private var purchaseHistories: List<PurchaseHistory>
) :
    RecyclerView.Adapter<WalletAdapter.ViewHolder>() {

    private var onItemClickListener: ((Int) -> Unit)? = null
    private var context: Context? = null
    private var selectedPosition: Int = 0

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClickListener = listener
    }

    fun updateList(newPurchaseHistories: List<PurchaseHistory>) {
        purchaseHistories = newPurchaseHistories
        notifyDataSetChanged()
    }


    interface OnItemClickListener {
        fun onItemClick(position: Int) {}
    }

    var itemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding =
            RowWalletPaymentBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = purchaseHistories[position].transactionTitle
        holder.description.text = purchaseHistories[position].transactionType
        holder.paymentValue.text = purchaseHistories[position].amount.toString()
        holder.date.text = purchaseHistories[position].date
    }

    override fun getItemCount() = purchaseHistories.size

    inner class ViewHolder(val binding: RowWalletPaymentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val title = binding.textViewTitle
        val description = binding.textViewDescription
        val date = binding.textViewDate
        val paymentValue = binding.textViewPaymentValue
    }
}
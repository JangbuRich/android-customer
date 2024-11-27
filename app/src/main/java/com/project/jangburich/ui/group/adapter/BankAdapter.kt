package com.project.jangburich.ui.group.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.jangburich.api.response.home.Team
import com.project.jangburich.databinding.RowBankBinding
import com.project.jangburich.databinding.RowHomeJangbuBinding
import com.project.jangburich.ui.MainActivity

class BankAdapter(
    private var activity: MainActivity,
    private var bankList: List<Bank>
) :
    RecyclerView.Adapter<BankAdapter.ViewHolder>() {

    private var onItemClickListener: ((Int) -> Unit)? = null
    private var context: Context? = null
    private var selectedPosition: Int = 0

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClickListener = listener
    }

    fun updateList(
        newBankList: List<Bank>
    ) {
        bankList = newBankList
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int) {}
    }

    var itemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding =
            RowBankBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bankName.text = bankList[position].name
        holder.bankImage.setImageResource(bankList[position].iconResId)

    }

    override fun getItemCount() = bankList.size

    inner class ViewHolder(val binding: RowBankBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val bankName = binding.textViewBank
        val bankImage = binding.imageViewBank

        init {
            binding.root.setOnClickListener {
                itemClickListener?.onItemClick(adapterPosition)

                true
            }
        }
    }
}
package com.project.jangburich.ui.group.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.jangburich.R
import com.project.jangburich.api.response.group.GetGroupResponse
import com.project.jangburich.api.response.group.TodayPayment
import com.project.jangburich.databinding.RowGroupBinding
import com.project.jangburich.databinding.RowPaymentBinding
import com.project.jangburich.ui.MainActivity

class PaymentAdapter(
    private var activity: MainActivity,
    private var paymentList: List<TodayPayment>
) :
    RecyclerView.Adapter<PaymentAdapter.ViewHolder>() {

    private var onItemClickListener: ((Int) -> Unit)? = null
    private var context: Context? = null
    private var selectedPosition: Int = 0

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClickListener = listener
    }

    fun updateList(
        newPaymentList: List<TodayPayment>
    ) {
        paymentList = newPaymentList
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int) {}
    }

    var itemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding =
            RowPaymentBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.menuName.text = paymentList[position].menuName
        holder.memberName.text = paymentList[position].consumeUserName
        holder.orderTime.text = paymentList[position].transactionTime
        holder.price.text = "${-paymentList[position].useAmount}원"

    }

    override fun getItemCount() = paymentList.size

    inner class ViewHolder(val binding: RowPaymentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val menuName = binding.textViewMenu
        val memberName = binding.textViewGroupMemberName
        val orderTime = binding.textViewTime
        val price = binding.textViewPrice
    }
}
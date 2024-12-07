package com.project.jangburich.ui.group.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.jangburich.api.response.group.StorePaymentHistory
import com.project.jangburich.api.response.group.TodayPayment
import com.project.jangburich.databinding.RowPaymentBinding
import com.project.jangburich.ui.MainActivity

class GroupStorePaymentAdapter (
    private var activity: MainActivity,
    private var paymentList: List<StorePaymentHistory>
) :
    RecyclerView.Adapter<GroupStorePaymentAdapter.ViewHolder>() {

    private var onItemClickListener: ((Int) -> Unit)? = null
    private var context: Context? = null
    private var selectedPosition: Int = 0

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClickListener = listener
    }

    fun updateList(
        newPaymentList: List<StorePaymentHistory>
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
        holder.memberName.text = paymentList[position].userName
        holder.orderTime.text = paymentList[position].paymentDate
        holder.price.text = "${-paymentList[position].price}원"

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
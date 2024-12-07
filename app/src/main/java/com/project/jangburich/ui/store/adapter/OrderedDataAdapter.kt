package com.project.jangburich.ui.store.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.jangburich.api.response.store.OrderedItem
import com.project.jangburich.databinding.RowOrderedBinding
import com.project.jangburich.ui.MainActivity

class OrderedDataAdapter(
    private var activity: MainActivity,
    private var orderedItems: List<OrderedItem>?
) :
    RecyclerView.Adapter<OrderedDataAdapter.ViewHolder>() {

    private var onItemClickListener: ((Int) -> Unit)? = null
    private var context: Context? = null
    private var selectedPosition: Int = 0

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClickListener = listener
    }

    fun updateList(newOrderedItem: List<OrderedItem>) {
        orderedItems = newOrderedItem
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int) {}
    }

    var itemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding =
            RowOrderedBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.menuName.text = orderedItems?.get(position)?.menuName
        holder.menuAmount.text = "수량 ${orderedItems?.get(position)?.quantity}개"
        holder.menuPrice.text = "${orderedItems?.get(position)?.price}원"
    }

    override fun getItemCount(): Int = orderedItems?.size!!

    inner class ViewHolder(val binding: RowOrderedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val menuName = binding.textViewMenuName
        val menuAmount = binding.textViewMenuAmount
        val menuPrice = binding.textViewMenuPrice

        init {
            binding.root.setOnClickListener {
                itemClickListener?.onItemClick(adapterPosition)

                // 클릭 리스너 호출
                onItemClickListener?.invoke(position)
            }
        }
    }
}
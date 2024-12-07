package com.project.jangburich.ui.store.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.jangburich.api.response.store.CartItem
import com.project.jangburich.api.response.store.StoreMenu
import com.project.jangburich.databinding.RowMenuBinding
import com.project.jangburich.databinding.RowOrderBinding
import com.project.jangburich.ui.MainActivity

class CartOrderAdapter (
    private var activity: MainActivity,
    private var cartItems: List<CartItem>
) :
    RecyclerView.Adapter<CartOrderAdapter.ViewHolder>() {

    private var onItemClickListener: ((Int) -> Unit)? = null
    private var context: Context? = null
    private var selectedPosition: Int = 0

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClickListener = listener
    }

    fun updateList(newCartItems: List<CartItem>) {
        cartItems = newCartItems
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int) {}
    }

    var itemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding =
            RowOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.menuName.text = cartItems[position].menuName
        holder.menuDescription.text = cartItems[position].menuDescription
        holder.menuPrice.text = "${cartItems[position].menuPrice}원"
        holder.quantity.text = "${cartItems[position].quantity}"
        Glide.with(activity).load(cartItems[position].menuImg).into(holder.menuImage)
    }

    override fun getItemCount() = cartItems.size

    inner class ViewHolder(val binding: RowOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val menuName = binding.textViewMenuName
        val menuDescription = binding.textViewMenuDescription
        val menuPrice = binding.textViewTotalPriceValue
        val menuImage = binding.imageViewCartOrderMenu
        val buttonDelete = binding.buttonDelete
        val buttonPlus = binding.buttonPlus
        val buttonMinus = binding.buttonMinus
        val quantity = binding.textViewCountNum

        init {
            binding.root.setOnClickListener {
                itemClickListener?.onItemClick(adapterPosition)

                // 클릭 리스너 호출
                onItemClickListener?.invoke(position)
            }

            binding.buttonPlus.setOnClickListener {

            }

            binding.buttonMinus.setOnClickListener {

            }

            binding.buttonDelete.setOnClickListener {

            }
        }
    }
}
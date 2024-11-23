package com.project.jangburich.ui.store.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.jangburich.api.response.store.Store
import com.project.jangburich.api.response.store.StoreMenu
import com.project.jangburich.databinding.RowMenuBinding
import com.project.jangburich.databinding.RowStoreListBinding
import com.project.jangburich.ui.MainActivity

class MenuAdapter (
    private var activity: MainActivity,
    private var menus: List<StoreMenu>
) :
    RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    private var onItemClickListener: ((Int) -> Unit)? = null
    private var context: Context? = null
    private var selectedPosition: Int = 0

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClickListener = listener
    }

    fun updateList(newMenus: List<StoreMenu>) {
        menus = newMenus
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int) {}
    }

    var itemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding =
            RowMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.menuName.text = menus[position].menuName
        holder.menuDescription.text = menus[position].description
        if(menus[position].isSignatureMenu == true) {
            holder.chipBest.visibility = View.VISIBLE
        } else {
            holder.chipBest.visibility = View.GONE
        }
        holder.menuPrice.text = "${menus[position].price}원"

        Glide.with(activity).load(menus[position].menuImgUrl).into(holder.menuImage)
    }

    override fun getItemCount() = menus.size

    inner class ViewHolder(val binding: RowMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val menuName = binding.textViewMenuName
        val menuDescription = binding.textViewMenuDescription
        val menuPrice = binding.textViewMenuPrice
        val menuImage = binding.imageView
        val chipBest = binding.chipBest

        init {
            binding.root.setOnClickListener {
                itemClickListener?.onItemClick(adapterPosition)

                // 클릭 리스너 호출
                onItemClickListener?.invoke(position)
            }
        }
    }
}
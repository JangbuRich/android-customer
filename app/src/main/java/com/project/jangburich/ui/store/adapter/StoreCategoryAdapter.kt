package com.project.jangburich.ui.store.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.jangburich.R
import com.project.jangburich.databinding.RowStoreCategoryBinding
import com.project.jangburich.ui.MainActivity

class StoreCategoryAdapter(
    private var activity: MainActivity,
    private var categoryList: List<String>
) : RecyclerView.Adapter<StoreCategoryAdapter.ViewHolder>() {

    private var onItemClickListener: ((Int) -> Unit)? = null
    private var context: Context? = null
    private var selectedPosition: Int = 0

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClickListener = listener
    }

    fun updateList(
        newNameList: List<String>
    ) {
        categoryList = newNameList
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int) {}
    }

    var itemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding =
            RowStoreCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // 배경 설정
        if (position == selectedPosition) {
            holder.categoryName.run {
                setTextColor(resources.getColor(R.color.gray_100))
            }
            holder.layout.setBackgroundResource(R.drawable.background_store_category_selected)
            holder.checkImage.visibility = View.VISIBLE
        } else {
            holder.categoryName.run {
                setTextColor(resources.getColor(R.color.gray_40))
            }
            holder.layout.setBackgroundResource(R.drawable.background_store_category_unselected)
            holder.checkImage.visibility = View.GONE
        }

        holder.categoryName.text = categoryList[position].toString()
    }

    override fun getItemCount() = categoryList.size

    inner class ViewHolder(val binding: RowStoreCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val categoryName = binding.textViewCategory
        val checkImage = binding.imageViewCategory
        val layout = binding.buttonCategory

        init {
            binding.root.setOnClickListener {
                itemClickListener?.onItemClick(adapterPosition)

                val previousPosition = selectedPosition
                selectedPosition = position

                // 이전 선택된 항목과 현재 선택된 항목을 갱신
                notifyItemChanged(previousPosition)
                notifyItemChanged(selectedPosition)
                true
            }
        }
    }
}
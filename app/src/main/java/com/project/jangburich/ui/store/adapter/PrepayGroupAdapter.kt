package com.project.jangburich.ui.store.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.jangburich.R
import com.project.jangburich.api.response.group.GetGroupResponse
import com.project.jangburich.api.response.store.Store
import com.project.jangburich.databinding.RowGroupBinding
import com.project.jangburich.databinding.RowPrepayGroupBinding
import com.project.jangburich.databinding.RowStoreListBinding
import com.project.jangburich.ui.MainActivity

class PrepayGroupAdapter (
    private var activity: MainActivity,
    private var groups: List<GetGroupResponse>
) :
    RecyclerView.Adapter<PrepayGroupAdapter.ViewHolder>() {

    private var onItemClickListener: ((Int) -> Unit)? = null
    private var context: Context? = null
    private var selectedPosition: Int = 0

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClickListener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int) {}
    }

    var itemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding =
            RowPrepayGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.groupName.text = groups[position].teamName
        holder.createdDate.text = groups[position].createdDate

        if (position == selectedPosition) {
            holder.layout.setBackgroundResource(R.drawable.background_prepay_group_selected)
            holder.checkBox.setImageResource(R.drawable.ic_check_circle_selected)
        } else {
            holder.layout.setBackgroundResource(R.drawable.background_gray95_round10)
            holder.checkBox.setImageResource(R.drawable.ic_check_circle_unselected)
        }

    }

    override fun getItemCount() = groups.size

    inner class ViewHolder(val binding: RowPrepayGroupBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val groupName = binding.textViewGroupName
        val createdDate = binding.textViewDate
        val layout = binding.layoutPrepayGroup
        val checkBox = binding.imageViewCheckbox


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
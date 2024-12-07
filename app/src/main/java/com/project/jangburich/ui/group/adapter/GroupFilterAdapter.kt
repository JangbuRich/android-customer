package com.project.jangburich.ui.group.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.jangburich.MyApplication
import com.project.jangburich.R
import com.project.jangburich.api.response.group.GetGroupResponse
import com.project.jangburich.databinding.RowGroupBinding
import com.project.jangburich.databinding.RowGroupFilterBinding
import com.project.jangburich.ui.MainActivity

class GroupFilterAdapter(
    private var activity: MainActivity,
    private var groupFilterList: List<String>
) :
    RecyclerView.Adapter<GroupFilterAdapter.ViewHolder>() {

    private var onItemClickListener: ((Int) -> Unit)? = null
    private var context: Context? = null
    private var selectedPosition: Int = 0

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClickListener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int) {}
    }

    fun updateSelectedPosition(position: Int) {
        val previousPosition = selectedPosition
        selectedPosition = position
        notifyItemChanged(previousPosition) // 이전 선택 상태 갱신
        notifyItemChanged(selectedPosition) // 현재 선택 상태 갱신
    }

    var itemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding =
            RowGroupFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.filter.run {
            text = MyApplication.groupFilterList[position]

            if(position == selectedPosition) {
                setBackgroundResource(R.drawable.background_group_selected)
                setTextColor(resources.getColor(R.color.gray_100))
            } else {
                setBackgroundResource(R.drawable.background_group_unselected)
                setTextColor(resources.getColor(R.color.gray_50))
            }
        }
    }

    override fun getItemCount() = groupFilterList.size

    inner class ViewHolder(val binding: RowGroupFilterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val filter = binding.buttonFilter

        init {
            binding.root.setOnClickListener {
                itemClickListener?.onItemClick(adapterPosition)

                true
            }
        }
    }
}
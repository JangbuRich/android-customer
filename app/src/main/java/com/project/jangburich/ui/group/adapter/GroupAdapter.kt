package com.project.jangburich.ui.group.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.jangburich.api.response.group.GetGroupResponse
import com.project.jangburich.databinding.RowBankBinding
import com.project.jangburich.databinding.RowGroupBinding
import com.project.jangburich.ui.MainActivity

class GroupAdapter(
    private var activity: MainActivity,
    private var groupList: List<GetGroupResponse>
) :
    RecyclerView.Adapter<GroupAdapter.ViewHolder>() {

    private var onItemClickListener: ((Int) -> Unit)? = null
    private var context: Context? = null
    private var selectedPosition: Int = 0

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClickListener = listener
    }

    fun updateList(
        newGroupList: List<GetGroupResponse>
    ) {
        groupList = newGroupList
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int) {}
    }

    var itemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding =
            RowGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.date.text = groupList[position].teamType
        holder.groupName.text = groupList[position].teamName
        holder.groupType.text = "#${groupList[position].createdDate}"
        if(groupList[position].isMeLeader) {
            holder.myGroup.visibility = View.VISIBLE
        } else {
            holder.myGroup.visibility = View.INVISIBLE
        }
    }

    override fun getItemCount() = groupList.size

    inner class ViewHolder(val binding: RowGroupBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val date = binding.textViewDate
        val groupName = binding.textViewGroupName
        val groupType = binding.textViewGroupType
        val myGroup = binding.imageViewMyGroup

        init {
            binding.root.setOnClickListener {
                itemClickListener?.onItemClick(adapterPosition)

                true
            }
        }
    }
}
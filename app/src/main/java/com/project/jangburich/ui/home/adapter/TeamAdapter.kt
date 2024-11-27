package com.project.jangburich.ui.home.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.jangburich.api.response.home.Team
import com.project.jangburich.databinding.RowHomeJangbuBinding
import com.project.jangburich.ui.MainActivity

class TeamAdapter(
    private var activity: MainActivity,
    private var teamList: List<Team>
) :
    RecyclerView.Adapter<TeamAdapter.ViewHolder>() {

    private var onItemClickListener: ((Int) -> Unit)? = null
    private var context: Context? = null
    private var selectedPosition: Int = 0

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClickListener = listener
    }

    fun updateList(
        newTeamList: List<Team>
    ) {
        teamList = newTeamList
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int) {}
    }

    var itemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding =
            RowHomeJangbuBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val constraintSet = ConstraintSet()
        constraintSet.clone(holder.layout) // ConstraintLayout의 현재 제약 조건 복사

        var layoutPercent = ((teamList[position].currentAmount.toDouble()) / (teamList[position].totalAmount))


        Log.d("##", "layoutPercent : ${layoutPercent}")

        // widthPercent 설정
        constraintSet.constrainPercentWidth(holder.layoutPoint.id, layoutPercent.toFloat())

        constraintSet.applyTo(holder.layout)

        holder.groupName.text = teamList[position].teamName
        holder.storeName.text = teamList[position].storeName
        holder.myPoint.text = "${teamList[position].currentAmount.toString()}원"
        holder.totalPoint.text = "/ ${teamList[position].totalAmount.toString()}원"

        Glide.with(activity).load(teamList[position].storeImgUrl).into(holder.storeImage)

    }

    override fun getItemCount() = teamList.size

    inner class ViewHolder(val binding: RowHomeJangbuBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val groupName = binding.textViewGroupName
        val storeName = binding.textViewStoreName
        val storeImage = binding.profileImage
        val myPoint= binding.textViewMyPoint
        val layoutPoint = binding.progressIndicatorSelected
        val totalPoint= binding.textViewTotalPoint
        val layout = binding.layoutHomeJangbu

        init {
            binding.root.setOnClickListener {
                itemClickListener?.onItemClick(adapterPosition)

                true
            }
        }
    }
}
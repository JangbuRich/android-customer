package com.project.jangburich.ui.group.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.jangburich.R
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

        holder.date.text = groupList[position].createdDate
        holder.groupName.text = groupList[position].teamName
        holder.groupType.text = "#${groupList[position].teamType}"
        if(groupList[position].isMeLeader) {
            holder.myGroup.visibility = View.VISIBLE
            holder.groupLeaderBadge.visibility = View.VISIBLE
        } else {
            holder.myGroup.visibility = View.INVISIBLE
            holder.groupLeaderBadge.visibility = View.GONE
        }

        if(groupList[position].isLiked) {
            holder.buttonLike.setImageResource(R.drawable.ic_star_selected)
        } else {
            holder.buttonLike.setImageResource(R.drawable.ic_star_unselected)
        }

        // 최대 표시할 이미지 개수
        val maxImages = 3

        // 프로필 이미지 리스트 가져오기
        val profileImages = groupList[position].profileImageUrl ?: listOf()

        // 프로필 이미지들 처리
        for (i in 0 until maxImages) {
            val imageView = when (i) {
                0 -> holder.profileImage1
                1 -> holder.profileImage2
                2 -> holder.profileImage3
                else -> null
            }

            imageView?.let {
                if (i < profileImages.size && !profileImages[i].isNullOrEmpty()) {
                    // 이미지가 존재하면 Glide로 로드
                    Glide.with(activity)
                        .load(profileImages[i])
                        .into(it)
                    it.visibility = View.VISIBLE // 이미지를 보여줌
                } else {
                    // 이미지가 없거나 크기를 초과한 경우 기본 이미지 숨김
                    it.setImageResource(R.drawable.img_profile_default)
                    it.visibility = if (i < profileImages.size) View.VISIBLE else View.GONE
                }
            }
        }



        if(groupList[position].peopleCount > 3) {
            holder.groupMemberNum.visibility = View.VISIBLE
            holder.groupMemberNum.text = "+${groupList[position].peopleCount - 3}"
        } else {
            holder.groupMemberNum.visibility = View.GONE
        }
    }

    override fun getItemCount() = groupList.size

    inner class ViewHolder(val binding: RowGroupBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val date = binding.textViewDate
        val groupName = binding.textViewGroupName
        val groupType = binding.textViewGroupType
        val myGroup = binding.imageViewMyGroup
        val groupLeaderBadge = binding.imageViewLeader
        val buttonLike = binding.buttonStar
        val profileImage1 = binding.imageViewGroupMemberProfile1
        val profileImage2 = binding.imageViewGroupMemberProfile2
        val profileImage3 = binding.imageViewGroupMemberProfile3
        val groupMemberNum = binding.textViewGroupMemberNum

        init {
            binding.root.setOnClickListener {
                itemClickListener?.onItemClick(adapterPosition)

                true
            }
        }
    }
}
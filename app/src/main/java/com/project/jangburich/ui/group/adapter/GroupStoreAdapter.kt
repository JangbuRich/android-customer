package com.project.jangburich.ui.group.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.jangburich.R
import com.project.jangburich.api.response.group.PrepayedStore
import com.project.jangburich.api.response.group.TodayPayment
import com.project.jangburich.databinding.RowGroupStoreBinding
import com.project.jangburich.databinding.RowPaymentBinding
import com.project.jangburich.ui.MainActivity

class GroupStoreAdapter(
    private var activity: MainActivity,
    private var storeList: List<PrepayedStore>
) :
    RecyclerView.Adapter<GroupStoreAdapter.ViewHolder>() {

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
            RowGroupStoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.storeName.text = storeList[position].storeName
        holder.storeAddress.text = storeList[position].address

        if(storeList[position].isLiked) {
            holder.buttonLike.setImageResource(R.drawable.ic_star_selected)
        } else {
            holder.buttonLike.setImageResource(R.drawable.ic_star_unselected)
        }

        Glide.with(activity)
            .load(storeList[position].storeImgUrl)
            .into(holder.storeImage)
    }

    override fun getItemCount() = storeList.size

    inner class ViewHolder(val binding: RowGroupStoreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val storeName = binding.textViewStoreName
        val storeAddress = binding.textViewStoreAddress
        val storeImage = binding.imageViewStore
        val buttonLike = binding.buttonLike

        init {
            binding.root.setOnClickListener {
                itemClickListener?.onItemClick(adapterPosition)

                true
            }
        }
    }
}
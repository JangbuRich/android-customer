package com.project.jangburich.ui.group

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.project.jangburich.R
import com.project.jangburich.api.response.group.GetGroupResponse
import com.project.jangburich.databinding.FragmentGroupBinding
import com.project.jangburich.ui.MainActivity
import com.project.jangburich.ui.group.adapter.GroupAdapter
import com.project.jangburich.ui.group.viewModel.GroupViewModel
import com.project.jangburich.ui.home.adapter.TeamAdapter
import com.project.jangburich.ui.home.viewModel.HomeViewModel

class GroupFragment : Fragment() {

    lateinit var binding: FragmentGroupBinding
    lateinit var mainActivity: MainActivity
    lateinit var viewModel: GroupViewModel

    private lateinit var groupAdapter: GroupAdapter

    var getGroupList = mutableListOf<GetGroupResponse>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentGroupBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity
        viewModel = ViewModelProvider(mainActivity)[GroupViewModel::class.java]

        viewModel.getGroupList(mainActivity)
        mainActivity.hideBottomNavigation(false)

        initAdapters()

        viewModel.run {
            groupList.observe(mainActivity) {
                getGroupList = it
                groupAdapter.updateList(getGroupList)
            }
        }

        mainActivity.hideBottomNavigation(false)

        binding.run {

        }

        return binding.root
    }

    private fun initAdapters() {
        // 어댑터 초기화
        groupAdapter = GroupAdapter(
            mainActivity,
            getGroupList
        ).apply {
            itemClickListener = object : GroupAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {

                }
            }
        }

        binding.recyclerViewGroup.apply {
            adapter = groupAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }
}
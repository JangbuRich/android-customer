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
import com.project.jangburich.MyApplication
import com.project.jangburich.R
import com.project.jangburich.api.response.group.GetGroupResponse
import com.project.jangburich.databinding.FragmentGroupBinding
import com.project.jangburich.ui.MainActivity
import com.project.jangburich.ui.group.adapter.GroupAdapter
import com.project.jangburich.ui.group.adapter.GroupFilterAdapter
import com.project.jangburich.ui.group.viewModel.GroupViewModel
import com.project.jangburich.ui.home.adapter.TeamAdapter
import com.project.jangburich.ui.home.viewModel.HomeViewModel

class GroupFragment : Fragment() {

    lateinit var binding: FragmentGroupBinding
    lateinit var mainActivity: MainActivity
    lateinit var viewModel: GroupViewModel

    private lateinit var groupFilterAdapter: GroupFilterAdapter
    private lateinit var groupAdapter: GroupAdapter

    var getGroupList = mutableListOf<GetGroupResponse>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentGroupBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity
        viewModel = ViewModelProvider(mainActivity)[GroupViewModel::class.java]

        mainActivity.hideBottomNavigation(false)

        viewModel.getGroupList(mainActivity, "ALL")

        initAdapters()
        observeViewModel()

        return binding.root
    }

    private fun initAdapters() {
        // 어댑터 초기화
        groupFilterAdapter = GroupFilterAdapter(
            mainActivity,
            MyApplication.groupFilterList
        ).apply {
            itemClickListener = object : GroupFilterAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    when(position) {
                        0 -> {
                            viewModel.getGroupList(mainActivity, "ALL")
                            MyApplication.groupFilterCategory = "ALL"
                        }
                        1 -> {
                            viewModel.getGroupList(mainActivity, "LEADER")
                            MyApplication.groupFilterCategory = "LEADER"
                        }
                        2 -> {
                            viewModel.getGroupList(mainActivity, "MEMBER")
                            MyApplication.groupFilterCategory = "MEMBER"
                        }
                    }
                    updateSelectedPosition(position) // 어댑터에 선택 상태 전달
                }
            }
        }

        binding.recyclerViewGroupFilter.apply {
            adapter = groupFilterAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        }

        groupAdapter = GroupAdapter(
            mainActivity,
            getGroupList
        ).apply {
            itemClickListener = object : GroupAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    MyApplication.selectedTeamId = getGroupList[position].teamId
                    viewModel.getGroupDetail(mainActivity)
                }
            }
        }

        binding.recyclerViewGroup.apply {
            adapter = groupAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }

    private fun observeViewModel() {
        viewModel.run {
            groupList.observe(viewLifecycleOwner) {
                getGroupList = it

                groupAdapter.updateList(getGroupList)
            }
        }
    }
}
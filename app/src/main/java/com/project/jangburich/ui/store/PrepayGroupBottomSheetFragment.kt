package com.project.jangburich.ui.store

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.project.jangburich.MyApplication
import com.project.jangburich.R
import com.project.jangburich.api.response.group.GetGroupResponse
import com.project.jangburich.databinding.FragmentPrepayGroupBottomSheetBinding
import com.project.jangburich.databinding.FragmentStoreMapBottomSheetBinding
import com.project.jangburich.ui.MainActivity
import com.project.jangburich.ui.group.viewModel.GroupViewModel
import com.project.jangburich.ui.store.adapter.PrepayGroupAdapter
import com.project.jangburich.ui.store.adapter.StoreListAdapter
import com.project.jangburich.ui.store.viewModel.StoreViewModel

interface PrepayGroupBottomSheetListener {
    fun onGroupButtonClicked()
}
class PrepayGroupBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var listener: PrepayGroupBottomSheetListener
    lateinit var binding: FragmentPrepayGroupBottomSheetBinding
    lateinit var mainActivity: MainActivity
    lateinit var viewModel: GroupViewModel

    private lateinit var prepayGroupAdapter: PrepayGroupAdapter
    private var getGroupList = mutableListOf<GetGroupResponse>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = parentFragment as PrepayGroupBottomSheetListener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPrepayGroupBottomSheetBinding.inflate(inflater, container, false)
        mainActivity = activity as MainActivity
        viewModel = ViewModelProvider(mainActivity)[GroupViewModel::class.java]

//        initAdapters()
        observeViewModel()

        viewModel.getGroupList(mainActivity, "LEADER")

        binding.run {
            buttonNext.setOnClickListener { onItemClicked() }
        }

        return binding.root
    }

    private fun observeViewModel() {
        viewModel.run {
            groupList.observe(viewLifecycleOwner) {
                getGroupList = it
                initAdapters()

                if(getGroupList.size != 0) {
                    MyApplication.prepaymentGroupId = getGroupList[0].teamId
                }
            }
        }
    }

    private fun initAdapters() {
        // 어댑터 초기화
        prepayGroupAdapter = PrepayGroupAdapter(
            mainActivity,
            getGroupList
        ).apply {
            itemClickListener = object : PrepayGroupAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    MyApplication.prepaymentGroupId = getGroupList[position].teamId
                }
            }
        }

        binding.recyclerViewPrepayGroup.apply {
            adapter = prepayGroupAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }

    private fun onItemClicked() {
        listener.onGroupButtonClicked()

        dismiss()
    }
}
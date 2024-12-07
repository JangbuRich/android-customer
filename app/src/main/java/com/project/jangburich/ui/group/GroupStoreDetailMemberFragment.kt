package com.project.jangburich.ui.group

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.jangburich.MyApplication
import com.project.jangburich.R
import com.project.jangburich.databinding.FragmentGroupStoreDetailLeaderBinding
import com.project.jangburich.databinding.FragmentGroupStoreDetailMemberBinding
import com.project.jangburich.ui.MainActivity
import com.project.jangburich.ui.group.adapter.GroupStorePaymentAdapter
import com.project.jangburich.ui.group.viewModel.GroupViewModel
import com.project.jangburich.ui.store.StoreDetailOrderFragment

class GroupStoreDetailMemberFragment : Fragment() {

    lateinit var binding: FragmentGroupStoreDetailMemberBinding
    lateinit var mainActivity: MainActivity
    lateinit var viewModel: GroupViewModel

    lateinit var paymentAdapter: GroupStorePaymentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentGroupStoreDetailMemberBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity
        viewModel = ViewModelProvider(mainActivity)[GroupViewModel::class.java]

        initView()
        initAdapter()

        binding.run {
            buttonStoreDetail.setOnClickListener {
                val nextFragment = StoreDetailOrderFragment()

                val transaction = mainActivity.manager.beginTransaction()
                transaction.replace(R.id.fragmentContainerView_main, nextFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }

            textViewStoreName.text = MyApplication.selectedGroupStoreDetail.storeName
            textViewGroupRemainPaymentValue.text = "${MyApplication.selectedGroupStoreDetail.remainingAmount}원"
            textViewGroupPersonalAllocatedValue.text = "${MyApplication.selectedGroupStoreDetail.availableAmount}원"
            textViewMyPaymentValue.text = "${MyApplication.selectedGroupStoreDetail.myUsedAmount}"
            textViewDateValue.text = "${MyApplication.selectedGroupStoreDetail.usageStartDate} ~ ${MyApplication.selectedGroupStoreDetail.usageEndDate}"
        }

        return binding.root
    }

    fun initAdapter() {
        paymentAdapter = GroupStorePaymentAdapter(
            mainActivity,
            MyApplication.selectedGroupStoreDetail.myPaymentHistories
        )

        binding.recyclerViewPayment.apply {
            adapter = paymentAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }

    fun initView() {
        binding.run {
            toolbar.run {
                buttonBack.setOnClickListener {
                    fragmentManager?.popBackStack()
                }
                buttonCharge.setOnClickListener {
                    viewModel.getPrepayData(mainActivity, MyApplication.selectedGroupStoreId, MyApplication.selectedTeamId)
                }
            }
        }
    }

}
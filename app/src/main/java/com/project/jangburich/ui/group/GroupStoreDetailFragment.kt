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
import com.project.jangburich.databinding.FragmentGroupStoreDetailBinding
import com.project.jangburich.ui.MainActivity
import com.project.jangburich.ui.group.adapter.GroupAdapter
import com.project.jangburich.ui.group.adapter.GroupStorePaymentAdapter
import com.project.jangburich.ui.group.viewModel.GroupViewModel
import com.project.jangburich.ui.store.StoreDetailOrderFragment

class GroupStoreDetailFragment : Fragment() {

    lateinit var binding: FragmentGroupStoreDetailBinding
    lateinit var mainActivity: MainActivity
    lateinit var viewModel: GroupViewModel

    lateinit var paymentAdapter: GroupStorePaymentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentGroupStoreDetailBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity
        viewModel = ViewModelProvider(mainActivity)[GroupViewModel::class.java]

        initView()
        initAdapter()

        binding.run {
            buttonStoreDetail.setOnClickListener {
            }

            textViewStoreName.text = MyApplication.selectedGroupStoreDetail.storeName
            textViewGroupTotalPaymentValue.text = "${MyApplication.selectedGroupStoreDetail.totalPrepayedAmount}원"
            textViewGroupRemainPaymentValue.text = "${MyApplication.selectedGroupStoreDetail.remainingPrepayedAmount}원"
            textViewPersonalAllocatedValue.text = "${MyApplication.selectedGroupStoreDetail.personalUsableAmount}원"
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
                }
            }
        }
    }

}
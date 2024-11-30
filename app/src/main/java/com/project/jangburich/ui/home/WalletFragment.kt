package com.project.jangburich.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.jangburich.MyApplication
import com.project.jangburich.api.response.home.PurchaseHistory
import com.project.jangburich.databinding.FragmentWalletBinding
import com.project.jangburich.ui.MainActivity
import com.project.jangburich.ui.home.adapter.WalletAdapter
import com.project.jangburich.ui.home.viewModel.WalletViewModel

class WalletFragment : Fragment() {

    lateinit var binding: FragmentWalletBinding
    lateinit var mainActivity: MainActivity
    lateinit var viewModel: WalletViewModel
    lateinit var walletAdapter: WalletAdapter

    var getPaymentHistory = mutableListOf<PurchaseHistory>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentWalletBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity
        viewModel = ViewModelProvider(mainActivity)[WalletViewModel::class.java]

        initAdapter()
        observeViewModel()

        binding.run {
            buttonCharge.setOnClickListener {
            }
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        initView()
    }

    fun initAdapter() {
        walletAdapter = WalletAdapter(mainActivity, getPaymentHistory)

        binding.recyclerViewPayment.apply {
            adapter = walletAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }

    fun observeViewModel() {
        viewModel.run {
            userPoint.observe(mainActivity) {
                binding.textViewUserPoint.text = "${it}원"
            }

            purchaseHistoryList.observe(mainActivity) {
                getPaymentHistory = it
                walletAdapter.updateList(getPaymentHistory)
            }
        }
    }

    fun initView() {
        viewModel.getWalletData(mainActivity)

        binding.run {
            textViewUserName.text = "${MyApplication.userName}님의 보유 금액"

            toolbar.run {
                textViewTitle.text = "나의 지갑"
                buttonBack.setOnClickListener {
                    fragmentManager?.popBackStack()
                }
            }
        }
    }
}
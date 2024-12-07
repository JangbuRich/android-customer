package com.project.jangburich.ui.wallet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.project.jangburich.MyApplication
import com.project.jangburich.MyApplication.Companion.isKakaoPayComplete
import com.project.jangburich.R
import com.project.jangburich.databinding.FragmentChargeBinding
import com.project.jangburich.ui.MainActivity
import com.project.jangburich.ui.wallet.viewModel.WalletViewModel

class ChargeFragment : Fragment() {

    lateinit var binding: FragmentChargeBinding
    lateinit var mainActivity: MainActivity
    lateinit var viewModel: WalletViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentChargeBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity
        viewModel = ViewModelProvider(mainActivity)[WalletViewModel::class.java]

        binding.run {
            buttonCharge.setOnClickListener {
                MyApplication.preChargeAmount += editTextChargePrice.text.toString().toInt()
                viewModel.readyKakaoPay(mainActivity, editTextChargePrice.text.toString())
            }
            textViewChargeTotalPriceValue.text = "${MyApplication.preChargeAmount}원"

            editTextChargePrice.addTextChangedListener {
                textViewChargeTotalPriceValue.text = "${MyApplication.preChargeAmount + editTextChargePrice.text.toString().toInt()}원"
            }
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        initView()
        if(isKakaoPayComplete) {
            val nextFragment = ChargeCompleteFragment()

            val transaction = mainActivity.manager.beginTransaction()
            transaction.replace(R.id.fragmentContainerView_main, nextFragment)
            transaction.commit()
        }
    }

    fun initView() {
        mainActivity.hideBottomNavigation(true)
        binding.run {
            toolbar.run {
                textViewTitle.text = "금액 충전"

                buttonBack.setOnClickListener {
                    fragmentManager?.popBackStack()
                }
            }
        }
    }
}
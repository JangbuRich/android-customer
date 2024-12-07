package com.project.jangburich.ui.wallet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.project.jangburich.MyApplication
import com.project.jangburich.R
import com.project.jangburich.databinding.FragmentChargeCompleteBinding
import com.project.jangburich.ui.MainActivity
import com.project.jangburich.ui.store.OrderCompleteFragment
import kotlin.concurrent.fixedRateTimer

class ChargeCompleteFragment : Fragment() {

    lateinit var binding: FragmentChargeCompleteBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentChargeCompleteBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        mainActivity.hideBottomNavigation(true)

        binding.run {
            textViewChargeTotalPriceValue.text = MyApplication.preChargeAmount.toString()

            buttonNext.setOnClickListener {
                fragmentManager?.popBackStack()
            }
        }

        return binding.root
    }

}
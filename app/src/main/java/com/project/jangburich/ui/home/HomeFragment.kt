package com.project.jangburich.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.jangburich.R
import com.project.jangburich.databinding.FragmentHomeBinding
import com.project.jangburich.ui.MainActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HomeFragment : Fragment(), HomeGroupBottomSheetListener {

    lateinit var binding: FragmentHomeBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        mainActivity.hideBottomNavigation(false)

        initView()

        binding.run {
            buttonMap.setOnClickListener { mainActivity.binding.bottomNavigation.selectedItemId = R.id.menu_store }
            buttonGroup.setOnClickListener { mainActivity.binding.bottomNavigation.selectedItemId = R.id.menu_group }
            buttonReserve.setOnClickListener { mainActivity.binding.bottomNavigation.selectedItemId = R.id.menu_reserve }


        }

        return binding.root
    }

    fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
        return dateFormat.format(Date())
    }

    fun initView() {
        binding.run {
            toolbar.run {
                buttonWallet.setOnClickListener {
                    // 나의 지갑 화면
                }
            }
        }
    }

    override fun onButtonClicked() {
    }
}
package com.project.jangburich.ui.store

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.jangburich.R
import com.project.jangburich.databinding.FragmentStoreMenuDetailBinding
import com.project.jangburich.ui.MainActivity


class StoreMenuDetailFragment : Fragment(), StoreMenuBuyBottomSheetListener {

    lateinit var binding: FragmentStoreMenuDetailBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentStoreMenuDetailBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        initView()

        binding.run {

        }

        return binding.root
    }

    fun initView() {
        binding.run {
            toolbar.run {
                buttonBack.setOnClickListener {

                }
            }
        }
    }

    override fun onButtonClicked(position: Int) {
        if(position == 0) {
            fragmentManager?.popBackStack()
        } else {
            val nextFragment = OrderFragment()

            val transaction = mainActivity.manager.beginTransaction()
            transaction.replace(R.id.fragmentContainerView_main, nextFragment)
            transaction.addToBackStack("")
            transaction.commit()
        }
    }
}
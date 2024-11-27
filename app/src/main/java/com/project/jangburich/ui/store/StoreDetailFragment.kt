package com.project.jangburich.ui.store

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.jangburich.MainUtil
import com.project.jangburich.MainUtil.setStatusBarTransparent
import com.project.jangburich.R
import com.project.jangburich.databinding.FragmentStoreDetailBinding
import com.project.jangburich.ui.MainActivity

class StoreDetailFragment : Fragment() {

    lateinit var binding: FragmentStoreDetailBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentStoreDetailBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        initView()

        binding.run {
            buttonPayBefore.setOnClickListener {

            }
            buttonReserve.setOnClickListener {
                
            }
        }

        return binding.root
    }

    fun initView() {
        mainActivity.setStatusBarTransparent()
    }
}
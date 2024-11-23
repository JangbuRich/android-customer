package com.project.jangburich.ui.store

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.jangburich.R
import com.project.jangburich.databinding.FragmentStoreMenuDetailBinding


class StoreMenuDetailFragment : Fragment() {

    lateinit var binding: FragmentStoreMenuDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentStoreMenuDetailBinding.inflate(layoutInflater)

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
}
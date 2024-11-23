package com.project.jangburich.ui.store

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.jangburich.R
import com.project.jangburich.databinding.FragmentOrderCompleteBinding

class OrderCompleteFragment : Fragment() {

    lateinit var binding: FragmentOrderCompleteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentOrderCompleteBinding.inflate(layoutInflater)

        initView()

        return binding.root
    }

    fun initView() {
        binding.run {
            toolbar.run {
                buttonBack.setOnClickListener {
                    fragmentManager?.popBackStack()
                }
            }
        }
    }
}
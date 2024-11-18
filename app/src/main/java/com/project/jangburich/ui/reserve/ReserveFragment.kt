package com.project.jangburich.ui.reserve

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.jangburich.R
import com.project.jangburich.databinding.FragmentReserveBinding

class ReserveFragment : Fragment() {

    lateinit var binding: FragmentReserveBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentReserveBinding.inflate(layoutInflater)

        return binding.root
    }

}
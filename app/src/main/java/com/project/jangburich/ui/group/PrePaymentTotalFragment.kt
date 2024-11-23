package com.project.jangburich.ui.group

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.jangburich.R
import com.project.jangburich.databinding.FragmentPrePaymentTotalBinding

class PrePaymentTotalFragment : Fragment() {

    lateinit var binding: FragmentPrePaymentTotalBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPrePaymentTotalBinding.inflate(layoutInflater)

        intiView()

        binding.run {

        }

        return binding.root
    }

    fun intiView() {
        binding.run {
            toolbar.run {
                buttonBack.setOnClickListener {
                   fragmentManager?.popBackStack()
                }
                textViewTitle.text = "선결제"
            }
        }
    }

}
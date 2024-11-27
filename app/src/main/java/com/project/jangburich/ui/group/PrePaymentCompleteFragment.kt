package com.project.jangburich.ui.group

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.jangburich.R
import com.project.jangburich.databinding.FragmentPrePaymentCompleteBinding
import com.project.jangburich.ui.MainActivity

class PrePaymentCompleteFragment : Fragment() {

    lateinit var binding: FragmentPrePaymentCompleteBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPrePaymentCompleteBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        binding.run {
            buttonAccept.setOnClickListener {
                mainActivity.binding.bottomNavigation.selectedItemId = R.id.menu_home
            }
            buttonMyGroup.setOnClickListener {
                mainActivity.binding.bottomNavigation.selectedItemId = R.id.menu_group
            }
        }

        return binding.root
    }
}
package com.project.jangburich.ui.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.jangburich.R
import com.project.jangburich.databinding.FragmentOnboarding1Binding
import com.project.jangburich.ui.MainActivity
import com.project.jangburich.ui.login.LoginMainFragment


class Onboarding1Fragment : Fragment() {

    lateinit var binding: FragmentOnboarding1Binding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentOnboarding1Binding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        mainActivity.hideBottomNavigation(true)

        binding.run {
            buttonNext.setOnClickListener {
                val nextFragment = Onboarding2Fragment()

                val transaction = mainActivity.manager.beginTransaction()
                transaction.replace(R.id.fragmentContainerView_main, nextFragment)
                transaction.addToBackStack("")
                transaction.commit()
            }

            buttonSkip.setOnClickListener {
                val nextFragment = LoginMainFragment()

                val transaction = mainActivity.manager.beginTransaction()
                transaction.replace(R.id.fragmentContainerView_main, nextFragment)
                transaction.addToBackStack("")
                transaction.commit()
            }
        }

        return binding.root
    }
}
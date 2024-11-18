package com.project.jangburich.ui.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.jangburich.R
import com.project.jangburich.databinding.FragmentOnboarding3Binding
import com.project.jangburich.ui.MainActivity
import com.project.jangburich.ui.login.LoginMainFragment

class Onboarding3Fragment : Fragment() {
    lateinit var binding: FragmentOnboarding3Binding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentOnboarding3Binding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        mainActivity.hideBottomNavigation(true)

        binding.run {
            buttonNext.setOnClickListener {
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
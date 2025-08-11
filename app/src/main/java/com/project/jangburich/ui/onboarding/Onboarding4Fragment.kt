package com.project.jangburich.ui.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.jangburich.R
import com.project.jangburich.databinding.FragmentOnboarding4Binding
import com.project.jangburich.ui.MainActivity
import com.project.jangburich.ui.login.LoginMainFragment

class Onboarding4Fragment : Fragment() {
    lateinit var binding: FragmentOnboarding4Binding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentOnboarding4Binding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        mainActivity.hideBottomNavigation(true)

        binding.run {
            buttonNext.setOnClickListener {
                mainActivity.supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView_main, LoginMainFragment())
                    .setReorderingAllowed(true)
                    .commit()
            }
        }

        return binding.root
    }
}
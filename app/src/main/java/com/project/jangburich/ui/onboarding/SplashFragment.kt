package com.project.jangburich.ui.onboarding

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.project.jangburich.R
import com.project.jangburich.databinding.FragmentSplashBinding
import com.project.jangburich.ui.MainActivity
import com.project.jangburich.ui.login.LoginMainFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment() {

    lateinit var binding: FragmentSplashBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSplashBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        mainActivity.hideBottomNavigation(true)

        viewLifecycleOwner.lifecycleScope.launch {
            delay(1000)
            if (!isAdded) return@launch
            mainActivity.supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView_main, Onboarding1Fragment())
                .commit()
        }

        return binding.root
    }

}
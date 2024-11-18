package com.project.jangburich.ui.onboarding

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.jangburich.R
import com.project.jangburich.databinding.FragmentSplashBinding
import com.project.jangburich.ui.MainActivity

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

        Handler().postDelayed({
            val nextFragment = Onboarding1Fragment()

            val transaction = mainActivity.manager.beginTransaction()
            transaction.replace(R.id.fragmentContainerView_main, nextFragment)
            transaction.commit()
        }, 1000)

        return binding.root
    }

}
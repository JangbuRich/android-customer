package com.project.jangburich.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.jangburich.R
import com.project.jangburich.databinding.FragmentLoginMainBinding
import com.project.jangburich.ui.MainActivity


class LoginMainFragment : Fragment() {

    lateinit var binding: FragmentLoginMainBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLoginMainBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        mainActivity.hideBottomNavigation(true)

        binding.run {
            buttonKakao.setOnClickListener {
                // 카카오 로그인
            }
        }

        return binding.root
    }

}
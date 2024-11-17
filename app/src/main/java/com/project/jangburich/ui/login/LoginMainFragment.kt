package com.project.jangburich.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.jangburich.R
import com.project.jangburich.databinding.FragmentLoginMainBinding


class LoginMainFragment : Fragment() {

    lateinit var binding: FragmentLoginMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLoginMainBinding.inflate(layoutInflater)

        binding.run {
            buttonKakao.setOnClickListener {
                // 카카오 로그인
            }
        }

        return binding.root
    }

}
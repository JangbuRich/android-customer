package com.project.jangburich.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.project.jangburich.R
import com.project.jangburich.databinding.FragmentSignUpUserInfoBinding

class SignUpUserInfoFragment : Fragment() {

    lateinit var binding: FragmentSignUpUserInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSignUpUserInfoBinding.inflate(layoutInflater)

        initView()

        binding.run {
            buttonNext.setOnClickListener {

            }

            editTextName.addTextChangedListener {
                checkEnable()
            }

            editTextPhoneNumber.addTextChangedListener {
                checkEnable()
            }
        }

        return binding.root
    }

    fun checkEnable() {
        binding.run {
            if(editTextName.text.isNotEmpty() && editTextPhoneNumber.text.isNotEmpty()) {
                buttonNext.isEnabled = true
            } else {
                buttonNext.isEnabled = false
            }
        }
    }

    fun initView() {
        binding.run {
            toolbar.run {
                buttonBack.setOnClickListener {
                    fragmentManager?.popBackStack()
                }
            }
        }
    }
}
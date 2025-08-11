package com.project.jangburich.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.project.jangburich.MyApplication
import com.project.jangburich.R
import com.project.jangburich.databinding.FragmentSignUpUserInfoBinding
import com.project.jangburich.ui.MainActivity

class SignUpUserInfoFragment : Fragment() {

    lateinit var binding: FragmentSignUpUserInfoBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSignUpUserInfoBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        initView()

        binding.run {
            buttonNext.setOnClickListener {
                val bundle = Bundle().apply {
                    putString("name", editTextName.text.toString().trim())
                    putString("phone", editTextPhoneNumber.text.toString().trim())
                }

                var nextFragment = SignUpAgreementFragment().apply {
                    arguments = bundle
                }

                mainActivity.supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView_main, nextFragment)
                    .addToBackStack(null)
                    .commit()
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
        mainActivity.hideBottomNavigation(true)

        binding.run {
            toolbar.run {
                buttonBack.setOnClickListener {
                    fragmentManager?.popBackStack()
                }
            }
        }
    }
}
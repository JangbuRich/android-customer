package com.project.jangburich.ui.group

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.project.jangburich.MyApplication
import com.project.jangburich.R
import com.project.jangburich.databinding.FragmentCreateGroupInfoBinding
import com.project.jangburich.ui.MainActivity

class CreateGroupInfoFragment : Fragment() {

    lateinit var binding: FragmentCreateGroupInfoBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCreateGroupInfoBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        initView()

        binding.run {

            editTextName.addTextChangedListener {
                checkEnable()
                textViewNameCount.text = "${editTextName.length()}/15"
            }
            editTextDescription.addTextChangedListener {
                checkEnable()
                textViewDescriptionCount.text = "${editTextDescription.length()}/100"
            }

            buttonNext.setOnClickListener {
                MyApplication.groupName = editTextName.text.toString()
                MyApplication.groupDescription = editTextDescription.text.toString()

                val nextFragment = CreateGroupAccountFragment()

                val transaction = mainActivity.manager.beginTransaction()
                transaction.replace(R.id.fragmentContainerView_main, nextFragment)
                transaction.addToBackStack("")
                transaction.commit()
            }

        }

        return binding.root
    }

    fun checkEnable() {
        binding.run {
            if(editTextName.text.isNotEmpty() && editTextDescription.text.isNotEmpty()) {
                buttonNext.isEnabled = true
            } else {
                buttonNext.isEnabled = false
            }
        }
    }

    fun isValidInput(input: String): Boolean {
        // 정규식: 영문, 숫자 포함 5~10자
        val regex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{5,10}$".toRegex()

        return regex.matches(input)
    }

    fun initView() {
        binding.run {
            buttonNext.isEnabled = false

            mainActivity.hideBottomNavigation(true)

            toolbar.run {
                buttonBack.setOnClickListener {
                    fragmentManager?.popBackStack()
                }
            }
        }
    }
}
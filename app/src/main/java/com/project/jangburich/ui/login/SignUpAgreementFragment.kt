package com.project.jangburich.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.project.jangburich.R
import com.project.jangburich.databinding.FragmentSignUpAgreementBinding
import com.project.jangburich.ui.MainActivity

class SignUpAgreementFragment : Fragment() {

    lateinit var binding: FragmentSignUpAgreementBinding
    lateinit var mainActivity: MainActivity

    val isAgree = MutableList(6) { false }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSignUpAgreementBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        initView()

        binding.run {
            buttonAgreement1Open.setOnClickListener {

            }
            buttonAgreement2Open.setOnClickListener {

            }
            buttonAgreement3Open.setOnClickListener {

            }
            buttonAgreement4Open.setOnClickListener {

            }
            buttonAgreement5Open.setOnClickListener {

            }

            checkboxAgreementAll.setOnClickListener {checkAgreementAll()}
            checkboxAgreement1.setOnClickListener {checkAgree(1, checkboxAgreement1)}
            checkboxAgreement2.setOnClickListener {checkAgree(2, checkboxAgreement2)}
            checkboxAgreement3.setOnClickListener {checkAgree(3, checkboxAgreement3)}
            checkboxAgreement4.setOnClickListener {checkAgree(4, checkboxAgreement4)}
            checkboxAgreement5.setOnClickListener {checkAgree(5, checkboxAgreement5)}
        }

        return binding.root
    }

    fun checkAgreementAll() {
        isAgree[0] = !isAgree[0]
        binding.run {
            if(isAgree[0]) {
                checkboxAgreementAll.setImageResource(R.drawable.ic_check_circle_selected)
                isAgree.fill(true)
                checkAgreementAllWithOthers()
            } else {
                checkboxAgreementAll.setImageResource(R.drawable.ic_check_circle_unselected)
                isAgree.fill(false)
                checkAgreementAllWithOthers()
            }
        }
        checkEnable()
    }

    fun checkAgreementAllWithOthers() {
        binding.run {
            listOf(
                checkboxAgreement1,
                checkboxAgreement2,
                checkboxAgreement3,
                checkboxAgreement4,
                checkboxAgreement5
            ).forEachIndexed { index, checkbox ->
                checkAgree(index + 1, checkbox)
            }
        }
    }

    fun checkAgree(position: Int, view: ImageView) {
        isAgree[position] = !isAgree[position]
        if(isAgree[position]) {
            view.setImageResource(R.drawable.ic_check_selected)
        } else {
            view.setImageResource(R.drawable.ic_check_unselected)
        }
        checkEnable()
    }

    fun checkEnable() {
        binding.run {
            if(isAgree[0] && isAgree[1] && isAgree[2]) {
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

                }
            }
        }
    }
}
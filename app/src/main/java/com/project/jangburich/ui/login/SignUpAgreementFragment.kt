package com.project.jangburich.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import com.project.jangburich.MyApplication
import com.project.jangburich.R
import com.project.jangburich.api.request.login.SaveSignUpInfoRequest
import com.project.jangburich.databinding.FragmentSignUpAgreementBinding
import com.project.jangburich.ui.MainActivity
import com.project.jangburich.ui.home.HomeFragment
import com.project.jangburich.ui.login.viewModel.LoginViewModel

class SignUpAgreementFragment : Fragment() {

    lateinit var binding: FragmentSignUpAgreementBinding
    lateinit var mainActivity: MainActivity
    private val viewModel: LoginViewModel by lazy {
        ViewModelProvider(requireActivity())[LoginViewModel::class.java]
    }

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

            buttonNext.setOnClickListener {
                // 회원가입 API
                val signUpInfo = SaveSignUpInfoRequest(arguments?.getString("name").toString(), arguments?.getString("phone").toString(), isAgree[4], isAgree[5])
                viewModel.saveSignUpInfo(mainActivity, signUpInfo) {
                    mainActivity.setBottomNavigationHome()
                }
            }

            checkboxAgreementAll.setOnClickListener {
                isAgree[0] = !isAgree[0]
                checkAgreementAll()
            }
            checkboxAgreement1.setOnClickListener {
                isAgree[1] = !isAgree[1]
                checkAgree(1, checkboxAgreement1)
            }
            checkboxAgreement2.setOnClickListener {
                isAgree[2] = !isAgree[2]
                checkAgree(2, checkboxAgreement2)
            }
            checkboxAgreement3.setOnClickListener {
                isAgree[3] = !isAgree[3]
                checkAgree(3, checkboxAgreement3)
            }
            checkboxAgreement4.setOnClickListener {
                isAgree[4] = !isAgree[4]
                isAgree[5] = !isAgree[5]
                checkOptionalAgreementAll()
                checkAgree(4, checkboxAgreement4)
            }
        }

        return binding.root
    }

    fun checkAgreementAll() {
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

    fun checkOptionalAgreementAll() {
        binding.run {
            if(isAgree[4] && isAgree[5]) {
                checkboxAgreement4.setImageResource(R.drawable.ic_check_selected)
                checkAgreementAllWithOthers()
            } else {
                checkboxAgreement4.setImageResource(R.drawable.ic_check_unselected)
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
                checkboxAgreement4
            ).forEachIndexed { index, checkbox ->
                checkAgree(index + 1, checkbox)
            }
        }
    }

    fun checkAgree(position: Int, view: ImageView) {
        if(isAgree[position]) {
            view.setImageResource(R.drawable.ic_check_selected)
        } else {
            view.setImageResource(R.drawable.ic_check_unselected)
        }
        checkEnable()
        if(isAgree[1] && isAgree[2] && isAgree[3] && isAgree[4] && isAgree[5]) {
            binding.checkboxAgreementAll.setImageResource(R.drawable.ic_check_circle_selected)
        } else {
            binding.checkboxAgreementAll.setImageResource(R.drawable.ic_check_circle_unselected)
        }
    }

    fun checkEnable() {
        binding.run {
            if(isAgree[1] && isAgree[2] && isAgree[3]) {
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
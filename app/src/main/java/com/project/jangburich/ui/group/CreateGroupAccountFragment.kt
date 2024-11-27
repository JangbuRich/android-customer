package com.project.jangburich.ui.group

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.Group
import androidx.lifecycle.ViewModelProvider
import com.project.jangburich.MyApplication
import com.project.jangburich.R
import com.project.jangburich.databinding.FragmentCreateGroupAccountBinding
import com.project.jangburich.ui.MainActivity
import com.project.jangburich.ui.group.viewModel.GroupViewModel
import com.project.jangburich.ui.home.HomeGroupBottomSheetFragment
import com.project.jangburich.ui.home.viewModel.HomeViewModel
import com.project.jangburich.ui.login.viewModel.LoginViewModel

class CreateGroupAccountFragment : Fragment(), BankBottomSheetListener {

    lateinit var binding: FragmentCreateGroupAccountBinding
    lateinit var mainActivity: MainActivity

    val bankBottomSheet = BankBottomSheetFragment()

    lateinit var viewModel: GroupViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCreateGroupAccountBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity
        viewModel = ViewModelProvider(mainActivity)[GroupViewModel::class.java]

        initView()

        binding.run {
            editTextAccountBank.setOnClickListener {
                bankBottomSheet.show(childFragmentManager, bankBottomSheet.tag)
            }

            buttonNext.setOnClickListener {
                MyApplication.groupAccountNumber = editTextAccountNumber.text.toString()
                MyApplication.groupBankName = editTextAccountBank.text.toString()

                viewModel.createGroup(mainActivity)
            }
        }
        
        return binding.root
    }

    fun initView() {
        binding.run {
            mainActivity.hideBottomNavigation(true)

            toolbar.run {
                buttonBack.setOnClickListener {
                    fragmentManager?.popBackStack()
                }
            }
        }
    }

    override fun onButtonClicked(position: Int) {
        binding.run {
            editTextAccountBank.setText(resources.getStringArray(R.array.bank)[position].toString())
        }
    }

}
package com.project.jangburich.ui.group

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.project.jangburich.MyApplication
import com.project.jangburich.R
import com.project.jangburich.databinding.FragmentEnterCodeGroupBinding
import com.project.jangburich.ui.MainActivity
import com.project.jangburich.ui.group.viewModel.GroupViewModel
import com.project.jangburich.ui.home.HomeFragment

class EnterCodeGroupFragment : Fragment() {

    lateinit var binding: FragmentEnterCodeGroupBinding
    lateinit var mainActivity: MainActivity
    lateinit var viewModel: GroupViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentEnterCodeGroupBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity
        viewModel = ViewModelProvider(mainActivity)[GroupViewModel::class.java]

        initView()
        mainActivity.hideBottomNavigation(true)

        binding.run {
            textViewDate.text = MyApplication.codeGroupInfo.createdAt
            textViewGroupName.text = MyApplication.codeGroupInfo.teamName
            textViewGroupType.text = MyApplication.codeGroupInfo.teamType

            buttonCancel.setOnClickListener {
                fragmentManager?.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

                val nextFragment = HomeFragment()

                val transaction = mainActivity.manager.beginTransaction()
                transaction.replace(R.id.fragmentContainerView_main, nextFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }

            buttonEnterGroup.setOnClickListener {
                viewModel.enterGroup(mainActivity)
            }
        }

        return binding.root
    }

    fun initView() {
        binding.run {
            toolbar.run {
                buttonBack.setOnClickListener {
                    fragmentManager?.popBackStack()
                }
                textViewTitle.text = "그룹 입장하기"
            }
        }
    }
}
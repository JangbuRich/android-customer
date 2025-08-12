package com.project.jangburich.ui.group

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.project.jangburich.R
import com.project.jangburich.databinding.FragmentCreateGroupCompleteBinding
import com.project.jangburich.ui.MainActivity
import com.project.jangburich.ui.home.HomeFragment

class CreateGroupCompleteFragment : Fragment() {

    lateinit var binding: FragmentCreateGroupCompleteBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCreateGroupCompleteBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        initView()

        binding.run {
            buttonAccept.setOnClickListener {
                // 내 그룹 상세 화면 전환
                parentFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

                mainActivity.supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView_main, GroupDetailFragment())
                    .addToBackStack(null)
                    .commit()
            }
            buttonMyGroup.setOnClickListener {
                mainActivity.binding.bottomNavigation.selectedItemId = R.id.menu_group
            }
        }

        return binding.root
    }

    fun initView() {
        binding.run {
            mainActivity.hideBottomNavigation(true)

            toolbar.run {
                buttonBack.setOnClickListener {
                    parentFragmentManager.popBackStack()
                }
            }
        }
    }

}
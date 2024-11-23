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
import com.project.jangburich.databinding.FragmentEnterGroupCodeBinding
import com.project.jangburich.ui.MainActivity
import com.project.jangburich.ui.group.viewModel.GroupViewModel
import com.project.jangburich.ui.home.HomeFragment

class EnterGroupCodeFragment : Fragment() {

    lateinit var binding: FragmentEnterGroupCodeBinding
    lateinit var mainActivity: MainActivity
    lateinit var viewModel: GroupViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentEnterGroupCodeBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity
        viewModel = ViewModelProvider(mainActivity)[GroupViewModel::class.java]

        initView()

        binding.run {
            buttonNext.setOnClickListener {
                MyApplication.code = editTextCode.text.toString()
                viewModel.getGroupInfoWithCode(mainActivity, MyApplication.code)
            }
        }

        return binding.root
    }

    fun initView() {
        mainActivity.hideBottomNavigation(true)

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
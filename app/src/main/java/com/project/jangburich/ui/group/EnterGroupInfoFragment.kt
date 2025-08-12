package com.project.jangburich.ui.group

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.project.jangburich.R
import com.project.jangburich.databinding.FragmentEnterGroupInfoBinding
import com.project.jangburich.ui.MainActivity
import com.project.jangburich.ui.group.viewModel.GroupViewModel

class EnterGroupInfoFragment : Fragment() {

    lateinit var binding: FragmentEnterGroupInfoBinding
    lateinit var mainActivity: MainActivity
    private val viewModel: GroupViewModel by lazy {
        ViewModelProvider(requireActivity())[GroupViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentEnterGroupInfoBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        initView()

        binding.run {
            textViewGroupName.text = viewModel.groupInfo.value?.teamName
            textViewGroupType.text = viewModel.groupInfo.value?.teamType

            buttonCancel.setOnClickListener {
                parentFragmentManager.popBackStack()
            }

            buttonEnterGroup.setOnClickListener {
                viewModel.enterGroup(mainActivity, requireArguments().getString("code").toString()) {
                    // 내 그룹 화면 전환
                    mainActivity.binding.bottomNavigation.selectedItemId = R.id.menu_group
                }
            }
        }

        return binding.root
    }

    fun initView() {
        mainActivity.hideBottomNavigation(true)

        binding.run {
            toolbar.run {
                buttonBack.setOnClickListener {
                    parentFragmentManager.popBackStack()
                }
                textViewTitle.text = "그룹 입장하기"
            }
        }
    }
}
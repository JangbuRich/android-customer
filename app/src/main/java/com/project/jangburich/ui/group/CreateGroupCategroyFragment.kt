package com.project.jangburich.ui.group

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.project.jangburich.MyApplication
import com.project.jangburich.R
import com.project.jangburich.databinding.FragmentCreateGroupCategroyBinding
import com.project.jangburich.ui.MainActivity
import com.project.jangburich.ui.login.LoginMainFragment

class CreateGroupCategroyFragment : Fragment() {

    lateinit var binding: FragmentCreateGroupCategroyBinding
    lateinit var mainActivity: MainActivity

    var category: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCreateGroupCategroyBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        initView()

        binding.run {
            val buttons = listOf(buttonGroup1, buttonGroup2, buttonGroup3, buttonGroup4, buttonGroup5, buttonGroup6, buttonGroup7, buttonGroup8)
            val categoryText = listOf(textViewGroup1, textViewGroup2, textViewGroup3, textViewGroup4, textViewGroup5, textViewGroup6, textViewGroup7, textViewGroup8)
            val categoryImage = listOf(imageViewGroup1,imageViewGroup2, imageViewGroup3, imageViewGroup4, imageViewGroup5, imageViewGroup6, imageViewGroup7, imageViewGroup8)
            val buttonIcons = listOf(
                R.drawable.ic_group1_selected,       // 각 버튼의 선택 상태 이미지
                R.drawable.ic_group2_selected,
                R.drawable.ic_group3_selected,
                R.drawable.ic_group4_selected,
                R.drawable.ic_group5_selected,
                R.drawable.ic_group6_selected,
                R.drawable.ic_group7_selected,
                R.drawable.ic_group8_selected
            )
            val buttonUnselectedIcons = listOf(
                R.drawable.ic_group1_unselected,       // 각 버튼의 비선택 상태 이미지
                R.drawable.ic_group1_unselected,
                R.drawable.ic_group3_unselected,
                R.drawable.ic_group4_unselected,
                R.drawable.ic_group5_unselected,
                R.drawable.ic_group6_unselected,
                R.drawable.ic_group7_unselected,
                R.drawable.ic_group8_unselected
            )

            // isClicked 배열 초기화
            val isClicked = MutableList(buttons.size) { false }

            // 각 버튼에 클릭 리스너 설정
            buttons.forEachIndexed { index, button ->
                button.setOnClickListener {
                    // 모든 버튼 상태 초기화 (isClicked를 false로 설정)
                    isClicked.fill(false)

                    // 클릭한 버튼만 true로 설정
                    buttonNext.isEnabled = true
                    isClicked[index] = true

                    // 모든 버튼의 배경, 이미지, 텍스트 색상 업데이트
                    buttons.forEachIndexed { i, btn ->
                        if (isClicked[i]) {
                            // 선택된 버튼의 스타일 변경
                            btn.setBackgroundResource(R.drawable.background_group_selected) // 선택된 상태의 배경
                            categoryText[i].setTextColor(ContextCompat.getColor(mainActivity, R.color.gray_100)) // 선택된 상태의 텍스트 색상
                            categoryImage[i].setImageResource(buttonIcons[i]) // 선택된 상태의 이미지
                        } else {
                            // 비선택된 버튼의 스타일로 변경
                            btn.setBackgroundResource(R.drawable.background_group_unselected) // 비선택 상태의 배경
                            categoryText[i].setTextColor(ContextCompat.getColor(mainActivity, R.color.gray_60)) // 비선택 상태의 텍스트 색상
                            categoryImage[i].setImageResource(buttonUnselectedIcons[i]) // 비선택 상태의 이미지
                        }
                    }

                    // 선택된 버튼에 따른 category 값 설정
                    category = when (index) {
                        0 -> "INDIVIDUAL"
                        1 -> "GATHERING"
                        2 -> "FAMILY"
                        3 -> "COMPANY"
                        4 -> "CLUB"
                        5 -> "ALUMNI"
                        6 -> "STUDENT_ASSOCIATION"
                        7 -> "OTHER"
                        else -> ""
                    }
                }
            }

            buttonNext.setOnClickListener {
                val bundle = Bundle().apply {
                    putString("category", category)
                    putBoolean("isEdit", arguments?.getBoolean("isEdit") == true)
                }

                var nextFragment = CreateGroupInfoFragment().apply {
                    arguments = bundle
                }

                mainActivity.supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView_main, nextFragment)
                    .addToBackStack(null)
                    .commit()
            }
        }

        return binding.root
    }

    fun initView() {
        binding.run {
            mainActivity.hideBottomNavigation(true)

            textViewIntro.text =
                if(arguments?.getBoolean("isEdit") == true) {
                    "그룹의 유형을\n수정해주세요"
                } else {
                    "생성할 그룹의\n유형을 선택해주세요"
                }

            toolbar.run {
                buttonBack.setOnClickListener {
                    parentFragmentManager.popBackStack()
                }
            }
        }
    }
}
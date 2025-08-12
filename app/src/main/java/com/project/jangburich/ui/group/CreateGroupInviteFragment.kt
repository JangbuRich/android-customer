package com.project.jangburich.ui.group

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.project.jangburich.MyApplication
import com.project.jangburich.R
import com.project.jangburich.databinding.FragmentCreateGroupInviteBinding
import com.project.jangburich.ui.BasicToast
import com.project.jangburich.ui.MainActivity

class CreateGroupInviteFragment : Fragment() {

    lateinit var binding: FragmentCreateGroupInviteBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCreateGroupInviteBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        initView()

        binding.run {
            textViewCode.text = requireArguments().getString("code").toString()

            buttonCopy.setOnClickListener {
                copyTextToClipboard(requireActivity(), requireArguments().getString("code").toString())
            }

            buttonLinkKakao.setOnClickListener {
                // 카카오 - 그룹원 초대하기
            }

            buttonLinkBasic.setOnClickListener {
                // 링크 복사 - 그룹원 초대하기
            }

            buttonLinkCode.setOnClickListener {
                // 비밀 코드 복사 - 그룹원 초대하기
                copyTextToClipboard(requireActivity(), requireArguments().getString("code").toString())
            }

            buttonNext.setOnClickListener {
                mainActivity.supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView_main, CreateGroupCompleteFragment())
                    .addToBackStack(null)
                    .commit()
            }
        }
        
        return binding.root
    }

    fun copyTextToClipboard(context: Context, text: String) {
        // ClipboardManager 인스턴스 가져오기
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

        // 클립 데이터 생성
        val clip = ClipData.newPlainText("Copied Text", text)

        // 클립보드에 데이터 복사
        clipboard.setPrimaryClip(clip)

        // 사용자에게 복사 알림
        BasicToast.showBasicToast(requireActivity(), "복사가 완료됐어요", R.drawable.ic_check_selected, binding.buttonNext)
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
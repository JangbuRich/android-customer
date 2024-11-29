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
            textViewCode.text = MyApplication.groupSecretCode

            buttonCopy.setOnClickListener {
                copyTextToClipboard(mainActivity, MyApplication.groupSecretCode)
            }

            buttonNext.setOnClickListener {
                val nextFragment = CreateGroupCompleteFragment()

                val transaction = mainActivity.manager.beginTransaction()
                transaction.replace(R.id.fragmentContainerView_main, nextFragment)
                transaction.addToBackStack("")
                transaction.commit()
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
        Toast.makeText(context, "텍스트가 복사되었습니다.", Toast.LENGTH_SHORT).show()
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
}
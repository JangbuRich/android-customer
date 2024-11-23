package com.project.jangburich.ui.group

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
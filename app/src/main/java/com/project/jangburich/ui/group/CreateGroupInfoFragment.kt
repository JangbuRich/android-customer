package com.project.jangburich.ui.group

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.project.jangburich.MyApplication
import com.project.jangburich.R
import com.project.jangburich.databinding.FragmentCreateGroupInfoBinding
import com.project.jangburich.ui.MainActivity
import com.project.jangburich.ui.group.viewModel.GroupViewModel

class CreateGroupInfoFragment : Fragment() {

    lateinit var binding: FragmentCreateGroupInfoBinding
    lateinit var mainActivity: MainActivity
    private val viewModel: GroupViewModel by lazy {
        ViewModelProvider(requireActivity())[GroupViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCreateGroupInfoBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        initView()

        binding.run {

            editTextName.addTextChangedListener {
                checkEnable()
                textViewNameCount.text = "${editTextName.length()}/15"
            }
            editTextDescription.addTextChangedListener {
                textViewDescriptionCount.text = "${editTextDescription.length()}/100"
            }

            buttonNext.setOnClickListener {
                // 그룹 생성 API
                viewModel.createGroup(mainActivity, requireArguments().getString("category").toString(), editTextName.text.toString(), editTextDescription.text.toString()) { code ->
                    val bundle = Bundle().apply {
                        putString("code", code)
                    }

                    var nextFragment = CreateGroupInviteFragment().apply {
                        arguments = bundle
                    }

                    mainActivity.supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView_main, nextFragment)
                        .addToBackStack(null)
                        .commit()
                }
            }

        }

        return binding.root
    }

    fun checkEnable() {
        binding.run {
            if(editTextName.text.isNotEmpty()) {
                buttonNext.isEnabled = true
            } else {
                buttonNext.isEnabled = false
            }
        }
    }

    fun initView() {
        binding.run {
            buttonNext.isEnabled = false

            mainActivity.hideBottomNavigation(true)

            toolbar.run {
                buttonBack.setOnClickListener {
                    fragmentManager?.popBackStack()
                }
            }
        }
    }
}
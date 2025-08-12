package com.project.jangburich.ui.group

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.project.jangburich.R
import com.project.jangburich.databinding.FragmentEnterGroupCodeBinding
import com.project.jangburich.ui.MainActivity
import com.project.jangburich.ui.group.viewModel.GroupViewModel

class EnterGroupCodeFragment : Fragment() {

    lateinit var binding: FragmentEnterGroupCodeBinding
    lateinit var mainActivity: MainActivity
    private val viewModel: GroupViewModel by lazy {
        ViewModelProvider(requireActivity())[GroupViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentEnterGroupCodeBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        initView()

        binding.run {
            buttonNext.setOnClickListener {
                viewModel.getGroupInfoWithCode(mainActivity, editTextCode.text.toString().trim()) {
                    val bundle = Bundle().apply {
                        putString("code", editTextCode.text.toString().trim())
                    }

                    var nextFragment = EnterGroupInfoFragment().apply {
                        arguments = bundle
                    }

                    mainActivity.supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView_main, nextFragment)
                        .addToBackStack(null)
                        .commit()
                }
            }

            editTextCode.addTextChangedListener {
                textViewCodeCount.text = "${editTextCode.text.length}/10"
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
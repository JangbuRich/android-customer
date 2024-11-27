package com.project.jangburich.ui.group

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.jangburich.R
import com.project.jangburich.databinding.FragmentGroupBinding
import com.project.jangburich.ui.MainActivity

class GroupFragment : Fragment() {

    lateinit var binding: FragmentGroupBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentGroupBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        mainActivity.hideBottomNavigation(false)

        return binding.root
    }
}
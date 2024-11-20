package com.project.jangburich.ui.store

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.project.jangburich.databinding.FragmentHomeGroupBottomSheetBinding
import com.project.jangburich.databinding.FragmentStoreMapBottomSheetBinding
import com.project.jangburich.ui.MainActivity

interface StoreMapBottomSheetListener {
    fun onButtonClicked()
}
class StoreMapBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var listener: StoreMapBottomSheetListener
    lateinit var binding: FragmentStoreMapBottomSheetBinding
    lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = parentFragment as StoreMapBottomSheetListener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStoreMapBottomSheetBinding.inflate(inflater, container, false)
        mainActivity = activity as MainActivity

        binding.run {
//            buttonMakeGroup.setOnClickListener { onItemClicked() }
        }

        return binding.root
    }

    private fun onItemClicked() {
        listener.onButtonClicked()

        dismiss()
    }
}
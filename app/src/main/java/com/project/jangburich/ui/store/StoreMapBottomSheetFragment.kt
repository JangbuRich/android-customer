package com.project.jangburich.ui.store

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.project.jangburich.MyApplication
import com.project.jangburich.databinding.FragmentHomeGroupBottomSheetBinding
import com.project.jangburich.databinding.FragmentStoreMapBottomSheetBinding
import com.project.jangburich.ui.MainActivity
import com.project.jangburich.ui.store.viewModel.StoreViewModel

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
            textViewStoreName.text = MyApplication.selectedStore.name
            textViewStoreCategory.text = MyApplication.selectedStore.category
            textViewTimeOpen.text = "${MyApplication.selectedStore.closeTime} 영업 종료"
            textViewPhonenumValue.text = MyApplication.selectedStore.phoneNumber

            if(MyApplication.selectedStore.businessStatus == "open") {
                textViewTimeOpenClose.text = "영업중"
            } else {
                textViewTimeOpenClose.text = "영업 종료"
            }

            Glide.with(mainActivity).load(MyApplication.selectedStore.imageUrl).into(imageViewStore)

            buttonPayBefore.setOnClickListener {
                onItemClicked()
            }
            buttonReserve.setOnClickListener {

            }
        }

        return binding.root
    }


    private fun onItemClicked() {
        listener.onButtonClicked()

        dismiss()
    }
}
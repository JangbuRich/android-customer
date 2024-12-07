package com.project.jangburich.ui.store

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.project.jangburich.MyApplication
import com.project.jangburich.R
import com.project.jangburich.databinding.FragmentHomeGroupBottomSheetBinding
import com.project.jangburich.databinding.FragmentStoreMenuBuyBottomSheetBinding
import com.project.jangburich.ui.MainActivity

interface StoreMenuBuyBottomSheetListener {
    fun onButtonClicked(position: Int, quantity: Int)
}
class StoreMenuBuyBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var listener: StoreMenuBuyBottomSheetListener
    lateinit var binding: FragmentStoreMenuBuyBottomSheetBinding
    lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = parentFragment as StoreMenuBuyBottomSheetListener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStoreMenuBuyBottomSheetBinding.inflate(inflater, container, false)
        mainActivity = activity as MainActivity

        binding.run {

            textViewPriceValue.text = "${MyApplication.selectedMenuList.price}원"

            var num = 1

            buttonMinus.setOnClickListener {
                num -= 1
                textViewCountNum.text = num.toString()
                textViewPriceValue.text = "${MyApplication.selectedMenuList.price * num}원"
            }
            buttonPlus.setOnClickListener {
                num += 1
                textViewCountNum.text = num.toString()
                textViewPriceValue.text = "${MyApplication.selectedMenuList.price * num}원"
            }
            buttonCart.setOnClickListener {onItemClicked(0, num) }
            buttonOrder.setOnClickListener { onItemClicked(1, num) }
        }

        return binding.root
    }

    private fun onItemClicked(position: Int, quantity: Int) {
        listener.onButtonClicked(position, quantity)

        dismiss()
    }


}
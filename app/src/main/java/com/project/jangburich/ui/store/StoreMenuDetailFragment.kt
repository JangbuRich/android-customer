package com.project.jangburich.ui.store

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.project.jangburich.MyApplication
import com.project.jangburich.R
import com.project.jangburich.databinding.FragmentStoreMenuDetailBinding
import com.project.jangburich.ui.MainActivity
import com.project.jangburich.ui.store.viewModel.StoreViewModel


class StoreMenuDetailFragment : Fragment(), StoreMenuBuyBottomSheetListener {

    lateinit var binding: FragmentStoreMenuDetailBinding
    lateinit var mainActivity: MainActivity
    lateinit var viewModel: StoreViewModel

    var orderBottomSheet = StoreMenuBuyBottomSheetFragment()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentStoreMenuDetailBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity
        viewModel = ViewModelProvider(mainActivity)[StoreViewModel::class.java]

        initView()

        binding.run {
            textViewMenuName.text = MyApplication.selectedMenuList.menuName
            textViewMenuDescription.text = MyApplication.selectedMenuList.description
            textViewMenuPrice.text = "${MyApplication.selectedMenuList.price}원"

            if(MyApplication.selectedMenuList.isSignatureMenu == true) {
                chipBest.visibility = View.VISIBLE
            } else {
                chipBest.visibility = View.GONE
            }
            Glide.with(mainActivity).load(MyApplication.selectedMenuList.menuImgUrl).into(imageViewMenu)

            buttonBuy.setOnClickListener {
                orderBottomSheet.show(childFragmentManager, orderBottomSheet.tag)
            }
        }

        return binding.root
    }

    fun initView() {
        binding.run {
            toolbar.run {
                buttonBack.setOnClickListener {
                    fragmentManager?.popBackStack()
                }
            }
        }
    }

    override fun onButtonClicked(position: Int, quantity: Int) {
        viewModel.addCart(mainActivity, position, MyApplication.selectedGroupStoreId, MyApplication.selectedMenuList.menuId, quantity)
    }
}
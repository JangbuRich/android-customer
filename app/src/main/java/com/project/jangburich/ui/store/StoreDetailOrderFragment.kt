package com.project.jangburich.ui.store

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.jangburich.MyApplication
import com.project.jangburich.R
import com.project.jangburich.api.response.store.StoreMenu
import com.project.jangburich.databinding.FragmentStoreDetailBinding
import com.project.jangburich.databinding.FragmentStoreDetailOrderBinding
import com.project.jangburich.ui.MainActivity
import com.project.jangburich.ui.group.viewModel.GroupViewModel
import com.project.jangburich.ui.store.viewModel.StoreViewModel

class StoreDetailOrderFragment : Fragment() {
    lateinit var binding: FragmentStoreDetailOrderBinding
    lateinit var mainActivity: MainActivity
    lateinit var viewModel: StoreViewModel
    lateinit var groupViewModel: GroupViewModel

    var getMenuList = mutableListOf<StoreMenu>()

    private lateinit var menuAdapter: com.project.jangburich.ui.store.adapter.MenuAdapter
    val prepayGroupBottomSheet = PrepayGroupBottomSheetFragment()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentStoreDetailOrderBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity
        viewModel = ViewModelProvider(mainActivity)[StoreViewModel::class.java]
        groupViewModel = ViewModelProvider(mainActivity)[GroupViewModel::class.java]

        viewModel.getStoreDetail(mainActivity, MyApplication.selectedGroupStoreId)

        mainActivity.hideBottomNavigation(true)

        initAdapters()

        viewModel.run {
            storeDetailData.observe(mainActivity) {
                binding.run {
                    textViewStoreName.text = "구름 스토어"
                    textViewStoreCategory.text = it.category
                    textViewLocationValue.text = it.address
                    textViewTimeOpen.text = it.operationStatus
                    textViewTimeOpenClose.text = "${it.closeTime} 영업 종료"
                    textViewPhonenumValue.text = it.contactNumber

                    Glide.with(mainActivity).load(it.representativeImg).into(imageViewStore)
                }
            }
            menuList.observe(mainActivity) {
                getMenuList = it
                menuAdapter.updateList(getMenuList)
            }
        }

        initView()

        binding.run {
            buttonCart.setOnClickListener {

            }
        }

        return binding.root
    }

    private fun initAdapters() {
        // 어댑터 초기화
        menuAdapter = com.project.jangburich.ui.store.adapter.MenuAdapter(
            mainActivity,
            getMenuList
        ).apply {
            itemClickListener = object : com.project.jangburich.ui.store.adapter.MenuAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    MyApplication.selectedMenuList = getMenuList[position]
                    val nextFragment = StoreMenuDetailFragment()

                    val transaction = mainActivity.manager.beginTransaction()
                    transaction.replace(R.id.fragmentContainerView_main, nextFragment)
                    transaction.addToBackStack(null)
                    transaction.commit()
                }
            }
        }

        binding.recyclerViewMenu.apply {
            adapter = menuAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }

    fun initView() {
//        mainActivity.setStatusBarTransparent()

        binding.run {
            toolbar.run {
                buttonBack.setOnClickListener {
                    fragmentManager?.popBackStack()
                }
            }
        }
    }
}
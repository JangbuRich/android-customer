package com.project.jangburich.ui.store

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.jangburich.MyApplication
import com.project.jangburich.api.request.store.OrderItem
import com.project.jangburich.api.response.store.CartItem
import com.project.jangburich.databinding.FragmentOrderBinding
import com.project.jangburich.ui.MainActivity
import com.project.jangburich.ui.store.adapter.CartOrderAdapter
import com.project.jangburich.ui.store.viewModel.StoreViewModel

class OrderFragment : Fragment() {

    lateinit var binding: FragmentOrderBinding
    lateinit var mainActivity: MainActivity
    lateinit var viewModel: StoreViewModel

    private lateinit var cartOrderAdapter: CartOrderAdapter
    var getCartList = mutableListOf<CartItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentOrderBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity
        viewModel = ViewModelProvider(mainActivity)[StoreViewModel::class.java]

        viewModel.getCartData(mainActivity)

        initView()
        initAdapter()
        observeViewModel()

        binding.run {
            buttonOrder.setOnClickListener {
                viewModel.order(mainActivity)
            }
        }

        return binding.root
    }

    private fun initAdapter() {
        // 어댑터 초기화
        cartOrderAdapter = CartOrderAdapter(
            mainActivity,
            getCartList
        ).apply {
            itemClickListener = object : CartOrderAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {

                }
            }
        }

        binding.recyclerViewCart.apply {
            adapter = cartOrderAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }

    private fun observeViewModel() {
        viewModel.run {
            storeName.observe(viewLifecycleOwner) {
                binding.textViewStoreName.text = it
            }
            storeCategory.observe(viewLifecycleOwner) {
                binding.textViewStoreCategory.text = it
            }
            cartTotalAmount.observe(viewLifecycleOwner) {
                binding.textViewMenuTotalPriceValue.text = "${it}원"
            }
            cartDiscountAmount.observe(viewLifecycleOwner) {
                binding.textViewMenuDiscountValue.text = "${it}원"
            }
            cartFinalAmount.observe(viewLifecycleOwner) {
                binding.run {
                    textViewTotalPriceValue.text = "${it}원"
                    buttonOrder.text = "${it}원 주문하기"
                }
            }
            cartItemList.observe(viewLifecycleOwner) {
                getCartList = it
                cartOrderAdapter.updateList(getCartList)

                var tempItemList = mutableListOf<OrderItem>()
                for(i in 0 until getCartList.size) {
                    tempItemList.add(OrderItem(getCartList[i].menuId, getCartList[i].quantity))
                }

                MyApplication.cartItem = tempItemList
            }
        }
    }

    fun initView() {
        binding.run {
            toolbar.run {
                buttonBack.setOnClickListener {
                    fragmentManager?.popBackStack()
                }
                textViewTitle.text = "주문하기"
            }
        }
    }

}
package com.project.jangburich.ui.store

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.jangburich.MyApplication
import com.project.jangburich.R
import com.project.jangburich.databinding.FragmentOrderCompleteBinding
import com.project.jangburich.ui.MainActivity
import com.project.jangburich.ui.store.adapter.CartOrderAdapter
import com.project.jangburich.ui.store.adapter.OrderedDataAdapter
import com.project.jangburich.ui.store.viewModel.StoreViewModel
import com.skydoves.balloon.ArrowOrientation
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec
import com.skydoves.balloon.showAlignStart
import com.skydoves.balloon.showAlignTop

class OrderCompleteFragment : Fragment() {

    lateinit var binding: FragmentOrderCompleteBinding
    lateinit var mainActivity: MainActivity
    lateinit var viewModel: StoreViewModel

    lateinit var orderedDataAdapter: OrderedDataAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentOrderCompleteBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity
        viewModel = ViewModelProvider(mainActivity)[StoreViewModel::class.java]


        initView()
        initAdapter()

        binding.run {
            textViewPrice.text = "${MyApplication.orderedData.totalAmount}원"
            buttonUseTicket.setOnClickListener {
                viewModel.useTicket(mainActivity)
            }
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        showToolTip()
    }

    private fun initAdapter() {
        // 어댑터 초기화
        orderedDataAdapter = OrderedDataAdapter(
            mainActivity,
            MyApplication.orderedData.items
        )

        binding.recyclerViewOrder.apply {
            adapter = orderedDataAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }

    private fun showToolTip() {
        binding.run {
            val balloon = Balloon.Builder(mainActivity)
//                .setWidth(BalloonSizeSpec.WRAP)
                .setWidthRatio(0.6f) // sets width as 60% of the horizontal screen's size.
                .setHeight(BalloonSizeSpec.WRAP)
                .setText("매장 직원만 클릭할 수 있어요!")
                .setTextColorResource(R.color.gray_100)
                .setTextSize(14f)
                .setTextTypeface(ResourcesCompat.getFont(mainActivity, R.font.pretendard_medium)!!)
                .setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)
                .setArrowOrientation(ArrowOrientation.BOTTOM)
                .setArrowSize(5)
                .setArrowPosition(0.5f)
                .setArrowColorResource(R.color.gray_20)
                .setTextGravity(Gravity.CENTER)
                .setElevation(0)
                .setPaddingHorizontal(12)
                .setPaddingVertical(8)
                .setMarginHorizontal(5)
                .setMarginVertical(10)
                .setCornerRadius(8f)
                .setBackgroundDrawableResource(R.drawable.background_tooltip)
                .setBalloonAnimation(BalloonAnimation.ELASTIC)
                .build()

            buttonUseTicket.showAlignTop(balloon)
        }
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
}
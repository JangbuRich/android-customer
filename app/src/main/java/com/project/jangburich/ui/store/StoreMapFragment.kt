package com.project.jangburich.ui.store

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.model.Marker
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.KakaoMapSdk
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapView
import com.project.jangburich.BuildConfig.KAKAO_MAP_KEY
import com.project.jangburich.R
import com.project.jangburich.api.response.store.Store
import com.project.jangburich.databinding.FragmentStoreMapBinding
import com.project.jangburich.ui.MainActivity
import com.project.jangburich.ui.login.viewModel.LoginViewModel
import com.project.jangburich.ui.store.adapter.StoreCategoryAdapter
import com.project.jangburich.ui.store.viewModel.StoreViewModel
import com.skydoves.balloon.ArrowOrientation
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec
import com.skydoves.balloon.showAlignStart

class StoreMapFragment : Fragment() {

    lateinit var binding: FragmentStoreMapBinding
    lateinit var mainActivity: MainActivity
    lateinit var viewModel: StoreViewModel

    private lateinit var storeCategoryAdapter: StoreCategoryAdapter

    private var storeCategoryNameList = mutableListOf<String>()
    private var categoryPosition = 0

    private lateinit var mapView : MapView
    private var kakaoMap : KakaoMap? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStoreMapBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity
        viewModel = ViewModelProvider(mainActivity)[StoreViewModel::class.java]

        mainActivity.hideBottomNavigation(false)

        storeCategoryNameList =
            resources.getStringArray(R.array.store_category).toMutableList()

        showMapView()

        binding.run {

            viewModel.getStoreList(mainActivity, "ALL")
            buttonList.setOnClickListener {
                val nextFragment = StoreListFragment()

                val transaction = mainActivity.manager.beginTransaction()
                transaction.replace(R.id.fragmentContainerView_main, nextFragment)
                transaction.addToBackStack("")
                transaction.commit()
            }
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        initAdapters()
        showToolTip()
    }

    private fun initAdapters() {
        // 카테고리 어댑터 초기화
        storeCategoryAdapter = StoreCategoryAdapter(
            mainActivity,
            storeCategoryNameList
        ).apply {
            itemClickListener = object : StoreCategoryAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    categoryPosition = position
                    // 매장 리스트 불러오기
                }
            }
        }

        binding.recyclerViewStoreCategory.apply {
            adapter = storeCategoryAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        }
    }

    private fun showToolTip() {
        binding.run {
            val balloon = Balloon.Builder(mainActivity)
//                .setWidth(BalloonSizeSpec.WRAP)
                .setWidthRatio(0.6f) // sets width as 60% of the horizontal screen's size.
                .setHeight(BalloonSizeSpec.WRAP)
                .setText("리스트로 매장을 확인할 수 있어요")
                .setTextColorResource(R.color.gray_100)
                .setTextSize(14f)
                .setTextTypeface(ResourcesCompat.getFont(mainActivity, R.font.pretendard_medium)!!)
                .setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)
                .setArrowOrientation(ArrowOrientation.END)
                .setArrowSize(5)
                .setArrowPosition(0.5f)
                .setArrowColorResource(R.color.gray_20)
                .setTextGravity(Gravity.CENTER)
                .setElevation(0)
                .setPaddingHorizontal(12)
                .setPaddingVertical(8)
                .setMarginHorizontal(5)
                .setCornerRadius(8f)
                .setBackgroundDrawableResource(R.drawable.background_tooltip)
                .setBalloonAnimation(BalloonAnimation.ELASTIC)
                .build()

            buttonList.showAlignStart(balloon)
        }
    }

    private fun showMapView() {
        mapView = binding.mapView

        // KakaoMapSDK 초기화
        KakaoMapSdk.init(requireContext(), KAKAO_MAP_KEY)

        mapView.start(object : MapLifeCycleCallback() {

            override fun onMapDestroy() {
                Log.d("KakaoMap", "onMapDestroy")
            }

            override fun onMapError(p0: Exception?) {
                Log.e("KakaoMap", "onMapError")
            }

        }, object : KakaoMapReadyCallback() {
            override fun onMapReady(kakaomap: KakaoMap) {
                kakaoMap = kakaomap

                // 마커 추가

            }
        })
    }
}
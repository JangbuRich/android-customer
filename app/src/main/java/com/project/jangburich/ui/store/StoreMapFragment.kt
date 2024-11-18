package com.project.jangburich.ui.store

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.project.jangburich.BuildConfig
import com.kakao.vectormap.KakaoMapSdk
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapView
import com.project.jangburich.BuildConfig.KAKAO_MAP_KEY
import com.project.jangburich.R
import com.project.jangburich.databinding.FragmentStoreMapBinding
import com.project.jangburich.ui.MainActivity
import com.project.jangburich.ui.store.adapter.StoreCategoryAdapter

class StoreMapFragment : Fragment() {

    lateinit var binding: FragmentStoreMapBinding
    lateinit var mainActivity: MainActivity

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

        mainActivity.hideBottomNavigation(false)

        storeCategoryNameList =
            resources.getStringArray(R.array.store_category).toMutableList()

        showMapView()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        initAdapters()
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


    private fun showMapView(){

        mapView = binding.mapView

        // KakaoMapSDK 초기화!!
        KakaoMapSdk.init(requireContext(), KAKAO_MAP_KEY)

        mapView.start(object : MapLifeCycleCallback() {

            override fun onMapDestroy() {
                // 지도 API가 정상적으로 종료될 때 호출
                Log.d("KakaoMap", "onMapDestroy")
            }

            override fun onMapError(p0: Exception?) {
                // 인증 실패 및 지도 사용 중 에러가 발생할 때 호출
                Log.e("KakaoMap", "onMapError")
            }
        }, object : KakaoMapReadyCallback(){
            override fun onMapReady(kakaomap: KakaoMap) {
                // 정상적으로 인증이 완료되었을 때 호출
                kakaoMap = kakaomap
            }
        })
    }
}
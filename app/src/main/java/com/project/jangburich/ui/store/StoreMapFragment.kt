package com.project.jangburich.ui.store

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.KakaoMapSdk
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapView
import com.kakao.vectormap.camera.CameraUpdateFactory
import com.kakao.vectormap.label.LabelLayer
import com.kakao.vectormap.label.LabelOptions
import com.kakao.vectormap.label.LabelStyle
import com.kakao.vectormap.label.LabelStyles
import com.project.jangburich.BuildConfig.KAKAO_MAP_KEY
import com.project.jangburich.MyApplication
import com.project.jangburich.R
import com.project.jangburich.api.response.store.Store
import com.project.jangburich.databinding.FragmentStoreMapBinding
import com.project.jangburich.ui.MainActivity
import com.project.jangburich.ui.group.PrePaymentTotalFragment
import com.project.jangburich.ui.group.viewModel.GroupViewModel
import com.project.jangburich.ui.home.HomeGroupBottomSheetFragment
import com.project.jangburich.ui.store.adapter.StoreCategoryAdapter
import com.project.jangburich.ui.store.viewModel.StoreViewModel
import com.skydoves.balloon.ArrowOrientation
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec
import com.skydoves.balloon.showAlignStart


class StoreMapFragment : Fragment(), StoreMapBottomSheetListener, PrepayGroupBottomSheetListener {

    lateinit var binding: FragmentStoreMapBinding
    lateinit var mainActivity: MainActivity
    lateinit var viewModel: StoreViewModel
    lateinit var groupViewModel: GroupViewModel
    private var labelLayer: LabelLayer? = null

    private lateinit var storeCategoryAdapter: StoreCategoryAdapter

    private var storeCategoryNameList = mutableListOf<String>()
    private var categoryPosition = 0
    private var getStoreList = mutableListOf<Store>()

    val storeBottomSheet = StoreMapBottomSheetFragment()
    val prepayGroupBottomSheet = PrepayGroupBottomSheetFragment()

    private lateinit var mapView : MapView
    private var kakaoMap : KakaoMap? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStoreMapBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity
        viewModel = ViewModelProvider(mainActivity)[StoreViewModel::class.java]
        groupViewModel = ViewModelProvider(mainActivity)[GroupViewModel::class.java]

        mainActivity.hideBottomNavigation(false)

        storeCategoryNameList =
            resources.getStringArray(R.array.store_category).toMutableList()

        showMapView()

        binding.run {

            viewModel.getStoreList(mainActivity, storeCategoryNameList[0])

            viewModel.run {
                storeList.observe(mainActivity) {
                    getStoreList = it

                    addMarkersAndSetupClickListener(getStoreList)
                }
            }

            buttonList.setOnClickListener {
                val nextFragment = StoreListFragment()

                val transaction = mainActivity.manager.beginTransaction()
                transaction.replace(com.project.jangburich.R.id.fragmentContainerView_main, nextFragment)
                transaction.addToBackStack(null)
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
                    viewModel.getStoreList(mainActivity, storeCategoryNameList[categoryPosition])
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
                .setTextTypeface(ResourcesCompat.getFont(mainActivity,R.font.pretendard_medium)!!)
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
            override fun getPosition(): LatLng {
                return LatLng.from(37.49757415, 127.0278389)
            }

            override fun onMapReady(kakaomap: KakaoMap) {
                kakaoMap = kakaomap

//                setupMap()
                addMarkersAndSetupClickListener(getStoreList)


                Log.d("##", "onMapReady")
            }
        })
    }

    /*
    private fun settingLabel() {
        val styles = kakaoMap?.labelManager?.addLabelStyles(
            LabelStyles.from(LabelStyle.from(R.drawable.ic_marker))
        )

        val options = LabelOptions.from(
            LatLng.from(37.6114538, 126.938461)
        ).setStyles(styles)

        val labelManager = kakaoMap?.labelManager
        val layer = labelManager?.layer

        if (layer != null) {
            // 레이어가 null이 아니라면
            val label = layer.addLabel(options)
            label.show()
            Log.d("##", "label : $label")
        } else {
            // 레이어가 null이라면 처리
        }
    }

    private fun setupMap() {
        // 인증 후 API가 정상적으로 실행될 때 호출됨
        // 좌표값은 추후 수정 예정
        val position = LatLng.from(37.6114538, 126.938461)
        val cameraUpdate = CameraUpdateFactory.newCenterPosition(position,20)
        kakaoMap?.moveCamera(cameraUpdate)

        // 라벨 추가
        settingLabel()
    }
     */

    private fun addMarkersAndSetupClickListener(storeList: List<Store>) {
        val labelManager = kakaoMap?.labelManager
        val layer = labelManager?.layer

        val position = LatLng.from(37.49757415, 127.0278389)
        val cameraUpdate = CameraUpdateFactory.newCenterPosition(position,15)
        kakaoMap?.moveCamera(cameraUpdate)

        if (layer != null) {
            storeList.forEach { store ->
                // 라벨 옵션 설정
                val options = LabelOptions.from(LatLng.from(store.latitude, store.longitude))
                    .setStyles(
                        kakaoMap?.labelManager?.addLabelStyles(
                            LabelStyles.from(LabelStyle.from(R.drawable.ic_marker))
                        )
                    )

                // 라벨 추가
                val label = layer.addLabel(options)
                label.isClickable = true
                label.tag = store.storeId // storeId를 라벨의 태그로 설정
                label.show()

                Log.d("##", "Added label for store: ${store.name}")
            }

            // 라벨 클릭 리스너 설정
            kakaoMap?.setOnLabelClickListener { _, _, clickedLabel ->
                val clickedStoreId = clickedLabel.tag as? Int // 라벨 태그에서 storeId 가져오기
                val clickedStore = storeList.find { it.storeId == clickedStoreId }

                if (clickedStore != null) {
                    Log.d("##", "Clicked on store: ${clickedStore.name}")
                    MyApplication.selectedStore = clickedStore
                    storeBottomSheet.show(childFragmentManager, storeBottomSheet.tag)
                } else {
                    Log.e("##", "Store not found for clicked label.")
                }
                true // 클릭 이벤트 소비
            }
        } else {
            Log.e("KakaoMap", "Label layer is null. Unable to add markers.")
        }
    }


    override fun onButtonClicked() {
        prepayGroupBottomSheet.show(childFragmentManager, prepayGroupBottomSheet.tag)
    }

    override fun onGroupButtonClicked() {
        groupViewModel.getPrepayData(mainActivity, MyApplication.selectedStore.storeId.toLong(), MyApplication.prepaymentGroupId)
    }


}
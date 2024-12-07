package com.project.jangburich.ui.group

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
import com.project.jangburich.databinding.FragmentGroupDetailBinding
import com.project.jangburich.ui.MainActivity
import com.project.jangburich.ui.group.adapter.GroupAdapter
import com.project.jangburich.ui.group.adapter.GroupStoreAdapter
import com.project.jangburich.ui.group.adapter.PaymentAdapter
import com.project.jangburich.ui.group.viewModel.GroupViewModel
import com.project.jangburich.ui.store.StoreDetailFragment
import com.project.jangburich.ui.store.adapter.StoreListAdapter

class GroupDetailFragment : Fragment() {

    lateinit var binding: FragmentGroupDetailBinding
    lateinit var mainActivity: MainActivity
    lateinit var viewModel: GroupViewModel

    lateinit var paymentAdapter: PaymentAdapter
    lateinit var groupStoreAdapter: GroupStoreAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentGroupDetailBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity
        viewModel = ViewModelProvider(mainActivity)[GroupViewModel::class.java]

        initView()

        binding.run {
            textViewGroupName.text = MyApplication.selectedGroupDetail.teamName
            textViewGroupDescription.text = MyApplication.selectedGroupDetail.description
            textViewGroupFavoriteStoreName.text = MyApplication.selectedGroupDetail.storeName
            if(MyApplication.selectedGroupDetail.isMeLeader) {
                imageViewLeader.visibility = View.VISIBLE
                textViewGroupRemainPaymentTitle.text = "남은 선결제 금액"
                textViewGroupTotalPaymentTitle.text = "전체 선결제 금액"
                textViewGroupTotalPaymentValue.text = "${MyApplication.selectedGroupDetail.totalPrepaidAmount}원"
                textViewGroupRemainPaymentValue.text = "${MyApplication.selectedGroupDetail.remainingAmount}원"
            } else {
                imageViewLeader.visibility = View.GONE
                textViewGroupRemainPaymentTitle.text = "남은 금액"
                textViewGroupTotalPaymentTitle.text = "사용 가능 금액(1인)"
                textViewGroupTotalPaymentValue.text = "${MyApplication.selectedGroupDetail.personalAllocatedAmount}원"
                textViewGroupRemainPaymentValue.text = "${MyApplication.selectedGroupDetail.personalAllocatedAmount - MyApplication.selectedGroupDetail.userUsedAmount}원"
            }
            textViewMyPaymentValue.text = "${-MyApplication.selectedGroupDetail.userUsedAmount}원"

            // 그룹원 정보
            textViewGroupMemberNum.text = "${MyApplication.selectedGroupDetail.totalMemberCount}명"
            // 최대 표시할 이미지 개수
            val maxImages = 3

            // 프로필 이미지 리스트 가져오기
            val profileImages = MyApplication.selectedGroupDetail.teamMemberImgUrl ?: listOf()

            // 프로필 이미지들 처리
            for (i in 0 until maxImages) {
                val imageView = when (i) {
                    0 -> imageViewGroupMemberProfile1
                    1 -> imageViewGroupMemberProfile2
                    2 -> imageViewGroupMemberProfile3
                    else -> null
                }

                imageView?.let {
                    if (i < profileImages.size && !profileImages[i].isNullOrEmpty()) {
                        imageViewGroupMemberProfileMore.visibility = View.INVISIBLE
                        // 이미지가 존재하면 Glide로 로드
                        Glide.with(mainActivity)
                            .load(profileImages[i])
                            .into(it)
                        it.visibility = View.VISIBLE // 이미지를 보여줌
                    } else {
                        // 이미지가 없거나 크기를 초과한 경우 기본 이미지 숨김
                        it.setImageResource(R.drawable.img_profile_default)
                        it.visibility = if (i < profileImages.size) View.VISIBLE else View.GONE
                        if(MyApplication.selectedGroupDetail.totalMemberCount > 3) {
                            imageViewGroupMemberProfileMore.visibility = View.VISIBLE
                        } else {
                            imageViewGroupMemberProfileMore.visibility = View.INVISIBLE
                        }
                    }
                }
            }

            // 오늘의 결제 내역
            textViewTodayPaymentAmount.text = "총 ${MyApplication.selectedGroupDetail.totalTodayTransactionCount}건"
            textViewTodayDate.text = MyApplication.todayDate

        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        initAdapter()
    }

    private fun initAdapter() {
        paymentAdapter = PaymentAdapter(
            mainActivity,
            MyApplication.selectedGroupDetail.todayPayments
        )

        binding.recyclerViewTodayPayment.apply {
            adapter = paymentAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }

        groupStoreAdapter = GroupStoreAdapter(
            mainActivity,
            MyApplication.selectedGroupDetail.prepayedStores
        ).apply {
            itemClickListener = object : GroupStoreAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    MyApplication.selectedGroupStoreId = MyApplication.selectedGroupDetail.prepayedStores[position].storeId.toLong()

                    viewModel.getGroupStoreDetail(mainActivity)
                }
            }
        }

        binding.recyclerViewGroupStore.apply {
            adapter = groupStoreAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
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
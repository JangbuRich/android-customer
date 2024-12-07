package com.project.jangburich.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.jangburich.MyApplication
import com.project.jangburich.MyApplication.Companion.isFirst
import com.project.jangburich.MyApplication.Companion.isKakaoPayComplete
import com.project.jangburich.R
import com.project.jangburich.api.response.home.Team
import com.project.jangburich.databinding.FragmentHomeBinding
import com.project.jangburich.ui.MainActivity
import com.project.jangburich.ui.group.CreateGroupCategroyFragment
import com.project.jangburich.ui.group.EnterGroupCodeFragment
import com.project.jangburich.ui.home.adapter.TeamAdapter
import com.project.jangburich.ui.home.viewModel.HomeViewModel
import com.project.jangburich.ui.wallet.ChargeCompleteFragment
import com.project.jangburich.ui.wallet.WalletFragment

class HomeFragment : Fragment(), HomeGroupBottomSheetListener {

    lateinit var binding: FragmentHomeBinding
    lateinit var mainActivity: MainActivity
    lateinit var viewModel: HomeViewModel

    private lateinit var teamAdapter: TeamAdapter

    val groupBottomSheet = HomeGroupBottomSheetFragment()

    var getTeamlist = mutableListOf<Team>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity
        viewModel = ViewModelProvider(mainActivity)[HomeViewModel::class.java]

        viewModel.run {
            userName.observe(mainActivity) {
                binding.run {
                    MyApplication.userName = it
                    textViewName.text = "${it}님 반가워요!"
                }
            }
            currentDate.observe(mainActivity) {
                binding.run {
                    MyApplication.todayDate = it
                    textViewDate.text = it
                }
            }
            userPoint.observe(mainActivity) {
                binding.run {
                    textViewPointValue.text = "${it}원"
                }
            }
            joinedTeamCount.observe(mainActivity) {
                binding.run {
                    textViewGroupNumValue.text = "${it}팀"
                }
            }
            reservationCount.observe(mainActivity) {
                binding.run {
                    textViewReserveNumValue.text = "${it}건"
                }
            }
            teamList.observe(mainActivity) {
                getTeamlist = it
                initAdapters()
            }
        }

        mainActivity.hideBottomNavigation(false)

        initView()

        binding.run {
            buttonMap.setOnClickListener { mainActivity.binding.bottomNavigation.selectedItemId = R.id.menu_store }
            buttonGroup.setOnClickListener { mainActivity.binding.bottomNavigation.selectedItemId = R.id.menu_group }
//            buttonReserve.setOnClickListener { mainActivity.binding.bottomNavigation.selectedItemId = R.id.menu_reserve }
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getHomeData(mainActivity)

        if(isFirst) {
            groupBottomSheet.show(childFragmentManager, groupBottomSheet.tag)
        }
    }

    private fun initAdapters() {
        // 어댑터 초기화
        teamAdapter = TeamAdapter(
            mainActivity,
            getTeamlist
        ).apply {
            itemClickListener = object : TeamAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {

                }
            }
        }

        binding.recyclerViewJangbu.apply {
            adapter = teamAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        }
    }

    fun initView() {
        mainActivity.hideBottomNavigation(false)

        binding.run {
            toolbar.run {
                buttonWallet.setOnClickListener {
                    // 나의 지갑 화면
                    val nextFragment = WalletFragment()

                    val transaction = mainActivity.manager.beginTransaction()
                    transaction.replace(R.id.fragmentContainerView_main, nextFragment)
                    transaction.addToBackStack(null)
                    transaction.commit()
                }
            }
        }
    }

    override fun onButtonClicked(position: Int) {
        if(position == 0) {
            val nextFragment = EnterGroupCodeFragment()

            val transaction = mainActivity.manager.beginTransaction()
            transaction.replace(R.id.fragmentContainerView_main, nextFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        } else {
            val nextFragment = CreateGroupCategroyFragment()

            val transaction = mainActivity.manager.beginTransaction()
            transaction.replace(R.id.fragmentContainerView_main, nextFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }
}
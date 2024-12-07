package com.project.jangburich.ui.group

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.project.jangburich.MyApplication
import com.project.jangburich.R
import com.project.jangburich.databinding.FragmentPrePaymentTotalBinding
import com.project.jangburich.ui.MainActivity
import com.project.jangburich.ui.group.viewModel.GroupViewModel
import com.project.jangburich.ui.home.HomeFragment

class PrePaymentTotalFragment : Fragment() {

    lateinit var binding: FragmentPrePaymentTotalBinding
    lateinit var mainActivity: MainActivity
    lateinit var viewModel: GroupViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPrePaymentTotalBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity
        viewModel = ViewModelProvider(mainActivity)[GroupViewModel::class.java]

        intiView()
        observeViewModel()

        binding.run {
            textViewStoreName.text = MyApplication.selectedStore.name
            textViewStoreCategory.text = MyApplication.selectedStore.category

            buttonNext.setOnClickListener {
                MyApplication.prepaymentTotalPrice = editTextPrice.text.toString().toInt()

                val nextFragment = PrePaymentIndividualFragment()

                val transaction = mainActivity.manager.beginTransaction()
                transaction.replace(R.id.fragmentContainerView_main, nextFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }

        return binding.root
    }

    fun observeViewModel() {
        viewModel.run {
            prepayData.observe(viewLifecycleOwner) {
                MyApplication.remainPrepayAmount = it.remainPrepay

                binding.run {
                    textViewRemainWalletValue.text = it.wallet.toString()
                    textViewDescription.text = "최소 ${it.minPrepayAmount}원부터 결제 가능해요"
                    editTextPrice.hint = it.minPrepayAmount.toString()
                }
            }
        }
    }

    fun intiView() {
        mainActivity.hideBottomNavigation(true)

        binding.run {
            toolbar.run {
                buttonBack.setOnClickListener {
                   fragmentManager?.popBackStack()
                }
                textViewTitle.text = "선결제"
            }
        }
    }

}
package com.project.jangburich.ui.group

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.project.jangburich.MyApplication
import com.project.jangburich.R
import com.project.jangburich.databinding.FragmentPrePaymentIndividualBinding
import com.project.jangburich.ui.MainActivity
import com.project.jangburich.ui.group.viewModel.GroupViewModel

class PrePaymentIndividualFragment : Fragment() {

    lateinit var binding: FragmentPrePaymentIndividualBinding
    lateinit var mainActivity: MainActivity
    lateinit var viewModel: GroupViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPrePaymentIndividualBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity
        viewModel = ViewModelProvider(mainActivity)[GroupViewModel::class.java]

        intiView()

        binding.run {
            textViewTotalPriceValue.text = MyApplication.prepaymentTotalPrice.toString()
            textViewStoreName.text = MyApplication.storeName
            textViewStoreCategory.text = MyApplication.storeCategory

            buttonNext.setOnClickListener {
                MyApplication.prepaymentIndividualPrice = editTextIndividualPrice.text.toString().toInt()
                viewModel.prepay(mainActivity)
            }
        }

        return binding.root
    }

    fun intiView() {
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
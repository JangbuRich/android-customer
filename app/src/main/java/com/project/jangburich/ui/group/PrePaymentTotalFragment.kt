package com.project.jangburich.ui.group

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.jangburich.MyApplication
import com.project.jangburich.R
import com.project.jangburich.databinding.FragmentPrePaymentTotalBinding
import com.project.jangburich.ui.MainActivity
import com.project.jangburich.ui.home.HomeFragment

class PrePaymentTotalFragment : Fragment() {

    lateinit var binding: FragmentPrePaymentTotalBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPrePaymentTotalBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        intiView()

        binding.run {
            textViewStoreName.text = MyApplication.storeName
            textViewStoreCategory.text = MyApplication.storeCategory

            buttonNext.setOnClickListener {
                MyApplication.prepaymentTotalPrice = editTextPrice.text.toString().toInt()

                val nextFragment = PrePaymentIndividualFragment()

                val transaction = mainActivity.manager.beginTransaction()
                transaction.replace(R.id.fragmentContainerView_main, nextFragment)
                transaction.addToBackStack("")
                transaction.commit()
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
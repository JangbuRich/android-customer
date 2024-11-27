package com.project.jangburich.ui.store

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.jangburich.R
import com.project.jangburich.databinding.FragmentStoreListBinding
import com.project.jangburich.ui.MainActivity

class StoreListFragment : Fragment() {

    lateinit var binding: FragmentStoreListBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentStoreListBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        binding.run {
            buttonMap.setOnClickListener {
                val nextFragment = StoreMapFragment()

                val transaction = mainActivity.manager.beginTransaction()
                transaction.replace(R.id.fragmentContainerView_main, nextFragment)
                transaction.addToBackStack("")
                transaction.commit()
            }
        }

        return binding.root
    }


}
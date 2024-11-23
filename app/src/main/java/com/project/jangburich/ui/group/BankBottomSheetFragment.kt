package com.project.jangburich.ui.group

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.project.jangburich.R
import com.project.jangburich.databinding.FragmentBankBottomSheetBinding
import com.project.jangburich.databinding.FragmentHomeGroupBottomSheetBinding
import com.project.jangburich.ui.MainActivity
import com.project.jangburich.ui.group.adapter.Bank
import com.project.jangburich.ui.group.adapter.BankAdapter
import com.project.jangburich.ui.home.adapter.TeamAdapter

interface BankBottomSheetListener {
    fun onButtonClicked(position: Int)
}
class BankBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var listener: BankBottomSheetListener
    lateinit var binding: FragmentBankBottomSheetBinding
    lateinit var mainActivity: MainActivity

    private lateinit var bankAdapter: BankAdapter

    private val bankList = listOf(
        Bank("카카오뱅크", R.drawable.ic_bank1),
        Bank("NH농협", R.drawable.ic_bank2),
        Bank("KB국민", R.drawable.ic_bank3),
        Bank("토스뱅크", R.drawable.ic_bank4),
        Bank("신한", R.drawable.ic_bank5),
        Bank("우리", R.drawable.ic_bank6),
        Bank("하나", R.drawable.ic_bank7),
        Bank("IBK기업", R.drawable.ic_bank8),
        Bank("새마을", R.drawable.ic_bank9),
        Bank("케이뱅크", R.drawable.ic_bank10),
        Bank("수협", R.drawable.ic_bank11),
        Bank("부산", R.drawable.ic_bank12)
    )

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = parentFragment as BankBottomSheetListener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBankBottomSheetBinding.inflate(inflater, container, false)
        mainActivity = activity as MainActivity

        initAdapters()

        binding.run {

        }

        return binding.root
    }

    private fun initAdapters() {
        // 어댑터 초기화
        bankAdapter = BankAdapter(
            mainActivity,
            bankList
        ).apply {
            itemClickListener = object : BankAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    listener.onButtonClicked(position)

                    dismiss()
                }
            }
        }

        binding.recyclerViewBank.apply {
            adapter = bankAdapter
            layoutManager = GridLayoutManager(context, 3)
        }
    }


}
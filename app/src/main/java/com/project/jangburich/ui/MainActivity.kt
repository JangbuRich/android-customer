package com.project.jangburich.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.project.jangburich.R
import com.project.jangburich.databinding.ActivityMainBinding
import com.project.jangburich.ui.group.GroupFragment
import com.project.jangburich.ui.home.HomeFragment
import com.project.jangburich.ui.mypage.MypageFragment
import com.project.jangburich.ui.reserve.ReserveFragment
import com.project.jangburich.ui.store.StoreFragment

class MainActivity : AppCompatActivity() {
    val manager = supportFragmentManager

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setBottomNavigationView()

        setContentView(binding.root)
    }

    private fun setBottomNavigationView() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> {
                    val nextFragment = HomeFragment()

                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.fragmentContainerView_main, nextFragment)
                    transaction.addToBackStack(null)
                    transaction.commit()
                    true
                }

                R.id.menu_store -> {
                    val nextFragment = StoreFragment()

                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.fragmentContainerView_main, nextFragment)
                    transaction.addToBackStack(null)
                    transaction.commit()
                    true
                }

                R.id.menu_group -> {
                    val nextFragment = GroupFragment()

                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.fragmentContainerView_main, nextFragment)
                    transaction.addToBackStack(null)
                    transaction.commit()
                    true
                }

                R.id.menu_reserve -> {
                    val nextFragment = ReserveFragment()

                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.fragmentContainerView_main, nextFragment)
                    transaction.addToBackStack(null)
                    transaction.commit()
                    true
                }

                R.id.menu_mypage -> {
                    val nextFragment = MypageFragment()

                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.fragmentContainerView_main, nextFragment)
                    transaction.addToBackStack(null)
                    transaction.commit()
                    true
                }

                else -> false
            }
        }
    }

    fun hideBottomNavigation(state: Boolean) {
        if (state) binding.bottomNavigation.visibility =
            View.GONE else binding.bottomNavigation.visibility = View.VISIBLE
    }
}
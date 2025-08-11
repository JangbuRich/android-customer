package com.project.jangburich.ui

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.project.jangburich.R
import com.project.jangburich.databinding.ActivityMainBinding
import com.project.jangburich.ui.group.GroupFragment
import com.project.jangburich.ui.home.HomeFragment
import com.project.jangburich.ui.mypage.MypageFragment
import com.project.jangburich.ui.reserve.ReserveFragment
import com.project.jangburich.ui.store.StoreMapFragment
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class MainActivity : AppCompatActivity() {
    val manager = supportFragmentManager

    lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setBottomNavigationView()

        binding.run {
            buttonJangbu.run {
                bringToFront()
                invalidate()

                // 내 장부 탭
                setOnClickListener {

                }
            }
        }

        setContentView(binding.root)
    }

    private fun setBottomNavigationView() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                // 홈 탭
                R.id.menu_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView_main, HomeFragment())
                        .commit()
                    true
                }

                // 매장 찾기 탭
                R.id.menu_store -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView_main, StoreMapFragment())
                        .commit()
                    true
                }

                // 내 장부 탭
                R.id.menu_jangbu -> {
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.fragmentContainerView_main, StoreMapFragment())
//                        .commit()
                    true
                }

                // 내 그룹 탭
                R.id.menu_group -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView_main, GroupFragment())
                        .commit()
                    true
                }

                // 마이페이지 탭
                R.id.menu_mypage -> {
                    val nextFragment = MypageFragment()

                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.fragmentContainerView_main, nextFragment)
                    transaction.addToBackStack(null)
                    transaction.commit()
                    true
                }

                // 예약 확인 탭
                /*
                R.id.menu_reserve -> {
                    val nextFragment = ReserveFragment()

                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.fragmentContainerView_main, nextFragment)
                    transaction.addToBackStack(null)
                    transaction.commit()
                    true
                }
                */

                else -> false
            }
        }
    }

    // Bottom Navigation을 "Home"으로 설정하는 함수
    fun setBottomNavigationHome() {
        binding.bottomNavigation.selectedItemId = R.id.menu_home
    }

    fun hideBottomNavigation(state: Boolean) {
        binding.run {
            if (state) {
                bottomNavigation.visibility = View.GONE
                buttonJangbu.visibility = View.GONE
            } else {
                bottomNavigation.visibility = View.VISIBLE
                buttonJangbu.visibility = View.VISIBLE
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun getKeyHash() {
        try {
            val info = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNING_CERTIFICATES)
            for (signature in info.signingInfo?.apkContentsSigners!!) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val keyHash = Base64.encodeToString(md.digest(), Base64.NO_WRAP)
                Log.d("KeyHash", keyHash)  // 키 해시를 로그로 출력
            }
        } catch (e: NoSuchAlgorithmException) {
            Log.e("KeyHash", "Unable to get MessageDigest. signature= $e")
        } catch (e: Exception) {
            Log.e("KeyHash", "Exception: $e")
        }
    }
}
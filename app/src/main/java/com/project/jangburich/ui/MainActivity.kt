package com.project.jangburich.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.project.jangburich.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val manager = supportFragmentManager

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}
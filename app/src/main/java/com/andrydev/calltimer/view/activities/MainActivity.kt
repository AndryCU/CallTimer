package com.andrydev.calltimer.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.andrydev.calltimer.R
import com.andrydev.calltimer.databinding.ActivityMainBinding
import com.andrydev.calltimer.model.entities.Alarm
import com.andrydev.calltimer.model.entities.AlarmEntity
import com.andrydev.calltimer.model.entities.toDatabase
import com.andrydev.calltimer.viewmodel.AlarmViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_CallTimer)
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        //val navController=findNavController(R.id.fragmentContainerView)
        binding.bottomNavigation.setupWithNavController(navController)
    }

}

//hdpi tiene a xhdpi
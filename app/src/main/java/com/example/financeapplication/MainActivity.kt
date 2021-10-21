package com.example.financeapplication


import android.os.Bundle


import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.example.financeapplication.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
//    private lateinit var appBarConfiguration: AppBarConfiguration //нужно узнать что это такое, мб благодаря этому я смогу вставлять меню

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setupNavigation()

    }
    private fun setupNavigation(){
        val navHostFrag =
            supportFragmentManager.findFragmentById(R.id.containerView) as NavHostFragment
        navController = navHostFrag.navController

    }

}
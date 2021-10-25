package com.example.financeapplication


import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.financeapplication.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        setupNavigation()

    }

    private fun setupNavigation() {
        val bottomNavigationView = binding.navView
        val navHostFrag =
            supportFragmentManager.findFragmentById(R.id.containerView) as NavHostFragment
        navController = navHostFrag.navController
        appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf(
                R.id.purchaseFragment,
                R.id.incomeFragment,
                R.id.goalFragment,
                R.id.statisticsFragment
            )
        )
        appBarConfiguration.fallbackOnNavigateUpListener
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.loginFragment ||
                destination.id == R.id.registrationFragment
            ) {
                bottomNavigationView.visibility = View.GONE
            } else {
                bottomNavigationView.visibility = View.VISIBLE
            }
        }
        setupWithNavController(bottomNavigationView, navController)
    }

    override fun onBackPressed() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.purchaseFragment ||
                destination.id == R.id.incomeFragment ||
                destination.id == R.id.goalFragment ||
                destination.id == R.id.statisticsFragment
            ) {
                finish()
            }
        }
    }
}
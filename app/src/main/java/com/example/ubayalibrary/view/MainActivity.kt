package com.example.ubayalibrary.view

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.ubayalibrary.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    private fun showNavigation(){
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNavigationView.visibility = View.VISIBLE

        val navView = findViewById<NavigationView>(R.id.navView)
        navView.visibility = View.GONE
    }

    fun onLoginSuccess(userId: Int, nrp: String, name: String, photoUrl:String) {
        val sharedPref = this.getSharedPreferences("Preferences", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putInt("userId", userId)
        editor.putString("nrp", nrp)
        editor.putString("name", name)
        editor.putString("photoUrl", photoUrl)
        editor.apply()
    }

    private fun hideNavigation(){
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNavigationView.visibility = View.GONE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        val navView = findViewById<NavigationView>(R.id.navView)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNav)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.hostFragment) as NavHostFragment
        navController = navHostFragment.navController

        NavigationUI.setupActionBarWithNavController(this,navController,drawerLayout)
        NavigationUI.setupWithNavController(navView,navController)

        bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener{_, destination, _ ->
            when(destination.id){
                R.id.itemLogin -> hideNavigation()
                R.id.itemRegister -> hideNavigation()
                else -> showNavigation()
            }
        }
//      Supaya tidak memunculkan draawer navigation
        setDrawerLockMode(false)
    }

    override fun onSupportNavigateUp(): Boolean {
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        return NavigationUI.navigateUp(navController,drawerLayout) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
      Toast.makeText(this, "You cannot go back", Toast.LENGTH_SHORT).show()
    }

    fun setDrawerLockMode(enabled: Boolean) {
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        val lockMode = if (enabled) DrawerLayout.LOCK_MODE_UNLOCKED else DrawerLayout.LOCK_MODE_LOCKED_CLOSED
        drawerLayout.setDrawerLockMode(lockMode)
    }
}

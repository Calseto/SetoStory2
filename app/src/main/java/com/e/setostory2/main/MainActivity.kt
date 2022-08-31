package com.e.setostory2.main

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import com.e.setostory2.R
import com.e.setostory2.add.AddStoryActivity
import com.e.setostory2.databinding.ActivityMainBinding
import com.e.setostory2.login.LoginActivity
import com.e.setostory2.map.MapActivity

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.mainAddFab.setOnClickListener {
            val intent = Intent(this,AddStoryActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    private fun logOut(){
        sharedPreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = sharedPreferences.edit()

        editor.apply{
            putString("name", null)
            putString("userid", null)
            putString("token", null)
        }.apply()

        val logIntent = Intent(this, LoginActivity::class.java)

        logIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        this.startActivity(logIntent)
        finish()
    }
    private fun openMap(){
        val inten = Intent(this, MapActivity::class.java)
        startActivity(inten)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                logOut()
                true
            }
            R.id.action_map -> {
                openMap()
                true
            }
            else -> true
        }

    }

}
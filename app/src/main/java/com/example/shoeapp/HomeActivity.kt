package com.example.shoeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() ,BottomNavigationView.OnNavigationItemSelectedListener {

    //initialize variable to refer to bottom navigation
    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        supportActionBar?.hide()

        //load up a default fragment so that the view is not empty

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container,HomeFragment()).commit()

        bottomNav = findViewById(R.id.bottomView)

        bottomNav.setOnNavigationItemSelectedListener(this)


    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.seller -> {
                val intent = Intent(applicationContext,Login::class.java)

                startActivity(intent)
            }

        }

        return true
    }
}
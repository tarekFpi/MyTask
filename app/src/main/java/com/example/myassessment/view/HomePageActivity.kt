package com.example.myassessment.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.myassessment.R
import com.example.myassessment.view.fragment.ProfileFragment
import com.example.myassessment.view.fragment.UserListFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomePageActivity : AppCompatActivity() {

    lateinit var bottomNav : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        loadFragment(UserListFragment())

        bottomNav = findViewById(R.id.bottom_navigation)
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.bottom_home -> {
                    loadFragment(UserListFragment())
                    true
                }
                R.id.bottom_profile -> {
                    loadFragment(ProfileFragment())
                    true
                }
            }
            false
        }
    }

    private  fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.framelayout_home,fragment)
        transaction.commit()
    }

    override fun onBackPressed() {
        //  super.onBackPressed()

        MaterialAlertDialogBuilder(this)
            .setTitle("Confirm")
            .setCancelable(false)
            //.setIcon(R.drawable.baseline_question_mark_24)
            .setMessage("Are you sure you want to exit?")
            .setPositiveButton("Yes"){ dialog, which ->

                this.finish()
            }
            .setNegativeButton("No"){ dialog, which ->
                dialog.dismiss()
            }
            .show()
    }
}
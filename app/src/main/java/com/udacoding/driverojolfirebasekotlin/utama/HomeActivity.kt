package com.udacoding.driverojolfirebasekotlin.utama

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.udacoding.driverojolfirebasekotlin.R
import com.udacoding.driverojolfirebasekotlin.utama.profile.ProfileFragment
import com.udacoding.driverojolfirebasekotlin.utama.home.HomeDriverFragment
import com.udacoding.driverojolfirebasekotlin.utama.home.HomeFragment
import com.udacoding.driverojolfirebasekotlin.utama.trackingservice.TrackingService
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.booking_item.*
import org.jetbrains.anko.toast

class HomeActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                setFragment(HomeDriverFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profile -> {

                setFragment(ProfileFragment())
                return@OnNavigationItemSelectedListener true
            }

        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var fragment = intent.getStringExtra("fragment")

        checkPermissions()

        setFragment(HomeDriverFragment())
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private fun checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION),1)
        }else{
            startService(Intent(this,TrackingService::class.java))
        }
    }

    fun setFragment(fragment : Fragment){

        supportFragmentManager.beginTransaction().replace(R.id.container2,fragment).commit()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 1){
            startService(Intent(this,TrackingService::class.java))
        }

    }

}

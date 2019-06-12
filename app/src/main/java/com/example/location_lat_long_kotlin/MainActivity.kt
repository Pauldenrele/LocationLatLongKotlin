package com.example.location_lat_long_kotlin

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.support.v4.app.ActivityCompat
import android.view.View
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    lateinit var locationCallback: LocationCallback

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Lines to check permissions
        val REQUEST_CODE =1000

        //If this activity is requesting for the location do this 👇
        if(ActivityCompat.shouldShowRequestPermissionRationale(this , android.Manifest.permission.ACCESS_FINE_LOCATION))
            ActivityCompat.requestPermissions(this , arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION ), REQUEST_CODE)

        /**
         * else do this 👇
         */
        else{
           buildLocationRequest()
            buildLocationCallback()

            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

            btn_start.setOnClickListener {
                View.OnClickListener {
               if (ActivityCompat.checkSelfPermission(this@MainActivity ,android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                   && ActivityCompat.checkSelfPermission(this@MainActivity ,android.Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED
               ){
                   ActivityCompat.requestPermissions(this@MainActivity , arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION) , REQUEST_CODE)
                   return@OnClickListener
               }
                    fusedLocationProviderClient.requestLocationUpdates(locationRequest , locationCallback , Looper.myLooper())

                    btn_start.isEnabled = !btn_start.isEnabled
                    btn_Stop.isEnabled = !btn_Stop.isEnabled

                }




            }

        }



    }

    private fun buildLocationCallback() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun buildLocationRequest() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

package com.example.location_lat_long_kotlin

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.support.v4.app.ActivityCompat
import android.view.View
import android.widget.Toast
import com.google.android.gms.location.*
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


        fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
        ) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)

            when(requestCode){
                REQUEST_CODE ->{

                    if(grantResults.size > 0 ){
                        if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                            Toast.makeText(this@MainActivity , "Permission granted" , Toast.LENGTH_SHORT).show()
                    }

                    else{
                        Toast.makeText(this@MainActivity , "Permission denied" , Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }

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

                btn_Stop.setOnClickListener {
                    View.OnClickListener {
                        if (ActivityCompat.checkSelfPermission(this@MainActivity ,android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(this@MainActivity ,android.Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                        ){
                            ActivityCompat.requestPermissions(this@MainActivity , arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION) , REQUEST_CODE)
                            return@OnClickListener
                        }
                        fusedLocationProviderClient.removeLocationUpdates( locationCallback)

                        btn_start.isEnabled = !btn_start.isEnabled
                        btn_Stop.isEnabled = !btn_Stop.isEnabled


                    }
                }




            }

        }



    }

    private fun buildLocationCallback() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
       locationCallback = object :LocationCallback(){
           override fun onLocationResult(p0: LocationResult?) {

               //get last location
              var location = p0!!.locations.get(p0!!.locations.size -1)

               txt_location.text = location.latitude.toString() + "/" + location.longitude.toString()

           }
       }

    }

    @SuppressLint("RestrictedApi")
    private fun buildLocationRequest()    {
        locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 5000
        locationRequest.fastestInterval = 3000
        locationRequest.smallestDisplacement = 10f


    }
}

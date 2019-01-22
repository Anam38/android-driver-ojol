package com.udacoding.driverojolfirebasekotlin.utama.trackingservice

import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.os.IBinder
import android.support.v4.content.ContextCompat
import android.util.Log
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.jetbrains.anko.toast

class TrackingService: Service() {

    var key:String? = null

    override fun onBind(intent: Intent?): IBinder? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate() {
        super.onCreate()
        requestlocation()
    }

    private fun requestlocation() {

        //get kooordinat terbaru
        var request = LocationRequest()
        //update location perdetik
        request.interval = 1000

        //setting akurasi
        request.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY

        val client = LocationServices.getFusedLocationProviderClient(this@TrackingService)
        val permission = ContextCompat.checkSelfPermission(this@TrackingService,android.Manifest.permission.ACCESS_FINE_LOCATION)

        if (permission == PackageManager.PERMISSION_GRANTED){
            Log.d("mongol","test")
            client.requestLocationUpdates(request,object : LocationCallback(){
                override fun onLocationResult(p0: LocationResult?) {
                    super.onLocationResult(p0)

                    val lat =p0?.lastLocation?.latitude
                    val lon = p0?.lastLocation?.longitude

                    insert(lat,lon)

                    Log.d("mongol","$lat,$lon")
                }
            },null)

        }
    }

    private fun insert(lat: Double?, lon: Double?) {

        val uidDriver = FirebaseAuth.getInstance().currentUser?.uid
        val database = FirebaseDatabase.getInstance()
        val reference = database.getReference("Driver")
        val query = reference.orderByChild("uid").equalTo(uidDriver)
        query.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                for (issue in p0.children){
                    key =  issue.key
                    reference.child(key?:"").child("lat").setValue(lat)
                    reference.child(key?:"").child("lon").setValue(lon)
                }
            }
        })

    }

}
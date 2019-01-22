package com.udacoding.driverojolfirebasekotlin.utama.detail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.udacoding.driverojolfirebasekotlin.R
import com.udacoding.driverojolfirebasekotlin.utama.HomeActivity
import com.udacoding.driverojolfirebasekotlin.utama.home.model.Booking
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity

class Detailorder : AppCompatActivity(), OnMapReadyCallback {

    var status :Int? = null
    var key : String? = null
    var mMap : GoogleMap? = null
    var booking: Booking? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_home)

        status = intent.getIntExtra("status",0)
        booking = intent.getSerializableExtra("booking") as Booking
        homeAwal.text = booking?.lokasiAwal.toString()
        homeTujuan.text = booking?.lokasiTujuan.toString()
        homeprice.text = booking?.harga
        homeWaktudistance.text = booking?.jarak

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapView) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val uid = booking?.tanggal
        val uidDriver = FirebaseAuth.getInstance().currentUser?.uid
        val database = FirebaseDatabase.getInstance()
        val reference = database.getReference("Order")
        val query = reference.orderByChild("tanggal").equalTo(uid)
        query.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                for (issue in p0.children){
                    key =  issue.key
                }
            }
        })
        homebuttonnext.onClick {

            if (status == 1){
                reference.child(key?:"").child("driver").setValue(uidDriver)

                reference.child(key?:"").child("status").setValue(2)
                homebuttonnext.text = "Up to Destination"
            }else{
                reference.child(key?:"").child("status").setValue(4)
            }

            startActivity<HomeActivity>("fragment" to "handle")
        }

    }

    override fun onMapReady(p0: GoogleMap?) {
        mMap = p0

        //create maker
        val locationAwal = LatLng(booking?.latAwal?: 0.0 ,booking?.lonAwal?: 0.0)
        val locationakhir = LatLng(booking?.latTujuan?: 0.0 ,booking?.lonTujuan?: 0.0)

        mMap?.addMarker(MarkerOptions().position(locationAwal).title(("lokasi Awal")).snippet(booking?.lokasiAwal))
        mMap?.addMarker(MarkerOptions().position(locationakhir).title(("Tujuan")).snippet(booking?.lokasiTujuan))

        var builder = LatLngBounds.builder()
        builder.include(locationAwal)
        builder.include(locationakhir)

        val width = resources.displayMetrics.widthPixels
        val height = resources.displayMetrics.heightPixels
        val padding = (width * 0.12).toInt()

        mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(locationAwal,14f))
        mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(locationakhir,14f))
        mMap?.uiSettings?.isZoomControlsEnabled = true
        mMap?.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(),width,height,padding))

//        route(locationAwal,locationakhir)
    }

}

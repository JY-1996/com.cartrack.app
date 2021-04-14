package com.cartrack.app.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.cartrack.app.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
    Log.e("position", intent.getStringExtra(ItemDetailFragment.ARG_LAT)!!)
        val map = LatLng(
            intent.getStringExtra(ItemDetailFragment.ARG_LAT)!!.toDouble(),
            intent.getStringExtra(ItemDetailFragment.ARG_LNG)!!.toDouble()
        )
        mMap.addMarker(MarkerOptions().position(map).title("Marker in " + intent.getStringExtra(ItemDetailFragment.ARG_CITY)))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(map))
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
}
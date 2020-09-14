/*
 * Created by Muhammad Mehroz Afzal on 2020.
 */

package com.jeeny.task.ui.map

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.jeeny.task.BR
import com.jeeny.task.R
import com.jeeny.task.databinding.ActivityMapBinding
import com.jeeny.task.ui.base.BaseActivity
import com.jeeny.task.utils.MapsUtil
import com.jeeny.task.utils.ToastUtil
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener


class MapsActivity : BaseActivity<ActivityMapBinding, MapViewModel>(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var mapViewModel: MapViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        //verifyPermissions()
    }

    private fun setupMap() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.activityMap_mapView) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModel(): MapViewModel {
        mapViewModel = ViewModelProvider(this, viewModelFactory).get(MapViewModel::class.java)
        return mapViewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_map
    }

    private fun verifyPermissions() {
        if (MapsUtil.isServicesOK(this)) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val lm = getSystemService(LOCATION_SERVICE) as LocationManager
                if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER) || lm.isProviderEnabled(
                        LocationManager.NETWORK_PROVIDER
                    )
                ) {
                    setupMap()
                } else
                    askUserToEnableLocation()
            } else {
                checkLocationPermissions()
            }
        }
    }

    private fun checkLocationPermissions() {
        Dexter.withContext(this)
            .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse) {
                    val lm = getSystemService(LOCATION_SERVICE) as LocationManager
                    if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER) || lm.isProviderEnabled(
                            LocationManager.NETWORK_PROVIDER
                        )
                    ) {
                        setupMap()
                    } else
                        askUserToEnableLocation()
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) {
                    ToastUtil.showCustomToast(this@MapsActivity, "Location Permission was denied")
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) {

                }
            }).check()
    }

    private fun askUserToEnableLocation() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Location Services")
        builder.setMessage("Please enable Location Services and GPS")
        builder.setPositiveButton(
            "YES"
        ) { _, _ ->
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
        }
        val alertDialog: Dialog = builder.create()
        alertDialog.setCanceledOnTouchOutside(false)
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
}
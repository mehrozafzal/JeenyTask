/*
 * Created by Muhammad Mehroz Afzal on 2020.
 */

package com.jeeny.task.ui.map

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.text.Html
import android.util.Log
import android.view.View
import androidx.annotation.Nullable
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
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import kotlinx.android.synthetic.main.activity_map.*

class MapActivity : BaseActivity<ActivityMapBinding, MapViewModel>(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var mapViewModel: MapViewModel
    private lateinit var mapFragment: SupportMapFragment

    companion object {
        const val REQUEST_CODE_CHECK_SETTINGS: Int = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mapFragment =
            supportFragmentManager.findFragmentById(R.id.activityMap_mapView) as SupportMapFragment
        verifyPermissions()
    }

    private fun setupMap() {
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        toggleEmptyView(false)
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

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
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
                } else {
                    toggleEmptyView(true)
                    askUserToEnableLocation()
                }
            } else {
                toggleEmptyView(true)
                askLocationPermissions()
            }
        }
    }

    private fun askLocationPermissions() {
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
                    ToastUtil.showCustomToast(this@MapActivity, "Location Permission was denied")
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) {

                }
            }).check()
    }


    private fun askUserToEnableLocation() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Location Services")
        builder.setMessage("Please enable Location Services and GPS")
        builder.setPositiveButton(
            Html.fromHtml("<font color='#56DAC7'>Yes</font>")
        ) { _, _ ->
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivityForResult(intent, REQUEST_CODE_CHECK_SETTINGS)
        }
        val alertDialog: Dialog = builder.create()
        alertDialog.setCanceledOnTouchOutside(false)
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun toggleEmptyView(show: Boolean) {
        if (show) {
            activityMap_emptyView.visibility = View.VISIBLE
        } else {
            activityMap_emptyView.visibility = View.GONE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_CODE_CHECK_SETTINGS == requestCode) {
            val lm = getSystemService(LOCATION_SERVICE) as LocationManager
            if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER) || lm.isProviderEnabled(
                    LocationManager.NETWORK_PROVIDER
                )
            ) {
                setupMap()
            } else {
                toggleEmptyView(true)
                askUserToEnableLocation()
            }
            /*  if (Activity.RESULT_OK == resultCode) {
                  setupMap()
              } else {
                  toggleEmptyView(true)
                  askUserToEnableLocation()
              }*/
        }
    }

}
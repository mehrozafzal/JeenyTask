/*
 * Created by Muhammad Mehroz Afzal on 2020.
 */

package com.jeeny.task.ui.map

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.*
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.text.Html
import android.view.View
import android.widget.AdapterView
import androidx.annotation.Nullable
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import com.jeeny.task.BR
import com.jeeny.task.R
import com.jeeny.task.databinding.ActivityMapBinding
import com.jeeny.task.repository.model.PoiListItem
import com.jeeny.task.ui.base.BaseActivity
import com.jeeny.task.ui.map.adapter.CustomInfoWindowAdapter
import com.jeeny.task.ui.map.adapter.VehicleSpinnerAdapter
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
import kotlinx.android.synthetic.main.custom_toolbar.view.*

class MapActivity : BaseActivity<ActivityMapBinding, MapViewModel>(), OnMapReadyCallback,
    GoogleMap.OnMarkerClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var mapViewModel: MapViewModel
    private lateinit var mapFragment: SupportMapFragment
    private val markers: ArrayList<Marker> = ArrayList()
    private var vehicleList: List<PoiListItem?>? = ArrayList()
    private val vehicleTypeList: ArrayList<String> = ArrayList()

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
        mMap = googleMap
        mMap.setMapStyle(
            MapStyleOptions.loadRawResourceStyle(
                this, R.raw.map_style
            )
        )
        mMap.setOnMarkerClickListener(this)
        mMap.setInfoWindowAdapter(CustomInfoWindowAdapter(this))
        vehicleSpinnerListener()
        observeVehicleResponse()
        toggleEmptyView(false)
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

    private fun observeVehicleResponse() {
        mapViewModel.getVehicleResponse().observe(this, Observer {
            when {
                it.status.isLoading() -> {
                    ToastUtil.showCustomToast(this, "Plotting vehicles...")
                }
                it.status.isSuccessful() -> {
                    if (it.data != null) {
                        if (it.data!!.poiList != null) {
                            if (it.data!!.poiList!!.isNotEmpty()) {
                                this.vehicleList = it.data!!.poiList!!
                                populateSpinner(it.data!!.poiList!!)
                            } else
                                ToastUtil.showCustomToast(this, "Fleet data not found!")
                        } else
                            ToastUtil.showCustomToast(this, "Fleet data not found!")
                    } else
                        ToastUtil.showCustomToast(this, "Fleet data not found!")
                }
                it.status.isError() -> {
                    it.errorMessage?.let { it1 -> ToastUtil.showCustomToast(this, it1) }
                }
            }
        })
    }

    private fun populateSpinner(vehicleList: List<PoiListItem?>) {
        for (item in vehicleList) {
            if (!vehicleTypeList.contains(item?.fleetType))
                vehicleTypeList.add(item?.fleetType!!)
        }
        val vehicleSpinnerAdapter =
            VehicleSpinnerAdapter(this, R.layout.item_vehicle, vehicleTypeList)
        activityMap_toolbar.toolbar_vehicleSpinner.adapter = vehicleSpinnerAdapter
    }

    private fun vehicleSpinnerListener() {
        activityMap_toolbar.toolbar_vehicleSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val item = parent?.getItemAtPosition(position) as String
                mMap.clear()
                markers.clear()
                filterDataByType(item)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }

    private fun filterDataByType(type: String) {
        vehicleList?.let {
            for (item in vehicleList!!) {
                if (item?.fleetType == type)
                    addMarkerToMap(item)
            }
            setMapBounds()
        }
    }

    private fun addMarkerToMap(poiListItem: PoiListItem) {
        val lat: Double = poiListItem.coordinate?.latitude as Double
        val lng: Double = poiListItem.coordinate.longitude as Double
        val location = LatLng(lat, lng)
        val marker = mMap.addMarker(
            MarkerOptions().position(location).title(poiListItem.fleetType)
                .snippet(
                    poiListItem.id.toString().plus("\n").plus(poiListItem.heading).plus("\n")
                        .plus(poiListItem.coordinate.toString())
                )
                .icon(BitmapDescriptorFactory.fromBitmap(MapsUtil.getMarkerBitmapFromView(this)))
        )
        marker.tag = poiListItem.id
        markers.add(marker)
    }

    private fun setMapBounds() {
        if (markers.size > 0) {
            val builder: LatLngBounds.Builder = LatLngBounds.Builder()
            for (marker in markers) {
                builder.include(marker.position)
            }
            val bounds = builder.build()
            val padding = 20
            val cu: CameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding)
            mMap.animateCamera(cu);
        }
    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        for (item in markers) {
            if (item.tag == marker?.tag) {
                val location = marker?.position?.latitude?.let {
                    marker.position?.longitude?.let { it1 ->
                        LatLng(
                            it,
                            it1
                        )
                    }
                }

                Handler().postDelayed({
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 15F))
                }, 300)
            }
        }
        return false
    }


}
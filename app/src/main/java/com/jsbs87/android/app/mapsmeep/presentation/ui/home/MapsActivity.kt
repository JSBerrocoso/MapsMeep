package com.jsbs87.android.app.mapsmeep.presentation.ui.home

import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.jsbs87.android.app.mapsmeep.BuildConfig
import com.jsbs87.android.app.mapsmeep.R
import com.jsbs87.android.app.mapsmeep.domain.model.Resource
import com.jsbs87.android.app.mapsmeep.presentation.extension.failure
import com.jsbs87.android.app.mapsmeep.presentation.extension.observe
import com.jsbs87.android.app.mapsmeep.presentation.platform.BaseActivity
import kotlinx.android.synthetic.main.activity_maps.*
import org.koin.android.scope.lifecycleScope
import org.koin.android.viewmodel.scope.viewModel

class MapsActivity : BaseActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    private val viewModel by lifecycleScope.viewModel<MapsViewModel>(this)

    override fun layoutId() = R.layout.activity_maps

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        observe(viewModel.resources, ::handleResources)
        observe(viewModel.loading, ::handleLoading)
        failure(viewModel.failure, ::showError)
    }

    private fun handleResources(resources: List<Resource>?) {
        resources?.map {
            val poi = LatLng(it.lat, it.lon)
            mMap.addMarker(MarkerOptions().position(poi).title(it.name))
        }
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val sydney = LatLng(38.7436057, -9.2302432)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Lisboa"))
        mMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                sydney,
                BuildConfig.DEFAULT_ZOOM.toFloat()
            )
        )
        mMap.setOnCameraMoveListener {
            if (!viewModel.isLoading()) {
                viewModel.loadResources(
                    BuildConfig.DEFAULT_CITY,
                    mMap.projection.visibleRegion.latLngBounds.northeast,
                    mMap.projection.visibleRegion.latLngBounds.southwest
                )
            }
        }
    }
}
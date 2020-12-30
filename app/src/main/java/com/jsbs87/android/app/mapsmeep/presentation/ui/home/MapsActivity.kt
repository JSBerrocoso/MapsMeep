package com.jsbs87.android.app.mapsmeep.presentation.ui.home

import android.os.Bundle
import android.view.View
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.maps.android.clustering.ClusterManager
import com.jsbs87.android.app.mapsmeep.BuildConfig
import com.jsbs87.android.app.mapsmeep.R
import com.jsbs87.android.app.mapsmeep.presentation.extension.failure
import com.jsbs87.android.app.mapsmeep.presentation.extension.observe
import com.jsbs87.android.app.mapsmeep.presentation.model.MeepItem
import com.jsbs87.android.app.mapsmeep.presentation.platform.BaseActivity
import com.jsbs87.android.app.mapsmeep.presentation.util.maputils.CustomClusterRenderer
import kotlinx.android.synthetic.main.activity_maps.*
import kotlinx.android.synthetic.main.bottom_sheet_content_view.*
import org.koin.android.ext.android.inject
import org.koin.android.scope.lifecycleScope
import org.koin.android.viewmodel.scope.viewModel

class MapsActivity : BaseActivity(), OnMapReadyCallback, GoogleMap.OnCameraMoveListener {

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private lateinit var clusterManager: ClusterManager<MeepItem>
    private lateinit var map: GoogleMap
    private val viewModel by lifecycleScope.viewModel<MapsViewModel>(this)
    private val defaultPosition: LatLng by inject()

    override fun layoutId() = R.layout.activity_maps

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)

        observe(viewModel.resources, ::handleResources)
        observe(viewModel.loading, ::handleLoading)
        failure(viewModel.failure, ::showError)
    }

    override fun initUI() {
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        bottomSheetBehavior = BottomSheetBehavior.from(ll_bottom_sheet)
        bottomSheetBehavior.isDraggable = false
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        bottomSheetBehavior.setPeekHeight(0, true)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        clusterManager = ClusterManager(this, map)
        clusterManager.renderer = CustomClusterRenderer(this, map, clusterManager)
        clusterManager.setOnClusterItemClickListener {
            map.animateCamera(CameraUpdateFactory.newLatLng(it.position))
            populateBottomPanel(it)
            true
        }

        map.setOnCameraMoveListener(this)
        map.setOnCameraIdleListener(clusterManager)
        map.setOnMarkerClickListener(clusterManager)
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                defaultPosition,
                BuildConfig.DEFAULT_ZOOM.toFloat()
            )
        )
    }

    override fun refresh() {
        if (!viewModel.isLoading()) {
            if (this::map.isInitialized) {

                val lowerLeftLatLon = map.projection.visibleRegion.latLngBounds.northeast
                val upperRightLatLon = map.projection.visibleRegion.latLngBounds.southwest

                if ((lowerLeftLatLon.latitude == 0.0 && lowerLeftLatLon.longitude == 0.0).not()) {
                    viewModel.loadResources(
                        BuildConfig.DEFAULT_CITY, lowerLeftLatLon, upperRightLatLon
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    private fun populateBottomPanel(itemSelected: MeepItem) {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
        txtName.text = getString(R.string.name_format, itemSelected.title)
        txtCompanyZoneId.text =
            getString(R.string.company_zone_id_format, itemSelected.companyZoneId.toString())
    }

    private fun handleResources(resources: List<MeepItem>?) {
        clusterManager.clearItems()
        clusterManager.addItems(resources)
    }

    override fun onCameraMove() {
        refresh()
    }

}
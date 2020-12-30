package com.jsbs87.android.app.mapsmeep.presentation.util.maputils

import android.content.Context
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.jsbs87.android.app.mapsmeep.presentation.model.MeepItem

class CustomClusterRenderer(
    context: Context?,
    map: GoogleMap?,
    clusterManager: ClusterManager<MeepItem>?
) : DefaultClusterRenderer<MeepItem>(context, map, clusterManager) {

    override fun onBeforeClusterItemRendered(item: MeepItem, markerOptions: MarkerOptions) {
        var bitmapDescriptor = BitmapDescriptorFactory.HUE_RED
        when (item.companyZoneId) {
            378 -> {
                bitmapDescriptor = BitmapDescriptorFactory.HUE_MAGENTA
            }
            382 -> {
                bitmapDescriptor = BitmapDescriptorFactory.HUE_BLUE
            }
            412 -> {
                bitmapDescriptor = BitmapDescriptorFactory.HUE_GREEN
            }
        }
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(bitmapDescriptor))
    }
}
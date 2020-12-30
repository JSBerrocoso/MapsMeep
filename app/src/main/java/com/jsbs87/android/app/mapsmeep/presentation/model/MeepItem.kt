package com.jsbs87.android.app.mapsmeep.presentation.model

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

class MeepItem(
    lat: Double,
    lng: Double,
    title: String,
    val companyZoneId: Int
) : ClusterItem {
    private val position: LatLng
    private val title: String

    override fun getPosition(): LatLng {
        return position
    }

    override fun getTitle(): String? {
        return title
    }

    override fun getSnippet(): String? {
        return ""
    }

    init {
        position = LatLng(lat, lng)
        this.title = title
    }
}
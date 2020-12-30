package com.jsbs87.android.app.mapsmeep.data.entity

import com.jsbs87.android.app.mapsmeep.domain.model.Resource

data class ResourceEntity(
    val id: String,
    val name: String,
    val x: Double,
    val y: Double,
    val scheduledArrival: Int,
    val locationType: Int,
    val companyZoneId: Int,
    val lat: Double,
    val lon: Double
) {

    fun toResource() =
        Resource(name, companyZoneId, lat, lon)

    companion object {
        fun empty() = Resource("", 0, 0.0, 0.0)
    }
}
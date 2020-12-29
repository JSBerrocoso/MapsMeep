package com.jsbs87.android.app.mapsmeep.domain.model

class Resource (
    val id: String,
    val name: String,
    val x: Double,
    val y: Double,
    val scheduledArrival: Int,
    val locationType: Int,
    val companyZoneId: Int,
    val lat: Double,
    val lon: Double
){
}
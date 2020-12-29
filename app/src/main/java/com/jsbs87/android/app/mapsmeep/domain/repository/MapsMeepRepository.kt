package com.jsbs87.android.app.mapsmeep.domain.repository

import com.google.android.gms.maps.model.LatLng
import com.jsbs87.android.app.mapsmeep.domain.exception.Failure
import com.jsbs87.android.app.mapsmeep.domain.model.Resource
import com.jsbs87.android.omtest.app.domain.functional.Either


interface MapsMeepRepository {

    suspend fun getResources(city: String, lowerLeftLatLon: LatLng, upperRightLatLon: LatLng): Either<Failure, List<Resource>>
}
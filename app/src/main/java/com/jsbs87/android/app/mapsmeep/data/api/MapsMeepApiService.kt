package com.jsbs87.android.app.mapsmeep.data.api

import com.jsbs87.android.app.mapsmeep.data.entity.ResourceEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MapsMeepApiService {

    @GET("routers/{city}/resources")
    fun getResourcesAsync(
        @Path("city") city: String,
        @Query("lowerLeftLatLon") lowerLeftLatLon: String,
        @Query("upperRightLatLon") upperRightLatLon: String
    ): Call<List<ResourceEntity>>

}
package com.jsbs87.android.app.mapsmeep.data.repository

import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.jsbs87.android.app.mapsmeep.data.api.MapsMeepApiService
import com.jsbs87.android.app.mapsmeep.data.entity.ResponseEntity
import com.jsbs87.android.app.mapsmeep.domain.exception.Failure
import com.jsbs87.android.app.mapsmeep.domain.model.Resource
import com.jsbs87.android.app.mapsmeep.domain.repository.MapsMeepRepository
import com.jsbs87.android.app.mapsmeep.presentation.network.NetworkHandler
import com.jsbs87.android.omtest.app.domain.functional.Either
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call

class MapsMeepRepositoryImp(
    private val apiService: MapsMeepApiService,
    private val networkHandler: NetworkHandler
) : MapsMeepRepository {

    override suspend fun getResources(
        city: String,
        lowerLeftLatLon: LatLng,
        upperRightLatLon: LatLng
    ): Either<Failure, List<Resource>> {
        val lowerLeftLatLon =
            lowerLeftLatLon.latitude.toString() + "," + lowerLeftLatLon.longitude.toString()
        val upperRightLatLon =
            upperRightLatLon.latitude.toString() + "," + upperRightLatLon.longitude.toString()
        return when (networkHandler.isInternetAvailable()) {
            true ->
                request(
                    apiService.getResourcesAsync(city, lowerLeftLatLon, upperRightLatLon),
                    { it.map { resourceEntity -> resourceEntity.toResource() } }, emptyList()
                )
            false -> Either.Left(Failure.NetworkConnection)
        }
    }


    private fun <T, R> request(
        call: Call<ResponseEntity<T>>,
        transform: (T) -> R,
        default: T
    ): Either<Failure, R> {
        return try {
            val response = call.execute()
            when (response.isSuccessful) {
                true -> Either.Right(transform((response.body()?.response ?: default)))
                false -> Either.Left(getFailure(response.errorBody()))
                else -> Either.Left(getFailure(response.errorBody()))
            }
        } catch (exception: Throwable) {
            Log.e(exception.javaClass.simpleName, "Request ${call.request().url}")
            Either.Left(Failure.ServerError)
        }
    }

    private fun getFailure(response: ResponseBody?): Failure {
        return try {
            val stringBody = response?.string()

            val failure =
                Failure.FeatureFailure.getFromCode(getErrorCodeFromBody(stringBody))
                    .apply {
                        message = stringBody
                    }
            failure
        } catch (e: Exception) {
            Failure.ServerError
        }
    }

    @Throws(Exception::class)
    fun getErrorCodeFromBody(stringBody: String?) =
        JSONObject(stringBody).getJSONObject("error").getInt("code")


}
package com.jsbs87.android.app.mapsmeep.domain.interactors

import com.google.android.gms.maps.model.LatLng
import com.jsbs87.android.app.mapsmeep.data.repository.MapsMeepRepositoryImp
import com.jsbs87.android.app.mapsmeep.domain.exception.Failure
import com.jsbs87.android.app.mapsmeep.domain.model.Resource
import com.jsbs87.android.app.mapsmeep.domain.functional.Either

class GetResourcesUseCase(private val repository: MapsMeepRepositoryImp) :
    UseCase<List<Resource>, GetResourcesUseCase.Params>() {
    override suspend fun run(params: Params): Either<Failure, List<Resource>> {
        return repository.getResources(params.city, params.lowerLeftLatLon, params.upperRightLatLon)
    }

    class Params(val city: String, val lowerLeftLatLon: LatLng, val upperRightLatLon: LatLng)

}
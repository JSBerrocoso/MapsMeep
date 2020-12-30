package com.jsbs87.android.app.mapsmeep.presentation.ui.home

import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.jsbs87.android.app.mapsmeep.domain.interactors.GetResourcesUseCase
import com.jsbs87.android.app.mapsmeep.domain.model.Resource
import com.jsbs87.android.app.mapsmeep.presentation.model.MeepItem
import com.jsbs87.android.app.mapsmeep.presentation.platform.BaseViewModel

class MapsViewModel(private val getResources: GetResourcesUseCase) : BaseViewModel() {

    val resources: MutableLiveData<List<MeepItem>> = MutableLiveData()

    fun loadResources(city: String, lowerLeftLatLon: LatLng, upperRightLatLon: LatLng) {
        showLoading()
        getResources(
            GetResourcesUseCase.Params(
                city,
                lowerLeftLatLon,
                upperRightLatLon
            )
        ) {
            hideLoading()
            it.either(::handleFailure, ::handlerResources)
        }
    }

    private fun handlerResources(result: List<Resource>) {
        resources.value = mapToMeepItem(result)
    }

    private fun mapToMeepItem(result: List<Resource>): MutableList<MeepItem> {
        val meepItems = mutableListOf<MeepItem>()
        result.map {
            meepItems.add(MeepItem(it.lat, it.lon, it.name, it.companyZoneId))
        }
        return meepItems
    }
}
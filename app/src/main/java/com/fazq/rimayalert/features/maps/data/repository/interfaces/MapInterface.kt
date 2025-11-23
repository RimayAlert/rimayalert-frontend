package com.fazq.rimayalert.features.maps.data.repository.interfaces

import com.fazq.rimayalert.core.states.DataState
import com.fazq.rimayalert.features.maps.domain.model.MapDataModel

interface MapInterface {
    suspend fun mapDetailUser(): DataState<MapDataModel>
}
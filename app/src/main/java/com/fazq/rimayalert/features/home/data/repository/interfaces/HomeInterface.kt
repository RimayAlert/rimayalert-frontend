package com.fazq.rimayalert.features.home.data.repository.interfaces

import com.fazq.rimayalert.core.states.DataState
import com.fazq.rimayalert.features.home.domain.model.IncidentModel

interface HomeInterface {

    suspend fun getIncident(): DataState<List<IncidentModel>>

}
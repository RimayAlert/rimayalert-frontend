package com.fazq.rimayalert.features.alerts.data.repository

import android.content.Context
import androidx.core.net.toUri
import com.fazq.rimayalert.core.preferences.UserPreferencesManager
import com.fazq.rimayalert.core.states.DataState
import com.fazq.rimayalert.core.utils.FileUtils
import com.fazq.rimayalert.core.utils.StringUtils
import com.fazq.rimayalert.core.utils.TokenManager
import com.fazq.rimayalert.features.alerts.data.repository.interfaces.AlertInterface
import com.fazq.rimayalert.features.alerts.data.service.AlertService
import com.fazq.rimayalert.features.alerts.domain.model.AlertModel
import com.fazq.rimayalert.features.alerts.domain.model.toRequestDTO
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class AlertRepository @Inject constructor(
    private val api: AlertService,
    private val tokenManager: TokenManager,
    private val stringUtils: StringUtils,
    @ApplicationContext private val context: Context,
    private val userPreferencesManager: UserPreferencesManager
) : AlertInterface {


    override suspend fun createAlert(alertModel: AlertModel): DataState<String> {
        val alertDTO = alertModel.toRequestDTO()
        val jsonData = Json.encodeToString(alertDTO.copy(image = null))
        val dataRequestBody = jsonData.toRequestBody("application/json".toMediaTypeOrNull())

        val imagePart = alertDTO.image?.let { uriString ->
            prepareImagePart(uriString)
        }
        val response = api.createAlert(dataRequestBody, imagePart)
        var dataState: DataState<String> = DataState.error("")
        response
            .catch { dataState = DataState.error(it.message ?: "Error") }
            .flowOn(Dispatchers.IO)
            .collect { responseState ->
                dataState = when (responseState) {
                    is DataState.Success -> {
                        DataState.success(responseState.data.message)
                    }

                    is DataState.Error -> {
                        DataState.error(responseState.message)
                    }
                }
            }
        return dataState
    }


    private fun prepareImagePart(uriString: String): MultipartBody.Part? {
        return try {
            val uri = uriString.toUri()
            val file = FileUtils.getFileFromUri(context, uri) ?: return null

            val mimeType = FileUtils.getMimeType(context, uri)
            val requestBody = file.asRequestBody(mimeType.toMediaTypeOrNull())

            MultipartBody.Part.createFormData(
                "image",
                file.name,
                requestBody
            )
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}
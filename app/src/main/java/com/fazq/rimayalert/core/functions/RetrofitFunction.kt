package com.fazq.rimayalert.core.functions

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import com.fazq.rimayalert.core.connection.response.ApiResponse
import com.fazq.rimayalert.core.connection.response.onErrorSuspend
import com.fazq.rimayalert.core.connection.response.onExceptionSuspend
import com.fazq.rimayalert.core.connection.response.onSuccessSuspend
import com.fazq.rimayalert.core.states.DataState
import com.fazq.rimayalert.core.utils.StringUtils
import com.fazq.rimayalert.features.auth.data.mapper.RegisterUserDTO
import com.fazq.rimayalert.features.common.interfaces.ErrorDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Response
import java.io.File
import java.io.IOException

fun <T> flowResponse(
    request: ApiResponse<T>,
    name: String,
    errorDao: ErrorDao,
    stringUtils: StringUtils,
): Flow<DataState<T>> {
    return flow {
        request.apply {
            this.onSuccessSuspend {
                data?.let {
                    emit(DataState.success(it, code))
                }
            }
        }.onErrorSuspend {
            emit(DataState.error(getMessageError(response), response.code()))
            insertErrorRetrofit(name, response, errorDao)
        }.onExceptionSuspend {
            if (this.exception is IOException) {
                insertErrorException(name, this.exception, errorDao)
                emit(DataState.error(stringUtils.noNetworkErrorMessage()))
            } else {
                insertErrorThrowable(name, this.exception, errorDao)
                emit(DataState.error(stringUtils.somethingWentWrong()))
            }
        }
    }
}

private fun <T> getMessageError(response: Response<T>): String {
    return try {
        return JSONObject(response.errorBody()!!.charStream().readText()).getString("message")
    } catch (e: Exception) {
        String.format("%s %s", response.code(), response.message())
    }
}


fun prepareFilePart(context: Context, partName: String, file: File): MultipartBody.Part {
    val fileUri: Uri =
        FileProvider.getUriForFile(context, context.packageName + ".fileprovider", file)
    // create RequestBody instance from file
    val requestFile: RequestBody =
        RequestBody.create(
            context.contentResolver.getType(fileUri)!!.toMediaTypeOrNull(),
            file
        )
    // MultipartBody.Part is used to send also the actual file name
    return MultipartBody.Part.createFormData(partName, file.name, requestFile)
}


fun createPartFromString(descriptionString: String?): RequestBody {
    return descriptionString!!.toRequestBody(MultipartBody.FORM)
}




class RetrofitFunction(private val appContext: Application) {
    fun prepareFilePart(partName: String, file: File): MultipartBody.Part {
        val fileUri: Uri =
            FileProvider.getUriForFile(appContext, appContext.packageName + ".fileprovider", file)
        val requestFile: RequestBody =
            RequestBody.create(
                appContext.contentResolver.getType(fileUri)!!.toMediaTypeOrNull(),
                file
            )
        return MultipartBody.Part.createFormData(partName, file.name, requestFile)
    }
}



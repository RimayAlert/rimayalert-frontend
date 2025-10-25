package com.fazq.rimayalert.core.functions

import android.util.Log
import com.fazq.rimayalert.features.common.entities.ErrorEntity
import com.fazq.rimayalert.features.common.interfaces.ErrorDao
import retrofit2.Response
import java.io.PrintWriter
import java.io.StringWriter
import java.io.Writer

suspend fun insertErrorException(name: String, exception: Exception, errorDao: ErrorDao) {
    exception.localizedMessage?.let { Log.e(name, it) }
    val writer: Writer = StringWriter()
    exception.printStackTrace(PrintWriter(writer))
    val error: String = writer.toString()
    errorDao.insertError(ErrorEntity(name = name, message = exception.message, error = error))
}

suspend fun insertErrorThrowable(name: String, throwable: Throwable, errorDao: ErrorDao) {
    throwable.localizedMessage?.let { Log.e(name, it) }
    val writer: Writer = StringWriter()
    throwable.printStackTrace(PrintWriter(writer))
    val error: String = writer.toString()
    errorDao.insertError(ErrorEntity(name = name, message = throwable.message, error = error))
}

suspend fun insertErrorRetrofit(name: String, response: Response<*>, errorDao: ErrorDao) {
    Log.e(name, response.toString())
    val errorMsg = String.format("%s %s", response.code(), response.message())
    errorDao.insertError(ErrorEntity(name = name, message = errorMsg, error = response.toString()))
}
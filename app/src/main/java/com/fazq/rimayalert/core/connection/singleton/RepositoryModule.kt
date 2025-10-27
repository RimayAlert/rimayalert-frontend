package com.fazq.rimayalert.core.connection.singleton

import android.app.Application
import com.fazq.rimayalert.core.functions.NetworkFunction
import com.fazq.rimayalert.core.functions.RetrofitFunction
import com.fazq.rimayalert.core.utils.SharedPref
import com.fazq.rimayalert.core.utils.StringUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideStringUtils(app: Application): StringUtils {
        return StringUtils(app)
    }

    @Singleton
    @Provides
    fun provideNetworkFunction(app: Application): NetworkFunction {
        return NetworkFunction(app)
    }


    @Singleton
    @Provides
    fun provideSharedPref(app: Application): SharedPref {
        return SharedPref(app)
    }


    @Singleton
    @Provides
    fun provideRetrofitFunction(app: Application): RetrofitFunction {
        return RetrofitFunction(app)
    }


}

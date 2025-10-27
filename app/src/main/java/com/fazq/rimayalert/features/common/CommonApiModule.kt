package com.fazq.rimayalert.features.common


import com.fazq.rimayalert.core.connection.database.DatabaseManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CommonApiModule {


    @Singleton
    @Provides
    fun provideErrorDao(db: DatabaseManager) = db.errorDao()

}
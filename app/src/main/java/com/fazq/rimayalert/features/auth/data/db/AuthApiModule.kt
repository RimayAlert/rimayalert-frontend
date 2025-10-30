package com.fazq.rimayalert.features.auth.data.db

import com.fazq.rimayalert.core.connection.database.DatabaseManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AuthApiModule {

    @Singleton
    @Provides
    fun provideRegisterDao(db: DatabaseManager) = db.registerUserDao()

    @Singleton
    @Provides
    fun provideAuthDao(db: DatabaseManager) = db.userDao()
}
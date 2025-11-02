package com.fazq.rimayalert.core.di

import android.content.Context
import androidx.room.Room
import com.fazq.rimayalert.core.connection.database.DatabaseManager
import com.fazq.rimayalert.core.constants.AppStrings
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, DatabaseManager::class.java, AppStrings.DATABASE_NAME).build()
}
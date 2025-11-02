package com.fazq.rimayalert.core.connection.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fazq.rimayalert.core.constants.AppStrings.DATABASE_NAME
import com.fazq.rimayalert.features.auth.domain.entities.RegisterUserEntity
import com.fazq.rimayalert.features.auth.data.db.interfaces.RegisterDao
import com.fazq.rimayalert.features.auth.data.db.interfaces.UserDao
import com.fazq.rimayalert.features.auth.domain.entities.UserEntity
import com.fazq.rimayalert.features.common.entities.ErrorEntity
import com.fazq.rimayalert.features.common.interfaces.ErrorDao


@Database(
    version = 1,
    exportSchema = true,
    entities = [
        ErrorEntity::class,
        RegisterUserEntity::class,
        UserEntity::class
    ],
//    autoMigrations = [
//        AutoMigration(from = 1, to = 2)
//    ]
)
abstract class DatabaseManager : RoomDatabase() {
    companion object {
        @Volatile
        private var INSTANCE: DatabaseManager? = null

        fun getDatabase(context: Context): DatabaseManager {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DatabaseManager::class.java,
                    DATABASE_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

    abstract fun errorDao(): ErrorDao

    abstract fun registerUserDao(): RegisterDao

    abstract fun userDao(): UserDao

}
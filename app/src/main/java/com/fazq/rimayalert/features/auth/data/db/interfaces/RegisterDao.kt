package com.fazq.rimayalert.features.auth.data.db.interfaces

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.fazq.rimayalert.features.auth.data.db.entities.RegisterUserEntity

@Dao
interface RegisterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: RegisterUserEntity)

    @Delete
    suspend fun deleteUser(user: RegisterUserEntity)

}
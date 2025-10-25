package com.fazq.rimayalert.features.common.interfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fazq.rimayalert.features.common.entities.ErrorEntity

@Dao
interface ErrorDao {
    @Query("SELECT * FROM error_table WHERE NOT send ORDER BY id DESC")
    suspend fun getErrors(): ErrorEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertError(errorEntity: ErrorEntity)

    @Query("DELETE FROM error_table")
    suspend fun deleteAllErrors()
}
package com.fazq.rimayalert.features.auth.data.db.interfaces

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fazq.rimayalert.features.auth.domain.entities.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM user_table WHERE username = :username")
    suspend fun getUser(username: String): List<UserEntity>

    @Query("SELECT * FROM user_table WHERE token = :token")
    suspend fun getUserByToken(token: String): List<UserEntity>

    @Query("SELECT * FROM user_table WHERE active = :value")
    suspend fun getUserActive(value: Boolean = true): List<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(quotes: UserEntity)

    @Query("DELETE FROM user_table WHERE id = :id")
    suspend fun deleteUserById(id: Int)

    @Delete
    suspend fun deleteUser(quotes: UserEntity)


    @Query("DELETE FROM user_table")
    suspend fun deleteAllUsers()

    @Query("UPDATE user_table SET active = :value")
    suspend fun updateInactiveAllUsers(value: Boolean = false): Int

    @Query("UPDATE user_table SET active = :value WHERE username = :username")
    suspend fun updateActiveUser(username: String, value: Boolean = true): Int

}
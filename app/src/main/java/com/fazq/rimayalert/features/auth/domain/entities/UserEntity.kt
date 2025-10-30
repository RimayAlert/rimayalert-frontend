package com.fazq.rimayalert.features.auth.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.fazq.rimayalert.features.auth.domain.model.User

@Entity(
    tableName = "user_table",
    indices = [Index(value = ["id"], unique = true)]
)
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "username") val username: String?,
    @ColumnInfo(name = "dni") val dni: String? = null,
    @ColumnInfo(name = "full_name") val fullName: String? = null,
    @ColumnInfo(name = "token") val token: String?,
    @ColumnInfo(name = "phone") val phone: String = "",
    @ColumnInfo(name = "email") val email: String = "",
    @ColumnInfo(name = "active") val active: Boolean = false,
)

fun User.toDatabase(token: String) = UserEntity(
    id,
    username,
    dni,
    fullName,
    token,
    phone,
    email,
    true
)

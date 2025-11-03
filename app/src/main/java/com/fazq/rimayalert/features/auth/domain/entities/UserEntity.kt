package com.fazq.rimayalert.features.auth.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.fazq.rimayalert.features.auth.data.mapper.UserDataDTO
import com.fazq.rimayalert.features.auth.domain.model.UserModel

@Entity(
    tableName = "user_table",
    indices = [Index(value = ["id"], unique = true)]
)
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "username") val username: String?,
    @ColumnInfo(name = "first_name") val firstName: String? = null,
    @ColumnInfo(name = "last_name") val lastName: String? = null,
    @ColumnInfo(name = "dni") val dni: String? = null,
    @ColumnInfo(name = "full_name") val fullName: String? = null,
    @ColumnInfo(name = "token") val token: String?,
    @ColumnInfo(name = "phone") val phone: String = "",
    @ColumnInfo(name = "email") val email: String = "",
    @ColumnInfo(name = "alias_name") val aliasName: String? = null,
    @ColumnInfo(name = "active") val active: Boolean = false,
)

fun UserModel.toDatabase(token: String) = UserEntity(
    id,
    username,
    firstName,
    lastName,
    dni,
    fullName,
    token,
    phone,
    email,
    aliasName,
    active
)

fun UserDataDTO.toEntity(token: String): UserEntity {
    return UserEntity(
        id = this.id,
        username = this.username,
        firstName = this.firstName,
        lastName = this.lastName,
        dni = this.dni,
        fullName = this.fullName,
        token = token,
        phone = "",
        email = this.email,
        aliasName = this.aliasName,
        active = this.isActive
    )
}

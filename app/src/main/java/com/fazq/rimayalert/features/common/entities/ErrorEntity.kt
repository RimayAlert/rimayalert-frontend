package com.fazq.rimayalert.features.common.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fazq.rimayalert.core.functions.getDateTimeString

@Entity(tableName = "error_table")
data class ErrorEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "datetime") val datetime: String? = getDateTimeString(),
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "message") val message: String?,
    @ColumnInfo(name = "error") val error: String?,
    @ColumnInfo(name = "send") val send: Boolean? = false,
)
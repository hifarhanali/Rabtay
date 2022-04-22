package com.example.rabtay

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "contact")
data class Contact (

    @PrimaryKey
    val id: String,

    val name: String,

    var number: String,

    @ColumnInfo(name="profile_photo")
    val profilePhoto: Bitmap? = null
)
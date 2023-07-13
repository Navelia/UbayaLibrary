package com.example.ubayalibrary.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Book(
    var id:String?,
) {
    @PrimaryKey(autoGenerate = true)
    var uuid:Int = 0
}
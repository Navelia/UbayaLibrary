package com.example.ubayalibrary.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    var id:String?,
) {
    @PrimaryKey(autoGenerate = true)
    var uuid:Int = 0
}
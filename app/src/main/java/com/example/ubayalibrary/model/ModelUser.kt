package com.example.ubayalibrary.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class User(
    var nrp:Int?,
    var nama:String?,
    var password:String?,
    var photoUrl:String?
    ) {
    @PrimaryKey(autoGenerate = true)
    var uuid:Int = 0
}
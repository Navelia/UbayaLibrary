package com.example.ubayalibrary.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Journal(
    var judul:String?,
    var penulis:String?,
    var tahun:Int?,
    var abstrak:String?,
    var letak:String?,
    var kataKunci:String?
) {
    @PrimaryKey(autoGenerate = true)
    var uuid:Int = 0
}
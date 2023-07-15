package com.example.ubayalibrary.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Book(
    var id:Int?,
    var judul:String?,
    var penulis:String?,
    var tahun:Int?,
    var sinopsis:String?,
    var photoUrl:String?
) {
    @PrimaryKey(autoGenerate = true)
    var uuid:Int = 0
}
package com.example.ubayalibrary.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Rental(
    var bookId:String?,
//    Nama buku taruh sini supaya ga usah join ke entitas buku
    var namaBuku:String?,
    var userNrp:String?,
    var tanggalSewa:String?,
    var tanggalPengembalian:String?,
) {
    @PrimaryKey(autoGenerate = true)
    var uuid:Int = 0
}
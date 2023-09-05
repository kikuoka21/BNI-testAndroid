package com.example.bni_test.modal

data class Transaksi(
    val id: Long,
    val timeStamp: Long,
    val tipeMutasi: Int,
    val nominal: Long,
    val keterangan: String,
    val saldo: Long
)

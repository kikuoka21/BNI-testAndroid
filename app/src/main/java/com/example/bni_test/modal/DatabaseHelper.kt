package com.example.bni_test.modal

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_VERSION = 3
        const val DATABASE_NAME = "bni-test.db"


        const val TABLE_TRANSAKSI = "ttransaksi"

        const val COLUMN_ID_TRANSAKSI = "id_transaksi"
        const val COLUMN_TIMESTAMP = "timestamp"
        const val COLUMN_TIPE_MUTASI = "tipe_mutasi"
        /**
         * 1 or 2
         * 1 debet artinya user melakukan pengurangan saldo
         * 2 kredit artinya user melakukan penambahan saldo*/
        const val COLUMN_NOMINAL = "nominal"
        const val COLUMN_KETERANGAN = "keterangan"
        const val COLUMN_SALDO = "saldo"


    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableUser = """
                CREATE TABLE $TABLE_TRANSAKSI(
                $COLUMN_ID_TRANSAKSI INTEGER PRIMARY KEY,
                $COLUMN_TIMESTAMP INTEGER,
                $COLUMN_TIPE_MUTASI INTEGER,
                $COLUMN_NOMINAL INTEGER,
                $COLUMN_KETERANGAN TEXT,
                $COLUMN_SALDO INTEGER)
                """.trimIndent()
        db?.execSQL(createTableUser)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL("DROP TABLE IF EXISTS $TABLE_TRANSAKSI")

        onCreate(p0)
    }


     fun findSaldoTerakhir(): Int {
        val db = readableDatabase
        var saldoTerakhir = 0


        val query =
            "SELECT $COLUMN_SALDO FROM $TABLE_TRANSAKSI ORDER BY $COLUMN_TIMESTAMP DESC LIMIT 1"
        val cursor: Cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            saldoTerakhir = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SALDO))
        }
        cursor.close()


        db.close()
        return saldoTerakhir
//         return 1000000000
    }


    fun addRecord(tanggal: Long, tipeMutasi: Int, nominal: Long, keterangan: String) :Long{

        // Mencari saldo terakhir
        val saldoTerakhir = findSaldoTerakhir()

        val db = writableDatabase
        val values = ContentValues()

        values.put(COLUMN_TIMESTAMP, tanggal)
        values.put(COLUMN_TIPE_MUTASI, tipeMutasi)
        values.put(COLUMN_NOMINAL, nominal)
        values.put(COLUMN_KETERANGAN, keterangan)
        values.put(COLUMN_SALDO, prosesSaldo(tipeMutasi, saldoTerakhir, nominal))

        val newRowId = db.insert(TABLE_TRANSAKSI, null, values)
//        val newRowId = 1L

        db.close()
        return newRowId
    }
    fun isBisaTransaksi(tipe:Int,nominal:Long):Boolean{

        return prosesSaldo(tipe, findSaldoTerakhir(), nominal)>-1
    }

    private fun prosesSaldo(tipe:Int, saldoTerakhir:Int, nominal:Long):Long{
        when(tipe){
            1->return saldoTerakhir-nominal
            2->return saldoTerakhir+nominal
        }
        return 0
    }

    fun getAllTransactions(): ArrayList<Transaksi> {
        val transactions = ArrayList<Transaksi>()
        val db = readableDatabase

        val query = "SELECT * FROM $TABLE_TRANSAKSI ORDER BY $COLUMN_TIMESTAMP DESC"
        val cursor: Cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID_TRANSAKSI))
                val tanggal = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_TIMESTAMP))
                val tipeMutasi = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TIPE_MUTASI))
                val nominal = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_NOMINAL))
                val keterangan = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_KETERANGAN))
                val saldo = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_SALDO))

                val transaction = Transaksi(id, tanggal, tipeMutasi, nominal, keterangan, saldo)
                transactions.add(transaction)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return transactions
    }
}
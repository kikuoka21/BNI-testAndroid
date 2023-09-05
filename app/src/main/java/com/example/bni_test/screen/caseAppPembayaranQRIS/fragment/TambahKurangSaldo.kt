package com.example.bni_test.screen.caseAppPembayaranQRIS.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.bni_test.R
import com.example.bni_test.modal.DatabaseHelper

class TambahKurangSaldo : Fragment() {
    private lateinit var databaseHelper: DatabaseHelper

    override fun onAttach(context: Context) {
        super.onAttach(context)
        databaseHelper = DatabaseHelper(context)
    }

    private lateinit var editNominal: EditText
    private lateinit var buttonTambah: Button
    private lateinit var buttonKurang: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tambahtransaksi, container, false)
        buttonTambah = view.findViewById(R.id.buttonTambah)
        buttonKurang = view.findViewById(R.id.buttonKurang)
        editNominal = view.findViewById(R.id.nominal)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        buttonTambah.setOnClickListener {

            val newRecord = databaseHelper.addRecord(
                System.currentTimeMillis(),
                2,
                editNominal.text.toString().toLong(),
                "Tambah Saldo"
            )
            var pesan = "Penambahan Saldo Gagal"
            if (newRecord != -1L) {
                pesan = "Penambahan Saldo Berhasil"
            }
            Toast.makeText(view.context, pesan, Toast.LENGTH_SHORT).show()

            editNominal.setText("")
        }
        buttonKurang.setOnClickListener {
            val nominal = editNominal.text.toString().toLong()
            if (databaseHelper.isBisaTransaksi(1, nominal)) {
                val newRecord = databaseHelper.addRecord(
                    System.currentTimeMillis(),
                    1,
                    nominal,
                    "Tarik Saldo"
                )
                var pesan = "Penarikan Saldo Gagal"
                if (newRecord != -1L) {
                    pesan = "Penarikan Saldo Berhasil"
                }
                Toast.makeText(view.context, pesan, Toast.LENGTH_SHORT).show()
                editNominal.setText("")
            }else{
                Toast.makeText(view.context, "Saldo Anda kurang", Toast.LENGTH_SHORT).show()
            }

        }

        editNominal.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Tidak melakukan apa-apa setelah teks berubah
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Tidak melakukan apa-apa sebelum teks berubah
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val nominalText = s.toString()

                val isNonEmpty = nominalText.isNotEmpty()
                val isIntegerGreaterThanZero = try {
                    val nominalInt = Integer.parseInt(nominalText)
                    nominalInt > 0
                } catch (e: NumberFormatException) {
                    false
                }

                buttonTambah.isEnabled = isNonEmpty && isIntegerGreaterThanZero
                buttonKurang.isEnabled = isNonEmpty && isIntegerGreaterThanZero
            }
        })
    }


}
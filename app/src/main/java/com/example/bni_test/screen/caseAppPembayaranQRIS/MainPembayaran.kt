package com.example.bni_test.screen.caseAppPembayaranQRIS

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.example.bni_test.R
import com.example.bni_test.modal.DatabaseHelper
import com.example.bni_test.screen.caseAppPembayaranQRIS.fragment.Beranda
import com.example.bni_test.screen.caseAppPembayaranQRIS.fragment.RiwayatPembayaran
import com.example.bni_test.screen.caseAppPembayaranQRIS.fragment.TambahKurangSaldo
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainPembayaran : AppCompatActivity() {

    private var cameraPermissionCode = 101
    private var permissionDeniedCount = 0

    private lateinit var bottomNav: BottomNavigationView
    private var menuKe = 0
    private lateinit var databaseHelper: DatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_page)
        databaseHelper = DatabaseHelper(this)


        bottomNav = findViewById(R.id.bottom_navigation)
        bottomNav.setOnItemSelectedListener { item: MenuItem ->
            if (checkCameraPermission()) {
                when (item.itemId) {
                    R.id.navHome -> {
                        if (menuKe != 0) {
                            menuKe = 0

                            supportFragmentManager.beginTransaction().replace(
                                R.id.fragment_container,
                                Beranda()
                            ).commit()
                        }
                    }

                    R.id.navQRScan -> {


                        startActivity(Intent(this, ScanQR::class.java))
                        return@setOnItemSelectedListener false
                    }
                    R.id.navHisTransaksi -> {
                        if (menuKe != 3) {
                            menuKe = 3
                        supportFragmentManager.beginTransaction().replace(
                            R.id.fragment_container,
                            RiwayatPembayaran()
                        ).commit()
                        }
                    }
                    R.id.navNabung -> {
                        if (menuKe != 2) {
                            menuKe = 2
                            supportFragmentManager.beginTransaction().replace(
                                R.id.fragment_container,
                                TambahKurangSaldo()
                            ).commit()
                        }
                    }

                }

                return@setOnItemSelectedListener true
            } else {
                requestCameraPermission()
                return@setOnItemSelectedListener false
            }
        }
        supportFragmentManager.beginTransaction().replace(
            R.id.fragment_container,
            Beranda()
        ).commit()
        if (!checkCameraPermission()) {
            requestCameraPermission()
        }


    }


    private fun checkCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }


    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA),
            cameraPermissionCode
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == cameraPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Izin kamera diberikan oleh pengguna.
            } else {
                AlertDialog.Builder(this)
                    .setTitle("Permission Request")
                    .setMessage("Wajib Memberikan Akses")
                    .setCancelable(false)
                    .setPositiveButton("Tutup") { _, _ ->
                        finish()
                    }
                    .setNegativeButton("Izinkan") { _, _ ->

                        permissionDeniedCount++
                        if (permissionDeniedCount < 2 && shouldShowRequestPermissionRationale(
                                Manifest.permission.CAMERA
                            )
                        ) {
                            requestCameraPermission()
                        } else {
                            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                            intent.data = Uri.parse("package:$packageName")
                            startActivity(intent)
                        }
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        intent.data = Uri.parse("package:$packageName")
                        startActivity(intent)
                    }
                    .show()
            }
        }
    }
}





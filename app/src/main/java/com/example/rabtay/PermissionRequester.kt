package com.example.rabtay

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionRequester(private val ctx: MainActivity) {
    companion object {
        const val REQUEST_PERMISSION_CODE = 101
        val requestPermissions = arrayOf(
            Manifest.permission.READ_CONTACTS
        )
    }

    fun hasReadContactsPermission() =
        ContextCompat.checkSelfPermission(
            ctx,
            Manifest.permission.READ_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED

    fun requestReadContactsPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                ctx,
                Manifest.permission.READ_CONTACTS
            )
        ) {
            Toast.makeText(
                ctx,
                "Read contacts permission is needed to display contacts",
                Toast.LENGTH_SHORT
            ).show()
        }
        else{
            ActivityCompat.requestPermissions(ctx, requestPermissions, REQUEST_PERMISSION_CODE)
        }
    }
}
package com.example.rabtay

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionRequester(private val ctx: MainActivity) {
    companion object {
        private const val REQUEST_PERMISSION_CODE = 101
        private val requestPermissions = arrayOf(
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
        ActivityCompat.requestPermissions(ctx, requestPermissions, REQUEST_PERMISSION_CODE)
    }

    fun onRequestPermissionsResult(
        requestCode: Int,
        grantResults: IntArray,
        readContacts: () -> Unit
    ) = if (requestCode == REQUEST_PERMISSION_CODE) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            readContacts()
        } else {
            Toast.makeText(ctx, "Permission was not granted", Toast.LENGTH_SHORT).show()
        }
        true
    } else false


}
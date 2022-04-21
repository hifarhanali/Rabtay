package com.example.rabtay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val permissionRequester = PermissionRequester(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val contactsListRecyclerView =
            this.findViewById<RecyclerView>(R.id.contacts_list_recyclerview)

        lateinit var contactsList: List<Contact>
        if (permissionRequester.hasReadContactsPermission()) {
            contactsList = readContacts()
        } else {
            permissionRequester.requestReadContactsPermission()
        }
        contactsListRecyclerView.adapter = ContactsAdaptor(contactsList)
        contactsListRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun readContacts() = ContactsFetcher(this.contentResolver).fetch()

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (!permissionRequester.onRequestPermissionsResult(
                requestCode,
                grantResults,
                ::readContacts
            )
        ) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }
}
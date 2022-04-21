package com.example.rabtay

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.media.MediaDataSource
import android.provider.ContactsContract
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.net.toUri
import kotlin.contracts.contract

class ContactsFetcher(private val contentResolver: ContentResolver) {
    @SuppressLint("Range")
    fun fetch(): List<Contact> {
        val contactsList = mutableListOf<Contact>()

        val getContactsProjection: Array<String> = arrayOf(
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.Contacts.HAS_PHONE_NUMBER,
            ContactsContract.Contacts.Photo.PHOTO_URI
        )

        val contactCursor = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            null
        );

        contactCursor.use { contactCursor ->
            if (contactCursor != null) {
                while (contactCursor.moveToNext()) {
                    val id =
                        contactCursor.getString(contactCursor.getColumnIndex(getContactsProjection[0]))
                    val name =
                        contactCursor.getString(contactCursor.getColumnIndex(getContactsProjection[1]))
                    val hasPhoneNumber =
                        contactCursor.getInt(contactCursor.getColumnIndex(getContactsProjection[2]))
                    val profilePhotoUri =
                        contactCursor.getString(contactCursor.getColumnIndex(getContactsProjection[3]))

                    if (hasPhoneNumber > 0) {
                        val phoneCursor = contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            arrayOf(id),
                            null
                        )

                        phoneCursor.use { phoneCursor ->
                            if (phoneCursor != null && phoneCursor.moveToNext()) {
                                Log.d("Fetcher", "$id $name $hasPhoneNumber")
                                contactsList.add(
                                    Contact(
                                        id,
                                        name,
                                        phoneCursor.getString(
                                            phoneCursor.getColumnIndex(
                                                ContactsContract.CommonDataKinds.Phone.NUMBER
                                            )
                                        ),
                                        if(profilePhotoUri != null) MediaStore.Images.Media.getBitmap(contentResolver, profilePhotoUri.toUri()) else null
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }

        Log.d("Fetcher", "${contactsList.size}")


        return contactsList;
    }

}
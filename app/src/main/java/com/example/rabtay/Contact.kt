package com.example.rabtay

import android.graphics.Bitmap

data class Contact (val id: String, val name: String, var number: String, val profilePhoto: Bitmap? = null)
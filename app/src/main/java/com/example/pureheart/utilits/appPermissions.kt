package com.example.pureheart.utilits

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PackageManagerCompat

const val READ_CONTACTS = Manifest.permission.READ_CONTACTS
const val PERMISSION_REQUEST = 200
fun checkPermission(permission: String):Boolean{
    return if(Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(APP_ACTIVITY,permission)!= PackageManager.PERMISSION_GRANTED){
       ActivityCompat.requestPermissions(APP_ACTIVITY, arrayOf(permission), PERMISSION_REQUEST)
        false
    }else true
}
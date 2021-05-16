package com.testapp.testapplication.utility.photo

import android.graphics.Bitmap
import android.net.Uri
import java.io.File

interface PhotoUtility {

    fun getPhotoRotation(uri: Uri): Float

    fun getPhotoByUri(uri: Uri): Bitmap

    fun getResizedPhoto(photo: Bitmap, newWidth: Int, newHeight: Int): Bitmap

    fun savePhotoAsJpg(bitmap: Bitmap, filename: String) : File
    fun toSquarePhoto(bitmap: Bitmap): Bitmap
    fun rotatePhoto(bitmap: Bitmap, degress: Float): Bitmap
}


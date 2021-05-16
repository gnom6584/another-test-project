package com.testapp.testapplication.utility.photo

import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.provider.MediaStore
import com.testapp.testapplication.utility.FileUtils
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception


class PhotoUtilityImpl(val context: Context) : PhotoUtility {

    override fun getPhotoRotation(uri: Uri) = try {
        when (ExifInterface(FileUtils.getPath(context, uri)).getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)) {
            ExifInterface.ORIENTATION_ROTATE_270 -> 270f
            ExifInterface.ORIENTATION_ROTATE_180 -> 180f
            ExifInterface.ORIENTATION_ROTATE_90 -> 90f
            else -> 0f
        }
    }
    catch (ex: Exception) {
        90f
    }

    override fun toSquarePhoto(bitmap: Bitmap) : Bitmap {
        val width = bitmap.width
        val height = bitmap.height

        if(width == height)
            return Bitmap.createBitmap(bitmap)

        if(width > height) {
            val offset = (width - height) / 2
            return Bitmap.createBitmap(bitmap, offset, 0, width - offset * 2,  height)
        }

        val offset = (height - width) / 2
        return Bitmap.createBitmap(bitmap, 0, offset, width,  height - offset * 2)
    }

    override fun rotatePhoto(bitmap: Bitmap, degress: Float) : Bitmap {
        val matrix = Matrix()
        matrix.preRotate(degress)
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    override fun getPhotoByUri(uri: Uri): Bitmap
            = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)

    override fun getResizedPhoto(bitmap: Bitmap, newWidth: Int, newHeight: Int): Bitmap
            = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true)

    override fun savePhotoAsJpg(bitmap: Bitmap, filename: String) : File {
        val f = File(context.cacheDir, filename);
        f.createNewFile();
        val bos = ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0 /*ignored for PNG*/, bos);
        val bitmapData = bos.toByteArray();
        val fos = FileOutputStream(f);
        fos.write(bitmapData);
        fos.flush();
        fos.close();
        return f;
    }
}
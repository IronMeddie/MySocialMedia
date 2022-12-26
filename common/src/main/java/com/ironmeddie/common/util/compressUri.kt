package com.ironmeddie.common.util

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.util.Size
import android.widget.Toast
import java.io.ByteArrayOutputStream

fun compressUri(uri: Uri?, context: Context): Uri? {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && uri !=null) {
        try {
            val thumbnail: Bitmap = context.contentResolver.loadThumbnail(uri, Size(1000, 1000), null)
            val outputStream = ByteArrayOutputStream()
            thumbnail.compress(Bitmap.CompressFormat.JPEG,100,outputStream)
            val path = MediaStore.Images.Media.insertImage(context.contentResolver,thumbnail,"val","null")
            outputStream.close()
            return Uri.parse(path)
        }catch (e: Exception){
            Log.d("checkCode", "exception while compressing " + e.localizedMessage)
        }
    }else{
        Toast.makeText(context,"target sdk is higher then yours", Toast.LENGTH_SHORT).show()
    }
    return uri
}
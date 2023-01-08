package com.example.deuHack.ui

import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.pointer.pointerInput

object Utils {
    @JvmStatic
    fun getToken(context: Context):String{
        return context.applicationContext
            .getSharedPreferences("accessToken", Context.MODE_PRIVATE)
            .getString("accessToken","")!!
    }

    @JvmStatic
    fun Modifier.addFocusCleaner(focusManager: FocusManager, doOnClear: () -> Unit = {}): Modifier {
        return this.pointerInput(Unit) {
            detectTapGestures(onTap = {
                doOnClear()
                focusManager.clearFocus()
            })
        }
    }
}

fun absolutelyPath(path: Uri?, context : Context): String {
    var proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
    var c: Cursor? = context.contentResolver.query(path!!, proj, null, null, null)
    var index = c?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
    c?.moveToFirst()

    var result = c?.getString(index!!)

    return result!!
}

@Suppress("DEPRECATION", "NewApi")
private fun Uri.parseBitmap(context: Context): Bitmap {
    return when (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) { // 28
        true -> {
            val source = ImageDecoder.createSource(context.contentResolver, this)
            ImageDecoder.decodeBitmap(source)
        }
        else -> {
            MediaStore.Images.Media.getBitmap(context.contentResolver, this)
        }
    }
}
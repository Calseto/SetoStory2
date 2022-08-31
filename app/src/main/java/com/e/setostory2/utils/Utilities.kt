package com.e.setostory2.utils

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import java.io.*
import java.text.SimpleDateFormat
import java.util.*


private const val FILENAME_FORMAT = "dd-MMM-yyyy"

val currentTime: String = SimpleDateFormat(FILENAME_FORMAT,Locale.US).format(System.currentTimeMillis())

fun createTempFile(context: Context): File {
    val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(currentTime, ".jpg", storageDir)
}

fun compress(file: File): File {
    val bitmap = BitmapFactory.decodeFile(file.path)
    var quality = 100
    var streamLength: Int
    do {
        val bmpStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, bmpStream)
        val bmpPicByteArray = bmpStream.toByteArray()
        streamLength = bmpPicByteArray.size
        quality -= 4
    } while (streamLength > 500000)
    bitmap.compress(Bitmap.CompressFormat.JPEG, quality, FileOutputStream(file))
    return file
}

fun uriToFile(selectedImg: Uri, context: Context): File {
    val contentResolver: ContentResolver = context.contentResolver
    val file = createTempFile(context)
    val input = contentResolver.openInputStream(selectedImg) as InputStream
    val output: OutputStream = FileOutputStream(file)
    var len: Int
    val buf = ByteArray(1024)
    while (input.read(buf).also { len = it } > 0) output.write(buf, 0, len)
    output.close()
    input.close()
    return file
}


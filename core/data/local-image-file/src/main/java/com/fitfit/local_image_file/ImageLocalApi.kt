package com.fitfit.local_image_file

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.runBlocking
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlin.math.sqrt

private const val LOCAL_IMAGE_TAG = "Local-Storage-Image"

private const val IMAGE_MAX_SIZE_MB = 3.0    //Mebibyte
private const val PROFILE_IMAGE_MAX_SIZE_MB = 0.2    //Mebibyte

class ImageLocalApi @Inject constructor(
    @ApplicationContext private val context: Context
): ImageLocalDataSource {
    override fun saveImageToInternalStorage(
        userId: Int,
        index: Int,
        uri: Uri,
        isProfileImage: Boolean
    ): String? {
        //convert uri to bitmap
        val bitmap = getBitMapFromUri(uri)

        //resize bitmap
        val newBitmap = resizeBitmap(isProfileImage, bitmap, uri)

        //make file name : userId date time index
        val fileName = getImageFileName(userId, isProfileImage, index)

        //save
        return try{
            context.openFileOutput(fileName, Context.MODE_PRIVATE).use { stream ->
                val quality = if (isProfileImage) 70 else 80

                if (!newBitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream)){
                    throw IOException("Couldn't save bitmap")
                }
            }
            fileName

        } catch(e: IOException){
            e.printStackTrace()
            null
        }
    }

    override fun saveImageToExternalStorage(
        imageFileName: String
    ): Boolean {
        val internalImageFile = File(context.filesDir, imageFileName)

        if (!internalImageFile.exists())
            return false

        val inputStream: InputStream = FileInputStream(internalImageFile)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        inputStream.close()

        val imageCollection =
            if (Build.VERSION.SDK_INT >= 29) MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
            else MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "${imageFileName.substringAfter("_")}.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.RELATIVE_PATH, "DCIM/bannerit")
            put(MediaStore.Images.Media.WIDTH, bitmap.width)
            put(MediaStore.Images.Media.HEIGHT, bitmap.height)
        }

        return try {
            context.contentResolver.insert(imageCollection, contentValues)?.also { uri ->
                context.contentResolver.openOutputStream(uri).use { outputStream ->
                    if (outputStream == null || !bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)) {
                        throw IOException("Couldn't save bitmap")
                    }
                }
            } ?: throw IOException("Couldn't create Media store entry")
            true
        }
        catch (e:IOException){
            e.printStackTrace()
            false
        }
    }

    override fun deleteFilesFromInternalStorage(
        files: List<String>
    ){
        files.forEach {
            deleteFileFromInternalStorage(it)
        }
    }

    override fun deleteAllImagesFromInternalStorage(

    ){
        Log.d(LOCAL_IMAGE_TAG, "delete all images from local storage")

        try {
            val fileList = context.filesDir.listFiles()

            for (file in fileList!!) {
                if (file.isFile && file.extension.equals("jpg", ignoreCase = true)) {
                    file.delete()
                }
            }

        } catch (e: Exception) {
            Log.e(LOCAL_IMAGE_TAG, "delete all images from local storage fail - ", e)
        }
    }

    override fun deleteUnusedProfileImageFiles(
        usingProfileImage: String?
    ){
        //get all .jpg files in internal storage
        val internalStorageDir = context.filesDir
        val allImageFiles = internalStorageDir.listFiles { file ->
            file.isFile && file.extension.equals("jpg", ignoreCase = true)
        }

        //filter unused profile image
        val unusedImageFiles = allImageFiles?.filter {
            "profile_" in it.name && it.name != usingProfileImage
        } ?: listOf()

        //delete unused image files
        deleteFilesFromInternalStorage(
            files = unusedImageFiles.map { it.name }
        )
    }
























    private fun getBitMapFromUri(
        uri: Uri,
    ): Bitmap {
        return if (Build.VERSION.SDK_INT >= 28) {
            val source = ImageDecoder.createSource(context.contentResolver, uri)
            ImageDecoder.decodeBitmap(source)
        }
        else{
            MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        }
    }

    private fun resizeBitmap(
        isProfileImage: Boolean,
        bitmap: Bitmap,
        uri: Uri
    ): Bitmap {
        var imageFileSize: Float = 0f
        runBlocking {
            imageFileSize = getFileSizeFromUri(uri)
        }

        val width = bitmap.width
        val height = bitmap.height


        //if is over 2.3 Mebibyte
        var scale: Double = 1.0

        if (isProfileImage && imageFileSize > PROFILE_IMAGE_MAX_SIZE_MB){
            scale = sqrt(PROFILE_IMAGE_MAX_SIZE_MB / imageFileSize)
        }
        else if (imageFileSize > IMAGE_MAX_SIZE_MB){
            scale = sqrt(IMAGE_MAX_SIZE_MB / imageFileSize)
        }


        return if (scale < 1.0){
            if (scale < 0.01f){
                scale = 0.01
            }

            val newBitmap = Bitmap.createScaledBitmap(
                bitmap,
                (width * scale).toInt(),
                (height * scale).toInt(),
                true
            )

            newBitmap
        }
        else
            bitmap
    }

    private fun getFileSizeFromUri(
        uri: Uri
    ): Float {

        var fileSize: Long = 0

        context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)

            cursor.moveToFirst()

            fileSize = cursor.getLong(sizeIndex)
        }

        //Byte to Mebibyte
        return fileSize.toFloat() / 1024 / 1024
    }

    private fun getImageFileName(
        userId: Int,
        isProfileImage: Boolean,
        index: Int
    ): String {
        val now = ZonedDateTime.now(ZoneId.of("UTC"))
        val dateTime = now.format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmssSSS"))

        return if (isProfileImage) "profile_${userId}_${dateTime}.jpg"
                else "${userId}_${dateTime}_${index+1}.jpg"

        //profile_1_231011_103012157.jpg
        //1_231011_103012157_0.jpg
    }

    private fun deleteFileFromInternalStorage(
        filePath: String
    ): Boolean{
        return try {
            context.deleteFile(filePath)
        } catch (e: Exception){
            e.printStackTrace()
            false
        }
    }
}
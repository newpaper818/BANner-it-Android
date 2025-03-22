package com.fitfit.core.data.data.repository

import android.net.Uri
import com.fitfit.local_image_file.ImageLocalDataSource
import javax.inject.Inject

class ImageRepository @Inject constructor(
    private val imageLocalDataSource: ImageLocalDataSource,
){
    fun saveImageToInternalStorage(
        userId: Int,
        index: Int,
        uri: Uri,
        isProfileImage: Boolean = false
    ): String?{
        return imageLocalDataSource.saveImageToInternalStorage(
            userId = userId,
            index = index,
            uri = uri,
            isProfileImage = isProfileImage
        )
    }

    fun saveImageToExternalStorage(
        imageFileName: String
    ): Boolean{
        return imageLocalDataSource.saveImageToExternalStorage(
            imageFileName = imageFileName
        )
    }

    fun deleteFilesFromInternalStorage(
        files: List<String>
    ){
        imageLocalDataSource.deleteFilesFromInternalStorage(
            files = files
        )
    }

    fun deleteAllImagesFromInternalStorage(){
        imageLocalDataSource.deleteAllImagesFromInternalStorage()
    }

    fun deleteUnusedProfileImageFiles(
        usingProfileImage: String?
    ){
        imageLocalDataSource.deleteUnusedProfileImageFiles(
            usingProfileImage = usingProfileImage
        )
    }
}
package com.fitfit.local_image_file

import android.net.Uri

interface ImageLocalDataSource {
    /**
     * when user choose image from gallery
     *
     * before upload image to remote
     */
    fun saveImageToInternalStorage(
        userId: Int,
        index: Int,
        uri: Uri,
        isProfileImage: Boolean = false
    ): String?

    /**
     * download to external storage
     *
     * user can see image on gallery app or files
     */
    fun saveImageToExternalStorage(
        imageFileName: String
    ): Boolean


    fun deleteFilesFromInternalStorage(
        files: List<String>
    )


    fun deleteAllImagesFromInternalStorage()


//    fun deleteUnusedImageFilesForAllTrips(
//        allTrips: List<Trip>
//    )


    fun deleteUnusedProfileImageFiles(
        usingProfileImage: String?
    )
}
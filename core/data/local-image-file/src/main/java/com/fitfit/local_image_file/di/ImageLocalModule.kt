package com.fitfit.local_image_file.di

import com.fitfit.local_image_file.ImageLocalApi
import com.fitfit.local_image_file.ImageLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ImageLocalModule {
    @Binds
    internal abstract fun bindCommonDataSource(
        imageLocalApi: ImageLocalApi
    ): ImageLocalDataSource
}
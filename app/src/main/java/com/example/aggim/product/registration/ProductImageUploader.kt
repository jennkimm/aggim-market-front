package com.example.aggim.product.registration
/*
    Created by Jin Lee at 2021/01/31
 */

import com.example.aggim.api.AggimApi
import com.example.aggim.api.response.ApiResponse
import com.example.aggim.api.response.ProductImageUploadResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import java.io.File

class ProductImageUploader : AnkoLogger {

    suspend fun upload(imageFile: File) = try {
        val part = makeImagePart(imageFile)
        withContext(Dispatchers.IO) {
            AggimApi.instance.uploadProductImage(part)
        }
    } catch (e: Exception) {
        error("error: product image upload", e)
        ApiResponse.error<ProductImageUploadResponse>(
            "An unknown error has occurred."
        )
    }

    private fun makeImagePart(imageFile: File): MultipartBody.Part {
        val mediaType = "multipart/form-data".toMediaType()
        val body = imageFile.asRequestBody(mediaType)

        return MultipartBody.Part
            .createFormData("image", imageFile.name, body)
    }

}
package com.example.aggim.api

import com.example.aggim.api.response.ApiResponse
import retrofit2.http.POST
import retrofit2.http.Query

interface AggimRefreshApi {
    @POST("/api/v1/refresh_token")
    suspend fun refreshToken(
            @Query("grant_type") grantType: String = "refresh_token"
    ): ApiResponse<String>

    companion object {
        val instance = ApiGenerator()
                .generateRefreshClient(AggimRefreshApi::class.java)
    }
}
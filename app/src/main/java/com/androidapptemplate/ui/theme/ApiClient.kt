package com.androidapptemplate.ui.theme

import com.androidapptemplate.PictureData
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject

class ApiClient @Inject constructor(
    val apiService: ApiService
) {
    suspend fun getPictures(limit: Int): List<PictureData>{
        return apiService.getPictures(limit)
    }

}

// can test this interface using a mock server
interface ApiService {
    @GET("list")
    suspend fun getPictures(
        @Query("limit") limit: Int
    ): List<PictureData>
}

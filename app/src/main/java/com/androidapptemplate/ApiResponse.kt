package com.androidapptemplate

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// You only need to provide a Serial name if you are trying to remove things like underscores
@Serializable
data class PictureData(
    val id: Int,
    val author: String,
    val width: Int,
    val height: Int,
    val url: String,
    @SerialName("download_url") val downloadUrl: String
)
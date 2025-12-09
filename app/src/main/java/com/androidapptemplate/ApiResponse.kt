package com.androidapptemplate

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// don't always need it
@Serializable
data class PictureData(
    @SerialName("id") val id: Int,
    @SerialName("author") val author: String,
    @SerialName("width") val width: Int,
    @SerialName("height") val height: Int,
    @SerialName("url") val url: String,
    @SerialName("download_url") val downloadUrl: String
)
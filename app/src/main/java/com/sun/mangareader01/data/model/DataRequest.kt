package com.sun.mangareader01.data.model

import android.net.Uri
import com.sun.mangareader01.utils.Constants.UTF_8
import java.net.URLDecoder

data class DataRequest(
    val scheme: String,
    val authority: String,
    val paths: List<String>? = null,
    val queryParams: Map<String, Any>? = null
) {
    fun toUrl(): String = Uri.Builder().apply {
        scheme(scheme)
        authority(authority)
        paths?.forEach { appendPath(it) }
        queryParams?.forEach {
            appendQueryParameter(it.key, it.value.toString())
        }
    }.toString()

    fun toRawUrl(): String = URLDecoder.decode(toUrl(), UTF_8)
}

package com.sun.mangareader01.utils

import android.content.Context
import android.graphics.Color
import android.widget.ImageView
import android.widget.Toast
import androidx.constraintlayout.widget.Placeholder
import com.bumptech.glide.Glide
import com.sun.mangareader01.R
import kotlinx.android.synthetic.main.item_manga.view.imageComicCover
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection

object Extensions {
    fun Context.showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    @Throws(IOException::class)
    fun HttpURLConnection.build(method: String) = apply {
        requestMethod = method
        connect()
    }

    @Throws(IOException::class)
    fun InputStreamReader.getJsonString(): String {
        val reader = BufferedReader(this)
        return StringBuilder().apply {
            do {
                val inputLine = reader.readLine()
                inputLine?.let { append(inputLine) }
            } while (inputLine != null)
            reader.close()
        }.toString()
    }

    fun ImageView.setImageUrl(
        url: String,
        default: Int = R.color.color_light_gray
    ) = Glide.with(context)
        .load(url)
        .placeholder(default)
        .into(imageComicCover)
}

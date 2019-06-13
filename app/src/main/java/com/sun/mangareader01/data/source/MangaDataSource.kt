package com.sun.mangareader01.data.source

import com.sun.mangareader01.data.model.MangasResponse
import com.sun.mangareader01.data.source.local.OnLoadedDataCallback

interface MangaDataSource {
    interface Remote {
        fun getMangas(query: String, callback: OnLoadedDataCallback<MangasResponse>)
    }
}
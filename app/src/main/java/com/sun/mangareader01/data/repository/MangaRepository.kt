package com.sun.mangareader01.data.repository

import com.sun.mangareader01.data.model.MangasResponse
import com.sun.mangareader01.data.source.MangaDataSource
import com.sun.mangareader01.data.source.local.OnLoadedDataCallback
import com.sun.mangareader01.data.source.remote.MangaRemoteDataSource

object MangaRepository : MangaDataSource.Remote {
    override fun getMangas(query: String, callback: OnLoadedDataCallback<MangasResponse>) =
        MangaRemoteDataSource.getMangas(query, callback)
}

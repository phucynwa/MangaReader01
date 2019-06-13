package com.sun.mangareader01.data.source.remote

import com.sun.mangareader01.data.model.DataRequest
import com.sun.mangareader01.data.model.MangasResponse
import com.sun.mangareader01.data.source.MangaDataSource
import com.sun.mangareader01.data.source.local.OnLoadedDataCallback
import com.sun.mangareader01.utils.Constants.AUTHORITY_READ_COMICS_ONLINE
import com.sun.mangareader01.utils.Constants.SCHEME_HTTPS
import com.sun.mangareader01.utils.PathConstants.PATH_SEARCH

const val KEY_QUERY = "query"

object MangaRemoteDataSource : MangaDataSource.Remote {
    override fun getMangas(
        query: String,
        callback: OnLoadedDataCallback<MangasResponse>
    ) {
        val requestUrl = DataRequest(
            scheme = SCHEME_HTTPS,
            authority = AUTHORITY_READ_COMICS_ONLINE,
            paths = listOf(PATH_SEARCH),
            queryParams = mapOf(
                KEY_QUERY to query.replace(' ', '+')
            )
        ).toRawUrl()
        GetResponseAsync(MangasResponseHandler(), callback).execute(requestUrl)
    }
}

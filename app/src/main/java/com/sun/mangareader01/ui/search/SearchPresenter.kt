package com.sun.mangareader01.ui.search

import com.sun.mangareader01.data.model.MangasResponse
import com.sun.mangareader01.data.repository.MangaRepository
import com.sun.mangareader01.data.source.local.OnLoadedDataCallback

class SearchPresenter(
    private val view: SearchContract.View,
    private val repository: MangaRepository
) : SearchContract.Presenter {

    init {
        view.setPresenter(this)
    }

    override fun getMangas(query: String) =
        repository.getMangas(query, object : OnLoadedDataCallback<MangasResponse> {
            override fun onSuccessful(data: MangasResponse) =
                view.showMangas(data.mangas)

            override fun onFailed(exception: Exception) = view.showError(exception)
        })
}

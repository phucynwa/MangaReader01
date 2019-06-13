package com.sun.mangareader01.ui.main

import com.sun.mangareader01.data.model.MangasResponse
import com.sun.mangareader01.data.repository.MangaRepository
import com.sun.mangareader01.data.source.local.OnLoadedDataCallback

const val SUGGESTIONS_LIMIT = 5

class MainPresenter(
    private val view: MainContract.View,
    private val repository: MangaRepository
) : MainContract.Presenter {

    init {
        view.setPresenter(this)
    }

    override fun getSuggestions(query: String) = repository.getMangas(
        query,
        object : OnLoadedDataCallback<MangasResponse> {
            override fun onSuccessful(data: MangasResponse) =
                view.showSuggestions(data.mangas.take(SUGGESTIONS_LIMIT))

            override fun onFailed(exception: Exception) =
                view.showError(exception)
        })
}

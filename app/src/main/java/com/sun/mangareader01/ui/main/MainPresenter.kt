package com.sun.mangareader01.ui.main

import com.sun.mangareader01.data.model.Manga
import com.sun.mangareader01.data.model.MangasResponse
import com.sun.mangareader01.data.source.local.OnLoadedDataCallback
import com.sun.mangareader01.data.source.repository.MangaRepository

class MainPresenter(
    private val view: MainContract.View,
    private val repository: MangaRepository
) : MainContract.Presenter {

    override fun getSuggestions(query: String) = repository.getMangas(
        query,
        object : OnLoadedDataCallback<MangasResponse> {
            override fun onSuccessful(data: MangasResponse) =
                view.showSuggestions(data.mangas.take(SUGGESTIONS_LIMIT))

            override fun onFailed(exception: Exception) =
                view.showError(exception)
        })

    override fun updateManga(manga: Manga) = repository.updateManga(
        manga,
        object : OnLoadedDataCallback<Boolean> {
            override fun onSuccessful(data: Boolean) {
            }

            override fun onFailed(exception: Exception) {
                view.showError(exception)
            }
        }
    )

    companion object {
        const val SUGGESTIONS_LIMIT = 5
    }
}

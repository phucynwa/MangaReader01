package com.sun.mangareader01.ui.search

import com.sun.mangareader01.data.model.Manga

interface SearchContract {
    interface View {
        fun setPresenter(presenter: Presenter)
        fun showMangas(mangas: List<Manga>)
        fun showError(exception: Exception)
        fun displaySearchingBar()
        fun hideSearchingBar()
    }

    interface Presenter {
        fun getMangas(query: String)
    }
}

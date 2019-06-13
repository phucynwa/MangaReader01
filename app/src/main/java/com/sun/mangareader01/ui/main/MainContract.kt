package com.sun.mangareader01.ui.main

import com.sun.mangareader01.data.model.Manga

interface MainContract {
    interface View {
        fun setPresenter(presenter: Presenter)
        fun showSuggestions(mangas: List<Manga>)
        fun showError(exception: Exception)
    }

    interface Presenter {
        fun getSuggestions(query: String)
    }
}

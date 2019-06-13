package com.sun.mangareader01.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sun.mangareader01.R
import com.sun.mangareader01.data.model.Manga
import com.sun.mangareader01.data.repository.MangaRepository
import com.sun.mangareader01.ui.adapter.MangaAdapter
import com.sun.mangareader01.utils.Extensions.showToast
import kotlinx.android.synthetic.main.fragment_search.barSearching
import kotlinx.android.synthetic.main.fragment_search.recyclerSearchResult

class SearchFragment : Fragment(), SearchContract.View {

    private lateinit var keyword: String
    private var presenter: SearchContract.Presenter? = null
    private val mangaAdapter: MangaAdapter by lazy { MangaAdapter(arrayListOf()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            keyword = it.getString(BUNDLE_SEARCH_KEY) ?: ""
            initPresenter()
            presenter?.getMangas(keyword)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_search, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpSearchResultView()
    }

    private fun initPresenter() {
        presenter = SearchPresenter(this, MangaRepository)
    }

    override fun setPresenter(presenter: SearchContract.Presenter) {
        this.presenter = presenter
    }

    private fun setUpSearchResultView() {
        recyclerSearchResult.layoutManager = LinearLayoutManager(context)
        recyclerSearchResult.adapter = mangaAdapter
        displaySearchingBar()
    }

    override fun showMangas(mangas: List<Manga>) {
        hideSearchingBar()
        mangaAdapter.updateData(mangas)
    }

    override fun showError(exception: Exception) {
        hideSearchingBar()
        context?.showToast(exception.toString())
    }

    override fun displaySearchingBar() {
        barSearching?.visibility = View.VISIBLE
    }

    override fun hideSearchingBar() {
        barSearching?.visibility = View.INVISIBLE
    }

    companion object {
        private const val BUNDLE_SEARCH_KEY = "keyword"

        @JvmStatic
        fun newInstance(keyword: String?) = SearchFragment().apply {
            arguments = Bundle().apply {
                putString(BUNDLE_SEARCH_KEY, keyword)
            }
        }
    }
}

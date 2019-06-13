package com.sun.mangareader01.ui.main

import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.widget.SearchView
import androidx.core.os.postDelayed
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sun.mangareader01.R
import com.sun.mangareader01.data.model.Manga
import com.sun.mangareader01.data.repository.MangaRepository
import com.sun.mangareader01.ui.adapter.SuggestionAdapter
import com.sun.mangareader01.ui.home.HomeFragment
import com.sun.mangareader01.ui.mycomics.MyComicsFragment
import com.sun.mangareader01.ui.search.SearchFragment
import com.sun.mangareader01.ui.trending.TrendingFragment
import com.sun.mangareader01.utils.Extensions.showToast
import kotlinx.android.synthetic.main.activity_main.listSuggestions
import kotlinx.android.synthetic.main.activity_main.viewNavigationBar
import kotlinx.android.synthetic.main.activity_main.viewSearch

const val DELAY_TYPING_CHANGE = 350L

class MainActivity : FragmentActivity(),
    MainContract.View,
    SearchView.OnQueryTextListener,
    AdapterView.OnItemClickListener,
    BottomNavigationView.OnNavigationItemSelectedListener {

    private var presenter: MainContract.Presenter? = null
    private val searchHandler: Handler by lazy { Handler() }
    private val suggestionAdapter: SuggestionAdapter by lazy {
        SuggestionAdapter(mutableListOf(), viewSearch.query.toString())
    }
    private var isTypingSearch = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initPresenter()
        initView()
        initListener()
    }

    private fun initView() {
        replaceFragment(HomeFragment())
        listSuggestions.adapter = suggestionAdapter
    }

    private fun initListener() {
        viewNavigationBar.setOnNavigationItemSelectedListener(this)
        viewSearch.apply {
            setOnClickListener {
                isIconified = false
                isTypingSearch = true
            }
            setOnQueryTextListener(this@MainActivity)
        }
        listSuggestions.onItemClickListener = this
    }

    private fun initPresenter() {
        presenter = MainPresenter(this, MangaRepository)
    }

    override fun setPresenter(presenter: MainContract.Presenter) {
        this.presenter = presenter
    }

    override fun showSuggestions(mangas: List<Manga>) {
        if (mangas.any() && isTypingSearch) {
            suggestionAdapter.apply {
                updateQuery(viewSearch.query.toString())
                updateData(mangas)
            }
            displaySuggestions()
        } else hideSuggestions()
    }

    override fun showError(exception: Exception) {
        showToast(exception.toString())
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .add(R.id.layoutContainerFragment, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.layoutContainerFragment, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun getCurrentFragment() =
        supportFragmentManager.findFragmentById(R.id.layoutContainerFragment)

    private fun isDisplayingSearchFragment() =
        getCurrentFragment() is SearchFragment

    override fun onQueryTextSubmit(query: String): Boolean {
        SearchFragment.newInstance(query).let {
            if (isDisplayingSearchFragment()) replaceFragment(it)
            else addFragment(it)
        }
        viewSearch.clearFocus()
        setIsTypingSearch(false)
        hideSuggestions()
        return true
    }

    override fun onQueryTextChange(newText: String): Boolean {
        getSuggestionsLater(newText)
        setIsTypingSearch(true)
        return true
    }

    private fun setIsTypingSearch(isTyping: Boolean) {
        isTypingSearch = isTyping
    }

    private fun getSuggestionsLater(keyword: String) {
        searchHandler.apply {
            removeCallbacksAndMessages(null)
            postDelayed(DELAY_TYPING_CHANGE) {
                if (keyword.isBlank()) hideSuggestions()
                else presenter?.getSuggestions(keyword)
            }
        }
    }

    private fun updateSearchAdapter(mangas: List<Manga>) {
        suggestionAdapter.apply {
            updateQuery(viewSearch.query.toString())
            updateData(mangas)
        }
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.itemHomeTab -> HomeFragment()
            R.id.itemTrendingTab -> TrendingFragment()
            R.id.itemMyComicsTab -> MyComicsFragment()
            else -> null
        }?.let { replaceFragment(it) }
        return true
    }

    private fun displaySuggestions() {
        listSuggestions.visibility = View.VISIBLE
    }

    private fun hideSuggestions() {
        listSuggestions.visibility = View.INVISIBLE
    }
}

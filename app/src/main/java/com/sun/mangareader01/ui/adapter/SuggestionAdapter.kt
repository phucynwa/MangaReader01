package com.sun.mangareader01.ui.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.sun.mangareader01.R
import com.sun.mangareader01.data.model.Manga
import com.sun.mangareader01.utils.Helpers.buildThumbUrl
import com.sun.mangareader01.utils.Helpers.highlightKeywordString
import kotlinx.android.synthetic.main.item_suggestion.view.imageMangaThumb
import kotlinx.android.synthetic.main.item_suggestion.view.textMangaTitle

class SuggestionAdapter(
    private var suggestions: MutableList<Manga>,
    private var keyword: String
) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: View.inflate(parent.context, R.layout.item_suggestion, null)
        val manga = getItem(position)
        view.tag = ViewHolder(view).apply {
            textMangaTitle.text = highlightKeywordString(manga.title, keyword)
            Glide.with(parent.context)
                .load(buildThumbUrl(manga.slug))
                .placeholder(R.color.color_light_gray)
                .circleCrop()
                .into(imageMangaThumb)
        }
        return view
    }

    override fun getItem(position: Int) = suggestions[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = suggestions.size

    fun updateData(mangas: List<Manga>) {
        suggestions = mangas as MutableList<Manga>
        notifyDataSetChanged()
    }

    fun updateQuery(query: String) {
        keyword = query
    }

    private class ViewHolder(view: View) {
        val textMangaTitle: TextView = view.textMangaTitle
        val imageMangaThumb: ImageView = view.imageMangaThumb
    }
}

package com.sun.mangareader01.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sun.mangareader01.R
import com.sun.mangareader01.data.model.Manga
import com.sun.mangareader01.ui.main.MainActivity
import com.sun.mangareader01.utils.Extensions.setImageUrl
import com.sun.mangareader01.utils.Helpers.buildCoverUrl
import kotlinx.android.synthetic.main.item_manga.view.buttonReadComic
import kotlinx.android.synthetic.main.item_manga.view.imageComicCover
import kotlinx.android.synthetic.main.item_manga.view.textMangaTitle

class MangaAdapter(
    private val mangas: MutableList<Manga>
) : RecyclerView.Adapter<MangaAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_manga,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bindData(mangas[position])

    override fun getItemCount() = mangas.size

    fun updateData(newMangas: List<Manga>) {
        val diffUtil =
            DiffUtil.calculateDiff(MangasUpdateCallback(mangas, newMangas))
        loadNewMangas(newMangas)
        diffUtil.dispatchUpdatesTo(this)
    }

    private fun loadNewMangas(newMangas: List<Manga>) {
        mangas.clear()
        mangas.addAll(newMangas)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val textComicTitle: TextView by lazy { view.textMangaTitle }
        private val imageComicCover: ImageView by lazy { view.imageComicCover }
        private val buttonReadComic: Button by lazy { view.buttonReadComic }

        // Bind data, imageComicCover set default image
        fun bindData(manga: Manga) {
            textComicTitle.text = manga.title
            imageComicCover.setImageUrl(buildCoverUrl(manga.slug))
            buttonReadComic.setOnClickListener(null)
        }
    }

    class MangasUpdateCallback(
        private val oldMangas: List<Manga>,
        private val newMangas: List<Manga>
    ) : DiffUtil.Callback() {

        override fun getOldListSize() = oldMangas.size

        override fun getNewListSize() = newMangas.size

        override fun areItemsTheSame(oldPosition: Int, newPosition: Int) =
            oldMangas[oldPosition].slug == newMangas[newPosition].slug

        override fun areContentsTheSame(oldPosition: Int, newPosition: Int) =
            areItemsTheSame(oldPosition, newPosition)
    }
}

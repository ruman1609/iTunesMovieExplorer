package com.rudyrachman16.itunesmovieexplorer.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rudyrachman16.itunesmovieexplorer.core.model.Movie
import com.rudyrachman16.itunesmovieexplorer.databinding.ItemMovieBinding
import com.rudyrachman16.itunesmovieexplorer.utils.ImageUtils.load

class MovieAdapter(
    private val clickCallback: (position: Int) -> Unit,
    private val favCallback: (movie: Movie) -> Unit
) : ListAdapter<Movie, MovieAdapter.ViewHolder>(object : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
        oldItem.trackId == newItem.trackId

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
        oldItem == newItem
}) {
    inner class ViewHolder(private val bind: ItemMovieBinding) :
        RecyclerView.ViewHolder(bind.root) {

        fun binding(movie: Movie) {
            val isLoading = movie.trackId == -1L && movie.trackName == "-"

            bind.layoutContentMovieItem.isVisible = !isLoading
            bind.layoutLoadingMovieItem.isVisible = isLoading

            if (isLoading) {
                bind.layoutLoadingMovieItem.startShimmer()
                return
            } else {
                bind.layoutLoadingMovieItem.stopShimmer()
            }

            bind.imgMovieCoverItem.load(movie.artworkUrl60)
            bind.txtMovieTitle.text = movie.trackName
            bind.txtMovieDescription.text = movie.shortDescription
            bind.txtMovieGenre.text = movie.primaryGenreName
            bind.txtMoviePrice.text = movie.trackPrice

            bind.lttLikeUnlikeItem.progress = if (movie.isFavorite) 1f else 0f
            bind.lttLikeUnlikeItem.setOnClickListener {
                movie.isFavorite = !movie.isFavorite
                bind.lttLikeUnlikeItem.speed =
                    if (bind.lttLikeUnlikeItem.progress == 1f) -1f else 1f
                bind.lttLikeUnlikeItem.playAnimation()
                favCallback(movie)
            }

            bind.root.setOnClickListener { clickCallback(adapterPosition) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding(getItem(position))
    }
}
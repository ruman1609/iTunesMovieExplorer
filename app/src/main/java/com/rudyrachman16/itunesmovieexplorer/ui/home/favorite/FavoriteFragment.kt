package com.rudyrachman16.itunesmovieexplorer.ui.home.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.rudyrachman16.itunesmovieexplorer.R
import com.rudyrachman16.itunesmovieexplorer.core.Status
import com.rudyrachman16.itunesmovieexplorer.core.model.Movie
import com.rudyrachman16.itunesmovieexplorer.databinding.FragmentFavoriteBinding
import com.rudyrachman16.itunesmovieexplorer.ui.home.HomeViewModel
import com.rudyrachman16.itunesmovieexplorer.ui.home.MovieAdapter
import com.rudyrachman16.itunesmovieexplorer.utils.UnitAndViewUtils.makeToast

class FavoriteFragment : Fragment() {
    private var _bind: FragmentFavoriteBinding? = null
    private val bind get() = _bind!!

    private val viewModel: HomeViewModel by activityViewModels()

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            findNavController().navigateUp()
        }
    }

    private val observerFavMovies = Observer<Status<List<Movie>>> {
        when (it) {
            Status.Loading -> loading(true)
            is Status.Error -> {
                loading(false)
                showRv(true)
                emptyTextPlaceholder(true, it.error)
            }

            is Status.Success -> {
                loading(false)
                viewModel.listFavoriteMovies.addAll(it.data)
                showRv(viewModel.listFavoriteMovies.isEmpty())
                emptyTextPlaceholder(false)

                if (viewModel.listFavoriteMovies.isEmpty()) {
                    return@Observer
                }
                adapter.submitList(viewModel.listFavoriteMovies.toList())
            }
        }
    }

    private val observerCUFavMovie = Observer<Status<String>> {
        when (it) {
            is Status.Error -> requireContext().makeToast(it.error.message, Toast.LENGTH_SHORT)
            is Status.Success -> {
                adapter.submitList(viewModel.listFavoriteMovies.toList())
                showRv(viewModel.listFavoriteMovies.isEmpty())
            }
            Status.Loading -> {}
        }
    }

    private val adapter = MovieAdapter(
        isForFavorite = true,
        clickCallback = {
            findNavController().navigate(
                FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment(it, true)
            )
        },
        favCallback = {
            viewModel.listFavoriteMovies.remove(it)
            viewModel.deleteFromFavorite(it)

            it.isFavorite = true

            val index = viewModel.listSearchResult.indexOf(it)
            if (index >= 0) {
                viewModel.listSearchResult[index].isFavorite = false
            }
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bind = FragmentFavoriteBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.listFavoriteMovies.removeAll { !it.isFavorite }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)
        bind.imgDetailMovieBackToolbar.setOnClickListener { findNavController().navigateUp() }
        bind.layoutEmptyFavorite.setOnClickListener { findNavController().navigateUp() }

        viewModel.getFavoriteMovies().observe(viewLifecycleOwner, observerFavMovies)

        bind.rvFavorite.adapter = adapter

        viewModel.cUResult.observe(viewLifecycleOwner, observerCUFavMovie)
    }

    private fun loading(isLoading: Boolean) {
        bind.rvFavorite.isVisible = true
        bind.layoutEmptyFavorite.isVisible = false

        if (isLoading) {
            adapter.submitList(List(5) { Movie() })
        } else {
            viewModel.listFavoriteMovies.clear()
            adapter.submitList(viewModel.listFavoriteMovies.toList())
        }
    }

    private fun showRv(isErrorOrEmpty: Boolean) {
        bind.rvFavorite.isVisible = !isErrorOrEmpty
        bind.layoutEmptyFavorite.isVisible = isErrorOrEmpty
    }

    private fun emptyTextPlaceholder(isError: Boolean, e: Exception = Exception()) {
        bind.txtEmptyFavorite.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                if (isError) R.color.md_theme_error else R.color.md_theme_onSurface
            )
        )
        bind.txtEmptyFavorite.text =
            if (isError) getString(R.string.error, e.message)
            else getString(R.string.try_like_a_movie_in_search_menu)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bind = null
        viewModel.getFavoriteMovies().removeObserver(observerFavMovies)
        viewModel.cUResult.removeObserver(observerCUFavMovie)
        onBackPressedCallback.isEnabled = false
        onBackPressedCallback.remove()
    }
}
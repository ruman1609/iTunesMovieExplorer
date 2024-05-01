package com.rudyrachman16.itunesmovieexplorer.ui.home.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.core.view.isInvisible
import androidx.core.view.marginBottom
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.appbar.AppBarLayout
import com.rudyrachman16.itunesmovieexplorer.R
import com.rudyrachman16.itunesmovieexplorer.core.Status
import com.rudyrachman16.itunesmovieexplorer.core.model.Movie
import com.rudyrachman16.itunesmovieexplorer.databinding.FragmentDetailBinding
import com.rudyrachman16.itunesmovieexplorer.ui.home.HomeViewModel
import com.rudyrachman16.itunesmovieexplorer.utils.ImageUtils.load
import com.rudyrachman16.itunesmovieexplorer.utils.ImageUtils.loadSVG
import com.rudyrachman16.itunesmovieexplorer.utils.UnitAndViewUtils.makeToast

class DetailFragment : Fragment() {
    private var _bind: FragmentDetailBinding? = null
    private val bind get() = _bind!!
    private var oldValue = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bind = FragmentDetailBinding.inflate(inflater, container, false)
        return bind.root
    }

    private var movie: Movie? = null
    private val viewModel: HomeViewModel by activityViewModels()
    private val args: DetailFragmentArgs by navArgs()

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            findNavController().navigateUp()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbarSetup()

        try {
            movie =
                if (!args.fromFavorite) viewModel.listSearchResult[args.itemPosition] else viewModel.listFavoriteMovies[args.itemPosition]
        } catch (e: Exception) {
            findNavController().navigateUp()
        }

        movie?.also {
            bind.imgDetailCover.load(it.artworkUrl100)
            bind.txtDetailMovieTitle.text = it.trackName
            bind.txtDetailMovieReleaseDate.text = getString(R.string.release_date, it.releaseDate)
            bind.txtDetailMovieGenre.text = it.primaryGenreName
            bind.txtDetailMoviePrice.text = it.trackPrice
            bind.imgDetailMovieCountry.loadSVG("https://flagicons.lipis.dev/flags/4x3/${it.country}.svg")
            bind.txtDetailMovieCountry.text =
                getString(R.string.iso_alpha_2, it.country.uppercase())
            bind.txtDetailMovieDescription.text = it.longDescription

            bind.lttMovieDetailLikeUnlike.progress = if (it.isFavorite) 1f else 0f
            bind.lttMovieDetailLikeUnlike.setOnClickListener { _ ->
                it.isFavorite = !it.isFavorite
                bind.lttMovieDetailLikeUnlike.speed =
                    if (bind.lttMovieDetailLikeUnlike.progress == 1f) -1f else 1f
                bind.lttMovieDetailLikeUnlike.playAnimation()

                if (it.isFavorite) {
                    viewModel.insertToFavorite(it)
                } else {
                    viewModel.deleteFromFavorite(it)
                }
            }
        }

        viewModel.cUResult.observe(viewLifecycleOwner) {
            when (it) {
                is Status.Error -> requireContext().makeToast(it.error.message, Toast.LENGTH_SHORT)
                else -> {}
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)
        bind.imgDetailMovieBackToolbar.setOnClickListener { findNavController().navigateUp() }
    }

    private fun toolbarSetup() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(bind.toolbarDetail)
        val supportActionBar = (requireActivity() as AppCompatActivity).supportActionBar
        supportActionBar?.title = ""

        ContextCompat.getDrawable(requireContext(), R.drawable.ic_back)?.apply {
            colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                ContextCompat.getColor(requireContext(), R.color.md_theme_onPrimary),
                BlendModeCompat.SRC_IN
            )
            supportActionBar?.setHomeAsUpIndicator(this)
        }

        val appBarListener = AppBarLayout.OnOffsetChangedListener { appBarLayout, i ->
            if (appBarLayout == null) {
                return@OnOffsetChangedListener
            }

            val condition =
                bind.collapsingToolbarDetail.height + i + bind.layoutInsideCollapsingToolbarDetail.marginBottom < 2 * bind.collapsingToolbarDetail.minimumHeight  // 48 is padding bottom of collapsing toolbar
            if (condition != oldValue) {
                oldValue = condition
                bind.txtDetailMovieTitleToolbar.text =
                    if (condition) bind.txtDetailMovieTitle.text else ""
                bind.toolbarDetail.setBackgroundColor(
                    if (condition) ContextCompat.getColor(
                        requireContext(),
                        R.color.md_theme_primary
                    )
                    else ContextCompat.getColor(requireContext(), android.R.color.transparent)
                )
                bind.layoutInsideCollapsingToolbarDetail.isInvisible = condition
            }
        }

        bind.appBarDetail.addOnOffsetChangedListener(appBarListener)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bind = null
        movie = null
        onBackPressedCallback.isEnabled = false
        onBackPressedCallback.remove()
        viewModel.cUResult.removeObservers(viewLifecycleOwner)
    }
}
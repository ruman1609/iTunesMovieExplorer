package com.rudyrachman16.itunesmovieexplorer.ui.home.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.rudyrachman16.itunesmovieexplorer.R
import com.rudyrachman16.itunesmovieexplorer.core.Status
import com.rudyrachman16.itunesmovieexplorer.core.model.Movie
import com.rudyrachman16.itunesmovieexplorer.databinding.FragmentSearchBinding
import com.rudyrachman16.itunesmovieexplorer.ui.home.HomeViewModel
import com.rudyrachman16.itunesmovieexplorer.ui.home.MovieAdapter
import com.rudyrachman16.itunesmovieexplorer.utils.UnitAndViewUtils.makeToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private var _bind: FragmentSearchBinding? = null
    private val bind get() = _bind!!

    private val adapter = MovieAdapter(
        clickCallback = {
            findNavController().navigate(
                SearchFragmentDirections.actionSearchFragmentToDetailFragment(it)
            )
        },
        favCallback = {
            if (it.isFavorite) {
                viewModel.insertToFavorite(it)
            } else {
                viewModel.deleteFromFavorite(it)
            }
        }
    )

    private var isFirst = true

    private val viewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _bind = FragmentSearchBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).apply {
            setSupportActionBar(bind.toolbarSearch)
            setTitle("")
        }
        bind.txtSearchToolbarTitle.text = getString(R.string.app_name)

        bind.layoutEmptySearch.setOnClickListener {
            bind.edtSearch.setText("")
            bind.edtSearch.requestFocus()
        }

        bind.imgGotoFavSearch.setOnClickListener {
            findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToFavoriteFragment())
        }

        bind.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                isFirst = false
                lifecycleScope.launch {
                    viewModel.keywordQuery.value = s.toString()
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        viewModel.searchResult.observe(viewLifecycleOwner) {
            when (it) {
                Status.Loading -> loading(true)
                is Status.Error -> {
                    loading(false)
                    showRv(true)
                    emptyTextPlaceholder(true, it.error)
                }

                is Status.Success -> {
                    loading(false)
                    viewModel.listSearchResult.addAll(it.data)
                    showRv(viewModel.listSearchResult.isEmpty())
                    emptyTextPlaceholder(false)

                    if (viewModel.listSearchResult.isEmpty()) {
                        return@observe
                    }
                    adapter.submitList(viewModel.listSearchResult.toList())
                }
            }
        }

        viewModel.cUResult.observe(viewLifecycleOwner) {
            when (it) {
                is Status.Error -> requireContext().makeToast(it.error.message, Toast.LENGTH_SHORT)
                else -> {}
            }
        }

        bind.rvSearchResult.adapter = adapter
    }

    private fun loading(isLoading: Boolean) {
        bind.rvSearchResult.isVisible = true
        bind.layoutEmptySearch.isVisible = false

        if (isLoading) {
            adapter.submitList(List(5) { Movie() })
        } else {
            viewModel.listSearchResult.clear()
            adapter.submitList(viewModel.listSearchResult.toList())
        }
    }

    private fun showRv(isErrorOrEmpty: Boolean) {
        bind.rvSearchResult.isVisible = !isErrorOrEmpty
        bind.layoutEmptySearch.isVisible = isErrorOrEmpty
    }

    private fun emptyTextPlaceholder(isError: Boolean, e: Exception = Exception()) {
        bind.txtSearchEmpty.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                if (isError) R.color.md_theme_error else R.color.md_theme_onSurface
            )
        )
        if (isError) {
            bind.txtSearchEmpty.text = getString(R.string.error, e.message)
        } else {
            bind.txtSearchEmpty.text =
                getString(if (isFirst) R.string.first_search_placeholder else R.string.empty_search_result)
        }
    }

    override fun onResume() {
        super.onResume()

        emptyTextPlaceholder(false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bind = null
        viewModel.cUResult.removeObservers(viewLifecycleOwner)
    }
}
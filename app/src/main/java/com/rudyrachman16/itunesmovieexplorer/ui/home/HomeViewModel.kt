package com.rudyrachman16.itunesmovieexplorer.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.rudyrachman16.itunesmovieexplorer.core.Repository
import com.rudyrachman16.itunesmovieexplorer.core.Status
import com.rudyrachman16.itunesmovieexplorer.core.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val listSearchResult: MutableList<Movie> = mutableListOf()
    val keywordQuery = MutableStateFlow("")

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val searchResult =
        keywordQuery.debounce(600).distinctUntilChanged().filter { it.trim().isNotEmpty() }
            .flatMapLatest { repository.getMovies(it) }.asLiveData()

    private val _cUResult: MutableLiveData<Status<String>> = MutableLiveData()
    val cUResult: LiveData<Status<String>> = _cUResult

    fun insertToFavorite(movie: Movie) {
        viewModelScope.launch {
            repository.insertToFavorite(movie).collectLatest { _cUResult.postValue(it) }
        }
    }

    fun deleteFromFavorite(movie: Movie) {
        viewModelScope.launch {
            repository.deleteFromFavorite(movie).collectLatest { _cUResult.postValue(it) }
        }
    }

    val listFavoriteMovies = mutableListOf<Movie>()
    fun getFavoriteMovies() = repository.getFavoriteMovies().asLiveData()
}
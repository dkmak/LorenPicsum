package com.androidapptemplate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    homeRepository: HomeRepository
) : ViewModel() {

    val pictureList: StateFlow<List<PictureData>> = homeRepository
        .getPictures(limit = INITIAL_LIMIT).map { result ->
            // Log.d("HomeViewModel", "Success: $result")
            result.getOrThrow().sortedBy { data -> data.author }
        }.catch { throwable ->
            // Log.d("HomeViewModel", "Failure: ${throwable.message}")
            emit(emptyList())
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    companion object {
        private const val INITIAL_LIMIT = 20
    }
}
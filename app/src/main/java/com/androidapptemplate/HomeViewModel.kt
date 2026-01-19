package com.androidapptemplate

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

sealed interface PicturesUiState {
    data class Success(val pictures: List<PictureData>) : PicturesUiState
    data class Error(val message: String): PicturesUiState
    data object Loading: PicturesUiState
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    homeRepository: HomeRepository
) : ViewModel() {
    val pictureUiState: StateFlow<PicturesUiState> = homeRepository
        .getPictures(limit = INITIAL_LIMIT).map { result ->
            // take the Result object and transform the data to a UI State object
            result.fold(
                // fold makes it easy to process successes and failures
                onSuccess = { pictures ->
                    Log.d("HomeViewModel", "Success: $result")
                    PicturesUiState.Success(pictures.sortedBy { it.author })
                },
                onFailure = { throwable ->
                    Log.d("HomeViewModel", "Failure: $result")
                    PicturesUiState.Error(throwable.message ?: "An unknown error occurred")
                }
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = PicturesUiState.Loading
        )

    companion object {
        private const val INITIAL_LIMIT = 20
    }
}
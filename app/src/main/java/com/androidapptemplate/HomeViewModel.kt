package com.androidapptemplate

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
            // Log.d("HomeViewModel", "Success: $result")
            result.fold(
                onSuccess = { pictures ->
                    PicturesUiState.Success(pictures.sortedBy { it.author })
                },
                onFailure = { throwable ->
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
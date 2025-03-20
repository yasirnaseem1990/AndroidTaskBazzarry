package com.yasirnaseem.androidtask.bazzarry.ui.presenation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yasirnaseem.androidtask.bazzarry.domain.GetWordsUseCase
import com.yasirnaseem.androidtask.network.data.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WordsViewModel @Inject constructor(
    private val getWordsUseCase: GetWordsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(WordsUiState())

    /*
    I've use statIn here because we need to save the resource in the viewModel through when to
    stop the values emitting the values, so this is lifecycle aware
     */
    val uiState: StateFlow<WordsUiState> = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = WordsUiState()
    )

    init {
        loadWords()
    }

    fun loadWords() {
        _uiState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {

            when (val data = getWordsUseCase.invoke()) {
                is Result.Success -> {

                    _uiState.update { uiState ->
                        uiState.copy(
                            isLoading = false,
                            words = data.data
                        )
                    }
                }

                is Result.Error -> {
                    _uiState.update { uiState ->
                        uiState.copy(
                            isLoading = false,
                            error = data.exception.toString()
                        )
                    }
                }
            }
        }
    }

    fun onSearchTextChange(text: String) {
        _uiState.value = _uiState.value.copy(searchText = text)
    }

    fun onSortClicked() {
        _uiState.update { uiState ->
            uiState.copy(isAscending = !_uiState.value.isAscending)
        }
    }

    // Function to handle device rotation
    fun saveWordsState(words: Map<String, Int>) {
        _uiState.value = _uiState.value.copy(words = words)
    }
}

data class WordsUiState(
    val words: Map<String, Int> = emptyMap(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isAscending: Boolean = true,
    val searchText: String = "",
    val sortOrder: SortOrder = SortOrder.ASCENDING
)

enum class SortOrder {
    ASCENDING, DESCENDING
}
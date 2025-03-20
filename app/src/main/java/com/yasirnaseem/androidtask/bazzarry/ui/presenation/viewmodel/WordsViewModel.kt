package com.yasirnaseem.androidtask.bazzarry.ui.presenation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yasirnaseem.androidtask.bazzarry.domain.GetWordsUseCase
import com.yasirnaseem.androidtask.network.data.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.Serializable
import javax.inject.Inject


@HiltViewModel
class WordsViewModel @Inject constructor(
    private val getWordsUseCase: GetWordsUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        savedStateHandle.get<WordsUiState>("ui_state") ?: WordsUiState()
    )

    /*
    I've use statIn here because we need to save the resource in the viewModel through when to
    stop the values emitting the values, so this is lifecycle aware
     */
    val uiState: StateFlow<WordsUiState> = _uiState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = WordsUiState()
        )

    init {
        val uiStateWords = _uiState.value.words
        if (uiStateWords.isEmpty()) loadWords()
    }

    fun loadWords() {
        _uiState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {

            getWordsUseCase().collectLatest { result ->
                when (result) {
                    is Result.Success -> {

                        _uiState.update { uiState ->
                            uiState.copy(
                                isLoading = false,
                                words = result.data
                            )
                        }
                    }

                    is Result.Error -> {
                        _uiState.update { uiState ->
                            uiState.copy(
                                isLoading = false,
                                error = result.exception.toString()
                            )
                        }
                    }
                }
                savedStateHandle["ui_state"] = _uiState.value
            }
        }
    }

    fun onSearchTextChange(text: String) {
        _uiState.update { uiState ->
            uiState.copy(searchText = text)
        }
        savedStateHandle["ui_state"] = _uiState.value
    }

    fun onSortClicked() {
        _uiState.update { uiState ->
            val newSortOrder = !uiState.isAscending
            val sortedWords = sortWords(uiState.words, newSortOrder)

            uiState.copy(
                isAscending = newSortOrder,
                words = sortedWords
            )
        }
        savedStateHandle["ui_state"] = _uiState.value //
    }

    private fun sortWords(words: Map<String, Int>, ascending: Boolean): Map<String, Int> {
        return if (ascending) words.toList().sortedBy { (_, value) -> value }.toMap()
        else words.toList().sortedByDescending { (_, value) -> value }.toMap()
    }
}

data class WordsUiState(
    val words: Map<String, Int> = emptyMap(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isAscending: Boolean = true,
    val searchText: String = "",
) : Serializable
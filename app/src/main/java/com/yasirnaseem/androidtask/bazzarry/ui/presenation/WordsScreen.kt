package com.yasirnaseem.androidtask.bazzarry.ui.presenation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yasirnaseem.androidtask.bazzarry.R
import com.yasirnaseem.androidtask.bazzarry.ui.presenation.viewmodel.WordsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordsScreen(wordsViewModel: WordsViewModel = hiltViewModel()) {
    val uiState by wordsViewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Word Count") },
                actions = {
                    IconButton(onClick = { wordsViewModel.loadWords() }) {
                        Icon(Icons.Filled.Refresh, "Refresh")
                    }
                    IconButton(onClick = { wordsViewModel.onSortClicked() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_sort),
                            contentDescription = "Sort",
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = uiState.searchText,
                onValueChange = { wordsViewModel.onSearchTextChange(it) },
                label = { Text(text = "Search") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(Icons.Filled.Search, contentDescription = "Search")
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else if (uiState.error != null) {
                Text(text = "Error: ${uiState.error}", color = MaterialTheme.colorScheme.error)
            } else {
                val filteredWords = uiState.words.filterKeys {
                    it.contains(uiState.searchText, ignoreCase = true)
                }
                val sortedWords = if (uiState.isAscending) {
                    filteredWords.toList().sortedBy { (_, value) -> value }.toMap()
                } else {
                    filteredWords.toList().sortedByDescending { (_, value) -> value }.toMap()
                }

                if (sortedWords.isEmpty()) {
                    Text(text = "No matching words found.")
                } else {
                    LazyColumn {
                        items(sortedWords.toList()) { (word, count) ->
                            WordItem(word = word, count = count)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WordItem(word: String, count: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = word)
        Text(text = "Count: $count")
    }
}
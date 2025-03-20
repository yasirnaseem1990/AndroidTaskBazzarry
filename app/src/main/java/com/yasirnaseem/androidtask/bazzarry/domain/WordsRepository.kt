@file:JvmName("WordsRepositoryKt")

package com.yasirnaseem.androidtask.bazzarry.domain

import com.yasirnaseem.androidtask.bazzarry.data.WordsDataSource
import com.yasirnaseem.androidtask.database.data.LocalWordsDataSource
import com.yasirnaseem.androidtask.database.model.WordEntity
import com.yasirnaseem.androidtask.network.data.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WordsRepository @Inject constructor(
    private val remoteWordsDataSource: WordsDataSource,
    private val localWordsDataSource: LocalWordsDataSource
) {

    suspend fun getWords(): Result<Map<String, Int>> {
        return withContext(Dispatchers.IO) {

            val localWords = localWordsDataSource.getWords()
            if (localWords.isNotEmpty()) {
                val wordMap = localWords.associate { it.word to it.count }
                Result.Success(wordMap)
            } else {
                val remoteResult = remoteWordsDataSource.getWebsiteContent()
                if (remoteResult is Result.Success) {
                    val wordEntities = remoteResult.data.map { WordEntity(it.key, it.value) }
                    localWordsDataSource.insertWords(wordEntities)
                }
                remoteResult
            }
        }
    }
}


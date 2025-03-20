@file:JvmName("WordsRepositoryKt")

package com.yasirnaseem.androidtask.bazzarry.domain

import com.yasirnaseem.androidtask.bazzarry.data.WordsDataSource
import com.yasirnaseem.androidtask.database.data.LocalWordsDataSource
import com.yasirnaseem.androidtask.database.model.WordEntity
import com.yasirnaseem.androidtask.network.data.ErrorType
import com.yasirnaseem.androidtask.network.data.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WordsRepository @Inject constructor(
    private val remoteWordsDataSource: WordsDataSource,
    private val localWordsDataSource: LocalWordsDataSource
) {

    fun getWords(): Flow<Result<Map<String, Int>>> = flow {
        try {
            val localWords = localWordsDataSource.getWordsFlow().first()

            if (localWords.isNotEmpty()) {
                emit(Result.Success(localWords))
            } else {
                val remoteResult = withContext(Dispatchers.IO) {
                    remoteWordsDataSource.getWebsiteContent()
                }

                if (remoteResult is Result.Success) {
                    val wordEntities = remoteResult.data.map { WordEntity(it.key, it.value) }
                    localWordsDataSource.insertWords(wordEntities)
                }
                emit(remoteResult)
            }
        } catch (e: Exception) {
            emit(Result.Error(ErrorType.Generic(e.message ?: "Unknown error")))
        }
    }.catch { e ->
        emit(Result.Error(ErrorType.Generic(e.message ?: "Unknown error")))
    }
}


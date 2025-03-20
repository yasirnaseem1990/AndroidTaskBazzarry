package com.yasirnaseem.androidtask.database.data

import com.yasirnaseem.androidtask.database.model.WordEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalWordsDataSource @Inject constructor(private val wordsDao: WordsDao) {

    fun getWordsFlow(): Flow<Map<String, Int>> {
        return wordsDao.getAllWords()
            .map { words -> words.associate { it.word to it.count } }
    }

    suspend fun insertWords(words: List<WordEntity>) {
        wordsDao.insertWords(words)
    }
}
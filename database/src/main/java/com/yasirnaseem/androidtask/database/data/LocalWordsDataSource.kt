package com.yasirnaseem.androidtask.database.data

import com.yasirnaseem.androidtask.database.model.WordEntity
import javax.inject.Inject

class LocalWordsDataSource @Inject constructor(private val wordsDao: WordsDao) {

    suspend fun getWords(): List<WordEntity> {
        return wordsDao.getAllWords()
    }

    suspend fun insertWords(words: List<WordEntity>) {
        wordsDao.insertWords(words)
    }
}
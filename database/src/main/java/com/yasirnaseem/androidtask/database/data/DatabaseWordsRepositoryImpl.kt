package com.yasirnaseem.androidtask.database.data

/*class DatabaseWordsRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource
) : com.yasirnaseem.androidtask.bazzarry.domain.WordsRepository {

    override suspend fun getWords(): Result<Map<String, Int>> {
        return try {
            val words = localDataSource.getWords().associate { it.word to it.count }
            Result.success(words)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun saveWords(words: Map<String, Int>) {
        val entities = words.map { WordEntity(it.key, it.value) }
        localDataSource.insertWords(entities)
    }

    override suspend fun getSavedWords(): Result<Map<String, Int>> = withContext(Dispatchers.IO) {
        val localWords = localDataSource.getWords()
        if (localWords.isNotEmpty()) {
            Result.success(localWords.associate { it.word to it.count })
        } else {
            Result.failure(Exception("No words saved locally"))
        }
    }
}*/

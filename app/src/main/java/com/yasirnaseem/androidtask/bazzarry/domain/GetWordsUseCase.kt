package com.yasirnaseem.androidtask.bazzarry.domain

import com.yasirnaseem.androidtask.network.data.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWordsUseCase @Inject constructor(private val wordsRepository: WordsRepository) {

    operator fun invoke(): Flow<Result<Map<String, Int>>> =
        wordsRepository.getWords()
}
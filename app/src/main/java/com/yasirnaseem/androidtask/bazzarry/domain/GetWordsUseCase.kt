package com.yasirnaseem.androidtask.bazzarry.domain

import com.yasirnaseem.androidtask.network.data.Result
import javax.inject.Inject

class GetWordsUseCase @Inject constructor(private val wordsRepository: WordsRepository) {

    suspend operator fun invoke(): Result<Map<String, Int>> =
        wordsRepository.getWords()
}
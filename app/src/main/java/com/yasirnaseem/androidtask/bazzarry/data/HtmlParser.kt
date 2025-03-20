package com.yasirnaseem.androidtask.bazzarry.data

import org.jsoup.Jsoup
import javax.inject.Inject

class HtmlParser @Inject constructor() {
    fun parseWords(html: String): Map<String, Int> {
        val document = Jsoup.parse(html)
        val text = document.body().text()
        val words = text.split("\\s+".toRegex())

        return words.filter { it.isNotBlank() }
            .groupBy { it.replace("[^a-zA-Z0-9]".toRegex(), "") }
            .mapValues { it.value.size }
            .filterKeys { it.isNotBlank() }
    }
}
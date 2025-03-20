package com.yasirnaseem.androidtask.bazzarry.di

import com.yasirnaseem.androidtask.bazzarry.data.HtmlParser
import com.yasirnaseem.androidtask.bazzarry.data.RemoteWordsDataSource
import com.yasirnaseem.androidtask.bazzarry.data.WordsDataSource
import com.yasirnaseem.androidtask.bazzarry.domain.WordsRepository
import com.yasirnaseem.androidtask.database.data.LocalWordsDataSource
import com.yasirnaseem.androidtask.network.data.WordApiService
import com.yasirnaseem.androidtask.network.data.di.NetworkHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWordsRepository(
        remoteWordsDataSource: WordsDataSource,
        localWordsDataSource: LocalWordsDataSource
    ) =
        WordsRepository(
            remoteWordsDataSource = remoteWordsDataSource,
            localWordsDataSource = localWordsDataSource
        )

    @Provides
    @Singleton
    fun provideRemoteDataSource(
        apiService: WordApiService,
        networkHandler: NetworkHandler,
        htmlParser: HtmlParser
    ): WordsDataSource {
        return RemoteWordsDataSource(
            apiService = apiService,
            networkHandler = networkHandler,
            htmlParser = htmlParser
        )
    }

    @Provides
    @Singleton
    fun provideHtmlParser(): HtmlParser =
        HtmlParser()
}
package com.yasirnaseem.androidtask.database.di

import android.content.Context
import androidx.room.Room
import com.yasirnaseem.androidtask.database.data.WordsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "words_database"
        ).build()
    }

    @Provides
    fun provideWordsDao(appDatabase: AppDatabase): WordsDao {
        return appDatabase.wordsDao()
    }
}
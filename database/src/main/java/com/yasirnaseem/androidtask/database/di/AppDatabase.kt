package com.yasirnaseem.androidtask.database.di

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yasirnaseem.androidtask.database.data.WordsDao
import com.yasirnaseem.androidtask.database.model.WordEntity

@Database(entities = [WordEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun wordsDao(): WordsDao
}
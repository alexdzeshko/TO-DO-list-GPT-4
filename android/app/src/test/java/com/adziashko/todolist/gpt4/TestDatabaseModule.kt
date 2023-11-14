package com.adziashko.todolist.gpt4

import android.content.Context
import androidx.room.Room
import com.adziashko.todolist.gpt4.data.DatabaseModule
import com.adziashko.todolist.gpt4.data.TaskDao
import com.adziashko.todolist.gpt4.data.TaskDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn( components = [SingletonComponent::class], replaces = [DatabaseModule::class])
object TestDatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): TaskDatabase {
        return Room.inMemoryDatabaseBuilder(
            appContext.applicationContext,
            TaskDatabase::class.java
        ).allowMainThreadQueries()
            .fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideTaskDao(db: TaskDatabase): TaskDao {
        return db.taskDao()
    }
}
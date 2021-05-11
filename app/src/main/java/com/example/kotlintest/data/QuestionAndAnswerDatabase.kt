package com.example.kotlintest.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Database(entities = [QuestionModel::class, AnswersModel::class], version = 1, exportSchema = false )
abstract class QuestionAndAnswerDatabase: RoomDatabase() {
    abstract fun dao(): QuestionAndAnswerDao

    companion object {
        @Volatile
        private var INSTANCE: QuestionAndAnswerDatabase? = null

        fun getDatabase(context: Context): QuestionAndAnswerDatabase{
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    QuestionAndAnswerDatabase::class.java,
                    "test_database")
                    .build()
                GlobalScope.launch {
                    val question = QuestionModel(0, "Вопрос")
                    instance.dao().addQuestion(question)
                }
                INSTANCE = instance
                return instance
            }

        }
    }


}
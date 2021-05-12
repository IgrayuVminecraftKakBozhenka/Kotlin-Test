package com.example.kotlintest.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [QuestionModel::class, AnswersModel::class], version = 1, exportSchema = false)
abstract class QuestionAndAnswerDatabase : RoomDatabase() {
    abstract fun dao(): QuestionAndAnswerDao

    init {

    }

    companion object {
        @Volatile
        private var INSTANCE: QuestionAndAnswerDatabase? = null

        fun getDatabase(context: Context): QuestionAndAnswerDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    QuestionAndAnswerDatabase::class.java,
                    "test_database"
                )
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            Log.d("Created db", "created")
                            db.execSQL("INSERT INTO questions (_id, question) VALUES(1, 'Что Котлин использует под капотом?')")
                            db.execSQL("INSERT INTO questions (_id, question) VALUES(2, 'Год выхода языка:')")
                            db.execSQL("INSERT INTO questions (_id, question) VALUES(3, 'Существуют ли примитивы в Котлине?')")
                            db.execSQL("INSERT INTO questions (_id, question) VALUES(4, 'Можно ли писать Java код в Котлин файле?')")
                            db.execSQL("INSERT INTO questions (_id, question) VALUES(5, 'Котлин это язык:')")
                        }
                    })
                    .build()
                INSTANCE = instance
                return instance
            }

        }
    }


}
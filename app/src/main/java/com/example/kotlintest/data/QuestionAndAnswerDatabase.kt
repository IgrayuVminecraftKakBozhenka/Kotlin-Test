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
                            db.execSQL("INSERT INTO questions (_id, question) VALUES(1, 'Что Котлин использует под капотом?')")
                            db.execSQL("INSERT INTO questions (_id, question) VALUES(2, 'Год выхода языка:')")
                            db.execSQL("INSERT INTO questions (_id, question) VALUES(3, 'Существуют ли примитивы в Котлине?')")
                            db.execSQL("INSERT INTO questions (_id, question) VALUES(4, 'Можно ли писать Java код в Котлин файле?')")
                            db.execSQL("INSERT INTO questions (_id, question) VALUES(5, 'Котлин это язык:')")

                            db.execSQL("INSERT INTO answers (_id, question_id, answer, correct) VALUES(1, 1, 'JVM', 1)")
                            db.execSQL("INSERT INTO answers (_id, question_id, answer, correct) VALUES(2, 1, '.NET', 0)")
                            db.execSQL("INSERT INTO answers (_id, question_id, answer, correct) VALUES(3, 1, '.DA', 0)")
                            db.execSQL("INSERT INTO answers (_id, question_id, answer, correct) VALUES(4, 1, 'C++', 0)")

                            db.execSQL("INSERT INTO answers (_id, question_id, answer, correct) VALUES(5, 2, '2006', 0)")
                            db.execSQL("INSERT INTO answers (_id, question_id, answer, correct) VALUES(6, 2, '1999', 0)")
                            db.execSQL("INSERT INTO answers (_id, question_id, answer, correct) VALUES(7, 2, '2011', 1)")
                            db.execSQL("INSERT INTO answers (_id, question_id, answer, correct) VALUES(8, 2, '456 До н. э.', 0)")

                            db.execSQL("INSERT INTO answers (_id, question_id, answer, correct) VALUES(9, 3, 'Да', 0)")
                            db.execSQL("INSERT INTO answers (_id, question_id, answer, correct) VALUES(10, 3, 'Нет', 0)")
                            db.execSQL("INSERT INTO answers (_id, question_id, answer, correct) VALUES(11, 3, 'Да, но не на уровне языка', 1)")
                            db.execSQL("INSERT INTO answers (_id, question_id, answer, correct) VALUES(12, 3, 'Только на уровне языка', 0)")

                            db.execSQL("INSERT INTO answers (_id, question_id, answer, correct) VALUES(13, 4, 'Да', 0)")
                            db.execSQL("INSERT INTO answers (_id, question_id, answer, correct) VALUES(14, 4, 'Нет', 1)")
                            db.execSQL("INSERT INTO answers (_id, question_id, answer, correct) VALUES(15, 4, 'Можно, но только некоторые функции', 0)")
                            db.execSQL("INSERT INTO answers (_id, question_id, answer, correct) VALUES(16, 4, 'Если сильно хочется, то можно', 0)")

                            db.execSQL("INSERT INTO answers (_id, question_id, answer, correct) VALUES(17, 5, 'Статически типизированный', 1)")
                            db.execSQL("INSERT INTO answers (_id, question_id, answer, correct) VALUES(18, 5, 'Динамически типизированный', 0)")
                            db.execSQL("INSERT INTO answers (_id, question_id, answer, correct) VALUES(19, 5, 'Оба варианта верны', 0)")
                            db.execSQL("INSERT INTO answers (_id, question_id, answer, correct) VALUES(20, 5, 'Нет верного ответа', 0)")
                        }
                    })
                    .build()
                INSTANCE = instance
                return instance
            }

        }
    }


}
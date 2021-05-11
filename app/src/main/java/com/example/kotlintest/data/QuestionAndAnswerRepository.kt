package com.example.kotlintest.data



class QuestionAndAnswerRepository(private val questionAndAnswerDao: QuestionAndAnswerDao) {

    //val readAllQuestionData: List<QuestionModel> = questionAndAnswerDao.readAllQuestionData()

    //val readAllAnswerData: List<AnswersModel> = questionAndAnswerDao.readAllAnswerData()

    suspend fun addQuestion(question: QuestionModel) {
        questionAndAnswerDao.addQuestion(question)
    }

    suspend fun addAnswer(answer: AnswersModel) {
        questionAndAnswerDao.addAnswer(answer)
    }

    suspend fun getQuestions(): List<QuestionModel>  {
        return questionAndAnswerDao.readAllQuestionData()
    }
}
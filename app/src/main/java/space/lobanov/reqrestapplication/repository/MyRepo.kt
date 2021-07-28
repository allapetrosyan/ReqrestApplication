package space.lobanov.reqrestapplication.repository

import space.lobanov.reqrestapplication.local_db.AppResult
import space.lobanov.reqrestapplication.model.Images

interface MyRepo {
    suspend fun getAllImages(): AppResult<List<Images>>
    suspend fun logInf(email: String, pass: String): String
    suspend fun signUp(email: String, pass: String): String
}
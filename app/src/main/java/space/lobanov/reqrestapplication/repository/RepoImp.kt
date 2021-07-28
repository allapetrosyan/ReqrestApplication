package space.lobanov.reqrestapplication.repository

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import space.lobanov.reqrestapplication.local_db.AppResult
import space.lobanov.reqrestapplication.local_db.ImageDao
import space.lobanov.reqrestapplication.local_db.handleApiError
import space.lobanov.reqrestapplication.local_db.handleSuccess
import space.lobanov.reqrestapplication.model.ApiUrl
import space.lobanov.reqrestapplication.model.Images

class RepoImp(
    private val api: ApiUrl,
    private val context: Context,
    private val dao: ImageDao = TODO()
) : MyRepo {

    override suspend fun getAllImages(): AppResult<List<Images>> {
        if (isOnline(context)) {
            return try {
                val response = api.getImageData()
                if (response.isSuccessful) {
                    response.body()?.let {
                        withContext(Dispatchers.IO) {
                            dao.insert(it.listOfImage)
                        }
                        Log.d("dwd", "inserted to room")
                        handleSuccess(it.listOfImage)
                    } ?: run {
                        handleApiError("error")
                    }
                } else {
                    handleApiError("error")
                }
            } catch (e: Exception) {
                Log.d("dwd", "error")
                AppResult.Error(e)
            }

        } else {
            val data = getImagesDataFromCache()
            return if (data.isNotEmpty()) {
                Log.d("dwd", "from db")
                AppResult.Success(data)
            } else {

                handleApiError("error from Room")
            }

        }

    }

    override suspend fun logInf(email: String, pass: String): String {
        val response = api.logIn(email, pass)
        if (response.body()?.token?.isNotEmpty() == true) {
            return response.body()!!.token
        }
        return " ";
    }

    override suspend fun signUp(email: String, pass: String): String {
        val response = api.createNewAccount(email, pass)
        if (response.body()?.token?.isNotEmpty() == true) {
            return response.body()!!.token
        }
        return " "; }

    private suspend fun getImagesDataFromCache(): List<Images> {
        return withContext(Dispatchers.IO) {
            dao.getAll()
        }
    }


    private fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val activeNetworkInfo = connectivityManager!!.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}


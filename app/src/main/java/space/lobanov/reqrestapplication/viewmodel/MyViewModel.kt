package space.lobanov.reqrestapplication.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import space.lobanov.reqrestapplication.local_db.AppResult
import space.lobanov.reqrestapplication.model.Images
import space.lobanov.reqrestapplication.repository.MyRepo

class MyViewModel(private var repo: MyRepo) : ViewModel() {

    val img = MutableLiveData<List<Images>>()
    //val tokenResponse =  MutableLiveData<String>()

    fun getMutableLiveData() {
        viewModelScope.launch() {
            when (val result = repo.getAllImages()) {
                is AppResult.Success -> {
                    img.value = result.successData!!
                }
                is AppResult.Error -> Log.d("dwd", "error")

            }
        }
    }


}
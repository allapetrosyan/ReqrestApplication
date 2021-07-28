package space.lobanov.reqrestapplication.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import space.lobanov.reqrestapplication.repository.MyRepo

class LoginViewModel(var repo: MyRepo) : ViewModel() {

    var strLogin = MutableLiveData<String?>()
    var strPass = MutableLiveData<String?>()

    private val _replaceResponse = MutableLiveData<String>()
    val replaceResponse: LiveData<String> = _replaceResponse

    private var _handleEmailEmptyTextError = MutableLiveData<String?>()
    val handleEmailEmptyTextError:LiveData<String?> = _handleEmailEmptyTextError

    private var _handlePassEmptyTextError = MutableLiveData<String?>()
    var handlePassEmptyTextError : LiveData<String?> = _handlePassEmptyTextError


    fun logIn(email: String, pass: String) {
        if (strLogin.value?.isEmpty() == true) {
            _handleEmailEmptyTextError.value = "Pls Type Email"
            return
        }
        if (strPass.value?.isEmpty() == true) {
            _handlePassEmptyTextError.value = "Pls Type Pass"
            return
        }
        viewModelScope.launch {
            val result = repo.logInf(email, pass)
            _replaceResponse.value = result
        }
    }
}





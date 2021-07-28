package space.lobanov.reqrestapplication.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import space.lobanov.reqrestapplication.repository.MyRepo

class RegViewModel(private val repoReg: MyRepo) : ViewModel() {

    private var strRegin = MutableLiveData<String?>()
    var strPass = MutableLiveData<String?>()

    private val _tokenResponse = MutableLiveData<String>()
    val tokenResponse: LiveData<String> = _tokenResponse

    private val _handleEmailEmptyTextError = MutableLiveData<String?>()
    val handleEmailEmptyTextError: LiveData<String?> = _handleEmailEmptyTextError

    private val _handlePassEmptyTextError = MutableLiveData<String?>()
    val handlePassEmptyTextError: LiveData<String?> = _handlePassEmptyTextError

    fun regIn(email: String, pass: String) {
        if (strRegin.value?.isEmpty() == true) {
            _handleEmailEmptyTextError.value = "Pls Type Email"
            return
        }
        if (strPass.value?.isEmpty() == true) {
            _handlePassEmptyTextError.value = "Pls Type Pass"
            return
        }
        viewModelScope.launch {
            val result = repoReg.logInf(email, pass)
            _tokenResponse.value = result
        }
    }
}
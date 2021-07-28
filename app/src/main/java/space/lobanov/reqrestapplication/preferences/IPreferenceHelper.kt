package space.lobanov.reqrestapplication.preferences

interface IPreferenceHelper {
    fun setUserToken(userToken: String)
    fun getUserToken(): String
    fun clearPrefs()
}
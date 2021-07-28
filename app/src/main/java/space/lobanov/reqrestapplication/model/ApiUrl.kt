package space.lobanov.reqrestapplication.model
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiUrl {
    @GET("https://reqres.in/api/users?page=2")
    suspend fun getImageData(): Response<ImageData>

        @FormUrlEncoded
        @POST("https://reqres.in/api/register")
      suspend fun createNewAccount(
            @Field("email") email: String?,
            @Field("password") pass: String?
        ): Response<TokenData>

        @FormUrlEncoded
        @POST("https://reqres.in/api/login")
       suspend fun logIn(
            @Field("email") email: String?,
            @Field("password") pass: String?
        ): Response<TokenData>


}
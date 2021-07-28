package space.lobanov.reqrestapplication.local_db

import android.app.Application
import android.content.Context
import androidx.room.Room
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.BuildConfig
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import space.lobanov.reqrestapplication.fragment.Constants.Companion.BASE_URL
import space.lobanov.reqrestapplication.login.LoginViewModel
import space.lobanov.reqrestapplication.model.ApiUrl
import space.lobanov.reqrestapplication.register.RegViewModel
import space.lobanov.reqrestapplication.repository.MyRepo
import space.lobanov.reqrestapplication.repository.RepoImp
import space.lobanov.reqrestapplication.viewmodel.MyViewModel

class DmModule {
    companion object {
        val apiModule = module {
            fun provideRetrofitApi(retrofit: Retrofit): ApiUrl {
                return retrofit.create(ApiUrl::class.java)
            }
            single { provideRetrofitApi(get()) }
        }

        val viewModelModule = module {
            viewModel {
                MyViewModel(repo = get())
            }
            viewModel {
                RegViewModel(repoReg = get())
            }
            viewModel {
                LoginViewModel(repo = get())
            }
        }

        val repositoryModule = module {
            fun provideRepository(
                api: ApiUrl,
                context: Context,
                dao: ImageDao
            ): MyRepo {
                return RepoImp(api, context, dao)
            }
            single { provideRepository(get(), androidContext(), get()) }
        }

        val networkModule = module {

            fun provideHttpClient(): OkHttpClient {
                val okHttpClientBuilder = OkHttpClient.Builder()

                if (BuildConfig.DEBUG) {
                    val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    }
                    okHttpClientBuilder.addInterceptor(httpLoggingInterceptor)
                }
                okHttpClientBuilder.build()
                return okHttpClientBuilder.build()
            }

            fun provideRetrofit(client: OkHttpClient, baseUrl: String): Retrofit {
                return Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client) //todo optimmal param
                    .build()
            }

            single { provideHttpClient() }
            single {
                val baseUrl = BASE_URL
                provideRetrofit(get(), baseUrl)
            }
        }

        val databaseModule = module {

            fun provideDatabase(application: Application): DataBase {
                return Room.databaseBuilder(application, DataBase::class.java, "imagesdb")
                    .fallbackToDestructiveMigration()
                    .build()
            }

            fun provideImagesDao(database: DataBase): ImageDao {
                return database.imageDao
            }

            single { provideDatabase(androidApplication()) }
            single { provideImagesDao(get()) }
        }


    }
}
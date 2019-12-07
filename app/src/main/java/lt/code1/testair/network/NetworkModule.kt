package lt.code1.testair.network

import android.content.Context
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import lt.code1.testair.BuildConfig
import lt.code1.testair.app.ApplicationScope
import lt.code1.testair.app.ServicesDimension
import lt.code1.testair.network.services.MOCK_API_URL
import lt.code1.testair.network.services.ServicesModule
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import lt.code1.testair.network.services.MockWebServer

private const val BASE_API_URL = "http://api.openweathermap.org/"
const val APPID = "7587eaff3affbf8e56a81da4d6c51d06"
private const val READ_TIMEOUT_S = 30L
private const val CONNECT_TIMEOUT_S = 30L

@Module(
    includes = [
        ServicesModule::class
    ]
)
class NetworkModule {

    @Provides
    @ApplicationScope
    fun provideRetrofit(
        context: Context,
        okHttpClient: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory,
        mockWebServer: MockWebServer
    ): Retrofit = if (BuildConfig.SERVICE_TYPE == ServicesDimension.MOCK) {
        mockWebServer.start(context)
        MOCK_API_URL
    } else {
        BASE_API_URL
    }.let { createRetrofit(okHttpClient, it, moshiConverterFactory) }

    private fun createRetrofit(
        okHttpClient: OkHttpClient,
        apiUrl: String,
        moshiConverterFactory: MoshiConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(apiUrl)
            .addConverterFactory(moshiConverterFactory)
            .build()
    }

    @Provides
    @ApplicationScope
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ) = OkHttpClient.Builder()
        .apply {
            readTimeout(READ_TIMEOUT_S, TimeUnit.SECONDS)
            connectTimeout(CONNECT_TIMEOUT_S, TimeUnit.SECONDS)
            addInterceptor(httpLoggingInterceptor)
        }
        .build()

    @Provides
    @ApplicationScope
    @Suppress("ConstantConditionIf")
    fun provideHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }

    @Provides
    @ApplicationScope
    fun provideMoshiConverterFactory(): MoshiConverterFactory = MoshiConverterFactory.create()

    @Provides
    @ApplicationScope
    fun provideMoshi(): Moshi = Moshi.Builder()
        .build()
}
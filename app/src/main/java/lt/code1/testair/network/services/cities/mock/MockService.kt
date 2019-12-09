package lt.code1.testair.network.services.cities.mock

import android.content.Context
import com.squareup.moshi.Moshi
import retrofit2.Response
import java.lang.reflect.ParameterizedType

abstract class MockService<T>(
    private val context: Context
) {

    abstract val fileName: String
    abstract val type: ParameterizedType

    fun parse(): Response<T> =
        Moshi.Builder()
            .build()
            .adapter<T>(type)
            .let {
                Response.success(
                    it.fromJson(getTextFromRawResource(fileName))
                )
            }

    private fun getTextFromRawResource(fileName: String) =
        context
            .resources
            .assets
            .open(fileName)
            .bufferedReader()
            .use { it.readText() }
}
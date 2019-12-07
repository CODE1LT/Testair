package lt.code1.testair.network.services

import android.content.Context
import lt.code1.testair.network.services.cities.external.CITIES_WEATHER_PATH
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit
import javax.inject.Inject

const val SERVER_PORT = 8080
const val MOCK_API_URL = "http://localhost:$SERVER_PORT/"
private const val RESPONSE_FILES_PATH = "api-response"
private const val RESPONSE_GET_USERS = "response_get_city.json"

class MockWebServer @Inject constructor() {

    fun start(context: Context) {
        Thread(Runnable {
            MockWebServer().run {
                dispatcher = getDispatcher(context)
                start(SERVER_PORT)
            }
        }).run {
            start()
            join()
        }
    }

    private fun getDispatcher(context: Context) = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return if (request.path?.contains(
                    CITIES_WEATHER_PATH,
                    true
                ) == true && request.method == "GET"
            ) {
                getMockResponse(
                    context,
                    "$RESPONSE_FILES_PATH/$RESPONSE_GET_USERS"
                ).throttleBody(20, 1, TimeUnit.SECONDS)
            } else {
                MockResponse().setResponseCode(404)
            }
        }
    }

    @Suppress("SameParameterValue")
    private fun getMockResponse(context: Context, path: String) = MockResponse()
        .setResponseCode(HttpURLConnection.HTTP_OK)
        .setBody(getUsersListFromFile(context, path))

    private fun getUsersListFromFile(context: Context, path: String) = context.assets
        .open(path)
        .bufferedReader()
        .use { it.readText() }

}